$(document).ready(function() {

	// Init all control handlers
	init_handlers();

	// Load all items
	load_all_items_list();

	// Render the tags select
	$(".chzn-select").chosen();
});

function load_all_tags_list(){
	
}

/**
 * Make a request to the server in order to retrieve items
 */

function load_all_items_list() {

	// Retrieve all items (JSON format) from the server
	$.get(serverBase + allItemsURL, function(items) {
		// Global array of items
		globalItems = items;
		currentItems = items;
		console.log("Retrieving " + items.length
				+ " items from the server (REST resources)...");

		// Display all items
		display_items(items);
	});

}

/**
 * Display all items
 */

function display_items(items) {

	// The HTML list (retrieved by a jquery CSS selector)
	var htmlItemsList = $("#items-list");
	htmlItemsList.empty();

	// Iterate over the items list
	var gridCounter = 1;
	var list;
	for ( var i = 0; i < items.length; i++) {
		var item = items[i];

		// Build thumb content
		// Image
		var content = "<img alt='300x200' style='width:100px;' src='data:image/png;base64,"
				+ item.base64Image + "'>";

		// Title
		content += "<div class='caption'>",
				content += "<a href='#' onclick='display_item_details(" + i
						+ ");'><h3>" + item.brand + " - " + item.name
						+ "</h3></a>";

		// Description & Price
		content += "</p><p>" + item.description + "</p>";
		content += "<span class='label label-info'>" + item.price + " "
				+ currency + "</span>";
		content += "</div>";

		// Append thumb
		if (gridCounter == 1) {
			list = $("<ul/>", {
				class : "thumbnails"
			}).appendTo(htmlItemsList);
		}

		var listElt = $('<li/>', {
			class : "span3"
		}).appendTo(list);

		$('<div/>', {
			class : "thumbnail",
			html : content
		}).appendTo(listElt);

		if (gridCounter++ == 4) {
			gridCounter = 1;
		}
	}
}

/**
 * Display items details
 * 
 * @param id
 */

function display_item_details(id) {

	// Retrieve the selected item
	var selectedItem = currentItems[id];

	// Build the template context
	var context = {
		item : selectedItem,
		currency : currency
	};

	// Compile the handlebars template and push data into it
	var template = Handlebars.compile(item_details_template);
	var html = template(context);

	// Show modal box (item details)
	$('#modal-item-details .modal-body').empty();
	$('#modal-item-details .modal-body').append(html);
	$('#modal-item-details').modal('show');
}
