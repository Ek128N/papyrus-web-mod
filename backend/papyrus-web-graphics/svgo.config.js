module.exports = {
	js2svg: {
		indent: 4, // number
		pretty: true, // boolean
	},
	plugins: [
		{
			name: 'preset-default',
			params: {
				overrides: {

					// customize the params of a default plugin
					inlineStyles: {
						onlyMatchedOnce: false,
					},
				},
			},
		},
	]
};
