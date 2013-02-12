function do_simple_search() {

	// Retrieve the text from the input
	var text = $("#search-text").val();
	console.log("Searching text : " + text);

	// Build the query url based on the name, brand, description, ... attributes
	var params = "?name=" + text + "&brand=" + text;

	$.get(serverBase + queryItemsURL + params, function(items) {
		currentItems = items;
		displayItems(items);
	});
}

function clear_search() {

	// Empty the search box
	$("#search-text").val("");

	// The items list is now the global one (not filtered)
	currentItems = globalItems;

	// Display items into the view
	displayItems(globalItems);
}