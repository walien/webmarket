// =========================
// Constants
//=========================
var serverBase = "http://localhost:8080";

var loginURL = "/rest/auth/login";
var logoutURL = "/rest/auth/logout";

var allItemsURL = "/rest/items/all";
var allTagsURL = "/rest/tags/all";
var queryItemByDataURL = "/rest/query/by-data/oneOf";
var queryItemByTagsURL = "/rest/query/by-tags/";

var currency = '€';

// =========================
// Global variables
// =========================
var globalItems = [];
var currentItems = [];

