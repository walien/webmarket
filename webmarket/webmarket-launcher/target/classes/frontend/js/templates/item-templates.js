var item_details_template = "<img alt='300x200' style='width:100px; float:right;' src='data:image/png;base64,{{item.base64Image}}'>"
				+ "<h3>{{item.brand}} - {{item.name}}</h3>" +
				"<p style='overflow: hidden; padding-right: 25px;'>{{item.description}}</p>" +
				"<p>{{#each item.tags}}<span class='label label-success'>{{this.name}}</span>  {{/each}}</p>" +
				"<p><span class='label label-info'>{{item.price}} {{currency}}</span></p>";