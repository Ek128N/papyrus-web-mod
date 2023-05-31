/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.services.uml.profile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.profile.DynamicProfileConverter;
import org.eclipse.papyrus.uml.domain.services.profile.ProfileDefinition;
import org.eclipse.papyrus.uml.domain.services.profile.ProfileVersion;
import org.eclipse.papyrus.web.persistence.entities.ProfileResourceEntity;
import org.eclipse.papyrus.web.persistence.repositories.IProfileRepository;
import org.eclipse.papyrus.web.services.api.dto.ApplyProfileInput;
import org.eclipse.papyrus.web.services.api.dto.ApplyProfileSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.IUMLProfileService;
import org.eclipse.papyrus.web.services.api.uml.profile.PublishProfileInput;
import org.eclipse.papyrus.web.services.api.uml.profile.PublishProfileSuccessPayload;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileMetadata;
import org.eclipse.papyrus.web.services.api.uml.profile.UMLProfileVersion;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service used to query the available UML profile metadata.
 *
 * @author lfasani
 */
public class UMLProfileService implements IUMLProfileService {

    /**
     * This prefix is used to reference the dynamic profile.
     */
    public static final String WEB_DYNAMIC_PROFILE_RESOURCE_PREFIX = "pathmap://WEB_DYNAMIC_PROFILE/"; //$NON-NLS-1$

    private static final Logger LOGGER = LoggerFactory.getLogger(UMLProfileService.class);

    private final UMLProfileMetadataRegistry umlRegistry;

    private final IObjectService objectService;

    private final IProfileRepository profileRepository;

    private Registry factoryRegistry;

