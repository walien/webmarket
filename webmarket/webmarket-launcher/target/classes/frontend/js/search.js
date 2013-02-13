/**
 * Toggle the search panel
 */

function toggle_search_panel() {
	$("#search-panel").toggle();
}

/**
 * Initialize all handlers
 */
function init_handlers() {

	// Init searching on 'ENTER' key press
	$("#search-text").keypress(function(event) {
		if (event.keyCode == 13) {
			do_simple_search();
		}
	});
}

/**
 * Do a simple search
 */

function do_simple_search() {

	// Retrieve the text from the input
	var text = $("#search-text").val();
	console.log("Searching text : " + text);

	// Build the query url based on the name, brand, description, ... attributes
	var params = "?name=" + text + "&brand=" + text;

	$.get(serverBase + queryItemsURL + params, function(items) {
		currentItems = items;
		display_items(items);
	});
}

/**
 * Clear the search
 */

function clear_search() {

	// Empty the search box
	$("#search-text").val("");
	$("#tags-select").val("");

	// The items list is now the global one (not filtered)
	currentItems = globalItems;

	// Display items into the view
	display_items(globalItems);
}