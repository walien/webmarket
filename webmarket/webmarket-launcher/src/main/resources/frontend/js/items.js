$(document).ready(function() {

	// Load all items
	loadAllItemsList();

});

/**
 * Make a request to the server
 */

function loadAllItemsList() {

	// Retrieve all items (JSON format) from the server
	$.get(serverBase + allItemsURL, function(items) {
		// Global array of items
		globalItems = items;
		console.log("Retrieving " + items.length
				+ " items from the server (REST resources)...");

		// Display all items
		displayAllItems();
	});

}

/**
 * Display all items
 */

function displayAllItems() {

	// The HTML list (retrieved by a jquery CSS selector)
	var htmlItemsList = $("#items-list");

	// Iterate over the items list
	var gridCounter = 1;
	var list;
	for ( var i = 0; i < globalItems.length; i++) {
		var item = globalItems[i];

		// Build thumb content
		// Image
		var content = "<img alt='300x200' style='width:100px;' src='data:image/png;base64,"
				+ item.base64Image + "'>";

		// Title
		content += "<div class='caption'>",
				content += "<a href='#' onclick='displayItemDetails(" + i
						+ ");'><h3>" + item.brand + " - " + item.name
						+ "</h3></a>";

		// Tags
		var tags = item.tags;
		for ( var j = 0; j < tags.length; j++) {
			content += "<span class='label label-success'>" + tags[j].name
					+ "</span> ";
		}

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

function displayItemDetails(id) {

	// Retrieve the selected item
	var selectedItem = globalItems[id];

	// Compile the handlebars template and push data into it
	var template = Handlebars.compile(item_details_template);
	var context = selectedItem;
	var html = template(context);

	// Show modal box (item details)
	$('#modal-item-details .modal-body').empty();
	$('#modal-item-details .modal-body').append(html);
	$('#modal-item-details').modal('show');
}
