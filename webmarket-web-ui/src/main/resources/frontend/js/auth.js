//////////////////////////////////
// SESSION ID STORAGE
//////////////////////////////////

$(document)
		.ready(
				function() {
					if (is_connected() == false) {
						$('#auth-form')
								.append(
										"<div class='navbar-form pull-right'><input id='username-tf' class='span2' type='text' placeholder='Email'> "
												+ "<input id='pwd-tf' class='span2' type='password' placeholder='Password'>"
												+ "<button class='btn' onclick='do_login();'>Sign in</button></div>");

					} else {
						$('#auth-form')
								.append(
										"<div class='navbar-form pull-right'><button class='btn span2' onclick='do_logout();'>"
												+ get_current_username()
												+ " / Sign out</button></div>");
					}
				});

function do_login() {

	var username = $("#username-tf").val();
	var pwd = $("#pwd-tf").val();

	console.log("Logging in with : " + username + "/" + pwd);

	$.post(serverBase + loginURL, {
		username : username,
		pwd : pwd
	}, function(sessionID) {

		// The returned session ID
		console.log("Current Session ID : " + sessionID);

		// Create a session object (stored into the local storage)
		create_session(username, sessionID);

		// Reload page
		location.reload();
	});

}

function do_logout() {

	var username = get_current_username();
	console.log("Logout with : " + username);

	// Destroy the session (remove data from the local storage)
	destroy_session();

	$.post(serverBase + logoutURL, {
		username : username,
	}, function(result) {

		// If the returned state is bad : throw an alert
		if (result == 'false') {
			alert("An error has occured during logout !");
		}

		// Reload page
		location.reload();
	});
}

function is_connected() {
	return localStorage['sessionID'] != "undefined"
			&& localStorage['sessionID'] != undefined;
}

function get_current_session_id() {
	return localStorage['sessionID'];
}

function get_current_username() {
	return localStorage['username'];
}

function create_session(username, id) {
	localStorage['username'] = username;
	localStorage['sessionID'] = id;
}

function destroy_session() {
	localStorage.clear();
}
