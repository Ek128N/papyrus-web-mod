# User Documentation

## Home page

The home page is the starting point of a modeling project. It is where user can initiate a new project or manage an existing one.

Create a new project in the platform can be done through following ways:
* Create a blank project
* Create a project from existing templates
* Upload a previously exported project

For more details on project creation topic, see [`Create a Project`](https://docs.obeostudio.com/2023.6.0/help_center.html#create-project) section of the documentation.

As far as existing projects are concerned, the following actions are available:
* Open an existing project  
In order to open an existing project, user has to locate the project in the Project list and click on its name.
* Delete a project  
See the [`Delete a Project`](https://docs.obeostudio.com/2023.6.0/help_center.html#_delete_a_project) section of the documentation
* Rename a project  
See the [`Rename a Project`](https://docs.obeostudio.com/2023.6.0/help_center.html#_rename_a_project) section of the documentation

Once the project (new or existing one) is chosen, the workbench page is presented.

## Workbench  

The Workbench page is the main page where Projects are edited.
It is basically composed of one main central area and two side panels.  
The [`left panel`](#project-explorer-panel) is mainly used to see the structure of the Project as a tree of all its component parts.
The [`central area`](#diagram-panel) is the place where diagrams live and the [`right panel`](#right-side-panel) can display the Properties of the current diagram selection.

### Project Explorer panel

The Explorer panel displays all elements contained by the _Project_. Roots nodes are the _Resources_ stored in the project where all elements underneath live. For _UML_ resources, the root element generally is a _Model_ or a _Profile_.

> **Warning:** Note that the name of the resource should always end with `.uml`.

#### _Explorer Tree_

In order to create a _New Representation Diagram_, use the contextual menu on any semantic element and choose the action _New Representation_, choose the type of diagram in the dialog.

The _Explorer_ tree contains semantic elements of the _Model_ and diagrams at the same level.
Diagram elements can be easily recognized by the diagram icon  <img alt="diagram icon" src="./imgs/diagram.svg" width="16" height="16"> whereas semantic elements have specific icons depending of their  nature.

From the _Explorer_ view, it is possible to create new semantic elements. To do so, use the contextual menu on any semantic element and choose _New Object_. In the dialog, select the new element to create among all semantic elements listed in the dropdown (names are prefixed by the feature containment).

As far as the representation creation is concerned, it is worth noting that _Representations_ depend of the selected semantic element (the one used to open the contextual menu). Here are available representations and their creation contexts:

| Representation | Context |
|------------------|---------|
| _Class Diagram_ | _Package_ |
| _Composite Structure Diagram_ | _Package_ |
| _Package_ | _Package_ |
| _State Machine Diagram_ | _State Machine_ |  

As we can see in the table above, the _State Machine Diagram_ can only be added from a _State Machine_ semantic element.

Deletion of semantic elements (and representation diagrams) is possible through the contextual menu of tree nodes.

#### _Explorer toolbar_

The _Explorer toolbar_ contains global _Project_ actions:

* _Upload model_  
    Add a previously downloaded UML _Model_ into the _Project_. Accepted UML _Model_ files should have the ```.uml``` extension
* Synchronization with _Representation_ editor  
    When Synchronization with _Representation_ is **enabled**, selecting a graphical element in the diagram will **_Reveal_** and **_Select_** the semantic element corresponding in the _Explorer_ tree.  
    When Synchronization with _Representation_ is **disabled**, selecting a graphical element in the diagram will **_Select_** the semantic element corresponding in the _Explorer_ tree only if it has already been revealed.

#### Apply profile

In order to apply a profile use the contextual menu from the root _Model_ element.
Select the profile to apply in the list of candidates.
The available profiles are:

* The static profiles registered in the platform
* The dynamic profiles stored in other projects of your _Papyrus Web_ server.

#### Apply stereotype

To apply a stereotype:

* Make sure that the correct profile is applied on your _Model_
* Use the contextual menu _Apply Stereotype_ on an element
* Select the _Stereotype_ to apply

This stereotype is now displayed in the widget _Applied Stereotypes_ in the _Detail_ panel.

To edit the properties of a _Stereotype_, select the matching _Stereotype Application_ from the _Explorer_ panel.

> 🗒️ **Note:** You can navigate to a _Stereotype Application_ using the widget _Applied Stereotypes_ in the _Detail_ panel.

### Validation panel

The _Validation_ panel shares the left area of the workbench page with the _Explorer_. See the [documentation section](https://docs.obeostudio.com/2023.6.0/help_center.html#_validation_view) for more details.

### Diagram panel

The diagram panel shows _Representation Diagrams_ created in the project. Several _Representations_ can be open and they appear in separate tabs. 
Diagrams represent graphically a part of your semantic model. Each representation defines its own set of rules regarding its content.  
Graphical elements can be selected by clicking on elements. The inner most clicked element is selected.
A red border (with eight square handles) appears around the element when it is selected.  
Once an graphical element is selected, a second click on it will reveal a set of available tools grouped in an horizontal palette.
 
The Palette displays all the tools available according to the current selection. It has the following subgroups from left to right:
* _Connector_ action
Action to initiate a _Connection_ from the current element to another one. This action is only present in the Palette when a _Connection_ is available. Once the second element is chosen, the list of connections between those elements is shown to specify the actual nature of this new _Connection_

* Children element creation group  
All children creation actions are grouped in a dropdown list depending of the nature of the selected element. 
* Edge creation group  
This group present the list of the available _Connection_ types starting from the selected element, once one _Connection_ has been chosen, only compatible elements can be targeted to finish the edge creation.
* Generic tools (Edit, Delete from diagram, Delete from model,Hide, Fade)
As far as Edit is concerned, it worth noting that names (or more generally texts) can be edited by the following ways:
    * Edit action of the Palette
    * Double click on elements
    * Press F2
> **Warning:** As far as _Edge_ elements are concerned, start and end labels can only be edited by double clicking them. As the matter of fact, the above ways will edit the edge label itself (the centered label of the edge).
* Collapse / Expand tools  
Those actions are only available for containers or compartments (e.g _Package_ or _Operations Compartment_ of a _Class_)
* Close the Palette action

The Diagram panel has global actions grouped in an horizontal toolbar underneath the diagram tabs.
This toolbar contains the following actions:
* Zoom percent selector
* Zoom group
* Fit to screen action
* Auto Arrange all action
* Share the diagram link
* Reveal hidden elements action
* Reveal faded elements action 

### Right side panel
On the right of the page, one can see several stacked views given information about current selection.

#### Details

The Details panel is used to visualize and edit all the features of the selected semantic object (from Explorer or diagrams). The panel is divided into 4 separate tabs:
* UML: details the main UML features of the semantic element 
* Comments: displays comments associated to the selected element. Those comments can be owned by the element or applied to it (using a relation between the comment and this element).
* Profile: focus on applied stereotypes/profiles of this selected element
* Advanced: this tab displays all features of the semantic element. It uses generic rules to find the best suitable widget to visualize and edit the feature.

In the UML tab, each property is represented by an appropriated widget according of its nature. For instance, a property of type ```ecore::EString``` will be represented by a text widget. It might be, in some situations more appropriated to represent this string by a text area widget which supports multiple lines of text.

Basic widgets

In the following table, we present the mapping between basic types and their associated widgets

| Basic Type | Widget |
|------------------|---------|
| _Boolean_ | _Checkbox_ |  
| _String_ | _Text field_ or _Text area_ |
| _Number_ | _Text field_ |
| _Enumeration_ | _Select_ or _Radio_|
| _List_ | _List_ |

Here is [the Sirius documentation](https://docs.obeostudio.com/2023.6.0/help_center.html#_details_view) of all those widgets.

#### Related Elements

There is a section about the [_Related Elements view_](https://docs.obeostudio.com/2023.6.0/help_center.html#project-editor) in the documentation.


#### Representations 

There is a section about [_Representations view_](https://docs.obeostudio.com/2023.6.0/help_center.html#_representations_view) in the documentation.
