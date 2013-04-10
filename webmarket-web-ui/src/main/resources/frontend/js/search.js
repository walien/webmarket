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

	// Init change on tags selection
	$("#tags-select").chosen().change(function() {
		var tags = [];
		$("#tags-select option:selected").each(function(index, option) {
			tags.push(option.value);
		});
		if (tags.length == 0) {
			clear_search();
			return;
		}
		do_filter_by_tags(tags);
	});
}

// =================================================
// SIMPLE SEARCH FEATURES
// =================================================

/**
 * Load all item tags
 */
function load_all_tags_list() {

	// Load all tags
	$.get(serverBase + allTagsURL, function(tags) {
		$.each(tags, function(index, tag) {
			$("#tags-select").append(
					"<option value='" + tag.id + "'>" + tag.name + "</option>")
		});
		$("#tags-select").trigger("liszt:updated");
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
	var params = "?name=" + text + "&brand=" + text + "&description=" + text;

	$.get(serverBase + queryItemByDataURL + params, function(items) {
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
	$("#tags-select").val("").trigger("liszt:updated");

	// The items list is now the global one (not filtered)
	currentItems = globalItems;

	// Display items into the view
	display_items(globalItems);
}

// =================================================
// FILTER FEATURES
// =================================================

function do_filter_by_tags(tags) {

	var tags = tags.join('+');

	// Build the query url based on the name, brand, description, ... attributes
	var params = "?tags=" + tags;

	$.get(serverBase + queryItemByTagsURL + params, function(items) {
		currentItems = items;
		display_items(items);
	});
}