    public UMLProfileService(UMLProfileMetadataRegistry registry, IObjectService objectService, IProfileRepository profileRepository, Registry factoryRegistry) {
        this.umlRegistry = Objects.requireNonNull(registry);
        this.objectService = Objects.requireNonNull(objectService);
        this.profileRepository = Objects.requireNonNull(profileRepository);
        this.factoryRegistry = factoryRegistry;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UMLProfileMetadata> getAllUMLProfiles() {
        List<UMLProfileMetadata> dynamicProfiles = StreamSupport.stream(this.profileRepository.findAll().spliterator(), false)
                .flatMap(profileResourceEntity -> this.getDynamicProfileMetadata(profileResourceEntity).stream()).collect(Collectors.toList());

        List<UMLProfileMetadata> profiles = new ArrayList<>(dynamicProfiles);
        profiles.addAll(this.umlRegistry.getUMLProfileDescriptions());
        return profiles;
    }

    private List<UMLProfileMetadata> getDynamicProfileMetadata(ProfileResourceEntity profileResourceEntity) {

        // No need to resoveProxies
        Resource resource = this.createResource(profileResourceEntity.getId().toString());
        try (var inputStream = new ByteArrayInputStream(profileResourceEntity.getContent().getBytes())) {
            resource.load(inputStream, null);
            return EMFUtils.allContainedObjectOfType(resource, Profile.class).map(profile -> {
                String profileId = profile.eResource().getURIFragment(profile);
                return new UMLProfileMetadata(profile.getName(), resource.getURI() + "#" + profileId); //$NON-NLS-1$
            }).toList();
        } catch (IOException exception) {
            LOGGER.warn(exception.getMessage(), exception);
            return Collections.emptyList();
        }

    }

    private void copyAllKeepingId(Collection<EObject> eObjects, Resource resource) {
        if (resource instanceof XMLResourceImpl) {
            XMLResourceImpl xmlResource = (XMLResourceImpl) resource;
            Copier copier = new Copier();
            Collection<EObject> copiedObjects = copier.copyAll(eObjects);
            copier.copyReferences();

            resource.getContents().addAll(copiedObjects);
            for (EObject sourceObject : copier.keySet()) {
                String id = this.objectService.getId(sourceObject);
                xmlResource.setID(copier.get(sourceObject), id);
            }
        }
    }

    private Resource createResource(String resourceId) {
        URI resourceUri = URI.createURI(UMLProfileService.WEB_DYNAMIC_PROFILE_RESOURCE_PREFIX + resourceId);
        Resource.Factory factory = (Factory) this.factoryRegistry.getExtensionToFactoryMap().get("uml"); //$NON-NLS-1$
        return factory.createResource(resourceUri);
    }

    @Override
    public IPayload applyProfile(IEditingContext editingContext, ApplyProfileInput input) {
        String packageUMLId = input.modelId();
        String profileURI = input.profileUriPath();
        IPayload payload = null;
        Optional<Package> umlPackageOptional = this.objectService.getObject(editingContext, packageUMLId)//
                .filter(Package.class::isInstance)//
                .map(Package.class::cast);

        String errorMessage = "The profile application failed"; //$NON-NLS-1$
        if (umlPackageOptional.isPresent()) {
            Package pack = umlPackageOptional.get();

            if (editingContext instanceof EditingContext) {
                // This call will load the resource in the resourceSet
                try {
                    Optional<Profile> umlProfileOptional = Optional.of(((EditingContext) editingContext).getDomain().getResourceSet().getEObject(URI.createURI(profileURI), true))
                            .filter(Profile.class::isInstance)//
                            .map(Profile.class::cast);

                    if (umlProfileOptional.isPresent()) {
                        if (this.isProfileApplicable(pack, umlProfileOptional.get())) {
                            pack.applyProfile(umlProfileOptional.get());

                            payload = new ApplyProfileSuccessPayload(input.id());

                            return payload;
                        } else {
                            errorMessage = MessageFormat.format("The profile with id {0} is already applied on the package with id {1}", profileURI, packageUMLId); //$NON-NLS-1$
                        }
                    } else {
                        errorMessage = MessageFormat.format("No profile found with id {0}", profileURI); //$NON-NLS-1$
                    }
                    // CHECKSTYLE:OFF
                } catch (RuntimeException e) {
                    errorMessage = MessageFormat.format("No profile found with id {0} : {1}", profileURI, e.getMessage()); //$NON-NLS-1$
                }
            }
        } else {
            errorMessage = MessageFormat.format("No Package found with id {0}", packageUMLId); //$NON-NLS-1$
        }

        LOGGER.error(errorMessage);
        return new ErrorPayload(input.id(), errorMessage);
    }

    private boolean isProfileApplicable(Package pack, Profile profile) {
        return !pack.getAppliedProfiles().contains(profile);
    }

    @Override
    public Optional<UMLProfileVersion> getProfileLastVersion(IEditingContext editingContext, String profileId) {
        Optional<UMLProfileVersion> versionOpt = Optional.empty();

        Optional<Profile> profileOpt = this.objectService.getObject(editingContext, profileId)//
                .filter(Profile.class::isInstance)//
                .map(Profile.class::cast);
        //
        if (profileOpt.isPresent()) {
            Optional<ProfileResourceEntity> profileResourceEntityOpt = profileOpt.map(EObject::eResource)//
                    .map(Resource::getURI)//
                    .map(URI::lastSegment)//
                    .map(segment -> UUID.nameUUIDFromBytes(segment.getBytes()))//
                    .flatMap(this.profileRepository::findById);
            if (profileResourceEntityOpt.isPresent()) {
                versionOpt = profileResourceEntityOpt.flatMap(profileResourceEntity -> {
                    Resource resource = this.createResource(profileResourceEntity.getId().toString());

                    try (var inputStream = new ByteArrayInputStream(profileResourceEntity.getContent().getBytes())) {
                        resource.load(inputStream, null);
                        return this.getLastProfileVersion(resource, profileOpt.get());
                    } catch (IOException exception) {
                        LOGGER.warn(exception.getMessage(), exception);
                    }
                    return null;
                });
            } else {
                versionOpt = Optional.of(new UMLProfileVersion(0, 0, 0));
            }
        }

        return versionOpt;
    }

    private Optional<UMLProfileVersion> getLastProfileVersion(Resource resourceOfPublishedProfile, Profile profileOfUmlResource) {
        return EMFUtils.allContainedObjectOfType(resourceOfPublishedProfile, Profile.class)//
                .filter(profile -> profile.getName() != null && profile.getName().equals(profileOfUmlResource.getName()))//
                .findFirst() //
                .flatMap(this::getVersionFromProfile);
    }

    private Optional<UMLProfileVersion> getVersionFromProfile(Profile profile) {
        Optional<UMLProfileVersion> versionOpt = Optional.empty();
        EAnnotation eAnnotationMain = profile.getEAnnotation("http://www.eclipse.org/uml2/2.0.0/UML"); //$NON-NLS-1$
        Optional<EPackage> ePackageOpt = eAnnotationMain.getContents().stream()//
                .filter(EPackage.class::isInstance)//
                .map(EPackage.class::cast)//
                .findFirst();
        if (ePackageOpt.isPresent()) {
            versionOpt = ePackageOpt.map(ePackage -> ((EModelElement) ePackage).getEAnnotation("PapyrusVersion"))// //$NON-NLS-1$
                    .map(eAnnotation -> eAnnotation.getDetails().get("Version")) // //$NON-NLS-1$
                    .map(strVersion -> {
                        UMLProfileVersion profileLastVersion = null;
                        String[] versions = strVersion.split("\\."); //$NON-NLS-1$
                        if (versions.length == 3) {
                            try {
                                profileLastVersion = new UMLProfileVersion(Integer.parseInt(versions[0]), Integer.parseInt(versions[1]), Integer.parseInt(versions[2]));
                            } catch (NumberFormatException e) {
                                LOGGER.error(MessageFormat.format("Invalid version format of profile {0} in profile resource with id {0}", profile.getName(), //$NON-NLS-1$
                                        profile.eResource().getURI().lastSegment()));
                            }
                        }

                        return profileLastVersion;
                    });
        } else {
            versionOpt = Optional.of(new UMLProfileVersion(0, 0, 0));
        }
        return versionOpt;
    }

    @Override
    public IPayload publishProfile(IEditingContext editingContext, PublishProfileInput publishProfileInput) {
        Optional<Profile> profileOpt = this.objectService.getObject(editingContext, publishProfileInput.objectId())//
                .filter(Profile.class::isInstance)//
                .map(Profile.class::cast);

        IPayload payload = null;
        if (profileOpt.isPresent()) {
            payload = this.doPublishProfile(editingContext, publishProfileInput, profileOpt.get());
        } else {
            payload = this.buildErrorPublishProfilePayload(publishProfileInput.id(), ". No profile with id " + publishProfileInput.objectId()); //$NON-NLS-1$
        }

        return payload;
    }

    private IPayload doPublishProfile(IEditingContext editingContext, PublishProfileInput publishProfileInput, Profile profile) {
        IPayload payload;
        if (this.isRootProfile(profile)) {
            Boolean isProfileUpdated = this.updateProfileWithEPackage(profile, publishProfileInput);
            if (isProfileUpdated) {
                Boolean profilePublish = this.publishProfile(profile, editingContext);
                if (profilePublish) {
                    payload = new PublishProfileSuccessPayload(publishProfileInput.id());
                } else {
                    payload = this.buildErrorPublishProfilePayload(publishProfileInput.id(), ". Failed to save the profile."); //$NON-NLS-1$
                }
            } else {
                payload = this.buildErrorPublishProfilePayload(publishProfileInput.id(), ". Failed to generate the ecore EPackage meta-model."); //$NON-NLS-1$
            }
        } else {
            payload = this.buildErrorPublishProfilePayload(publishProfileInput.id(), ". The profile is not a root profile."); //$NON-NLS-1$
        }
        return payload;
    }

    private ErrorPayload buildErrorPublishProfilePayload(UUID inputId, String message) {
        String baseMsg = MessageFormat.format("Failed to publish the dynamic profile of id {0}", inputId); //$NON-NLS-1$
        return new ErrorPayload(inputId, baseMsg + message);
    }

    private boolean isRootProfile(Profile profile) {
        return profile.eContainer() == null;
    }

    private Boolean publishProfile(Profile profile, IEditingContext editingContext) {
        Boolean publishSucceeded = this.toProfileEntity(profile, editingContext)//
                .map(this.profileRepository::save)//
                .isPresent();
        return publishSucceeded;
    }

    private Boolean updateProfileWithEPackage(Profile profile, PublishProfileInput publishProfileInput) {
        ProfileDefinition profileDefinition = new ProfileDefinition(new ProfileVersion(publishProfileInput.version()), publishProfileInput.comment(), publishProfileInput.copyright(),
                publishProfileInput.date(), publishProfileInput.author());
        if (new DynamicProfileConverter().generateEPackageInProfile(profile, publishProfileInput.saveOCLConstraint(), profileDefinition)) {
            return true;
        }
        return false;
    }

    private Optional<ProfileResourceEntity> toProfileEntity(Profile profile, IEditingContext editingContext) {
        Optional<ProfileResourceEntity> profileEntityOpt = Optional.empty();
        Resource resourceJson = profile.eResource();
        String resourceId = profile.eResource().getURI().lastSegment();

        Resource outputResource = this.createResource(resourceId);
        this.copyAllKeepingId(resourceJson.getContents(), outputResource);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            // No need to give options because the UMLResourceFactory properly set the options
            outputResource.save(outputStream, null);
            Optional<byte[]> optionalBytes = Optional.of(outputStream.toByteArray());
            if (optionalBytes.isPresent()) {
                byte[] bytes = optionalBytes.get();
                String content = new String(bytes);

                ProfileResourceEntity profileEntity = new ProfileResourceEntity();
                profileEntity.setId(UUID.nameUUIDFromBytes(resourceId.getBytes()));
                profileEntity.setContent(content);
                profileEntityOpt = Optional.of(profileEntity);
            }
        } catch (IOException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }

        return profileEntityOpt;
    }

}
