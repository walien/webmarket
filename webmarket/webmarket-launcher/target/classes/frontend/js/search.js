function do_simple_search() {

	// Retrieve the text from the input
	var text = $("#search-text").val();
	console.log("Searching text : " + text);

	$.get(serverBase + queryItemsURL + "?name=" + text, function(items) {
		displayItems(items);
	});
}

function clear_search() {
	$("#search-text").val("");
	displayItems(globalItems);
}