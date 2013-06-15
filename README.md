Webmarket
=========

Webmarket is an e-market solution designed to be simple and lightweight. 

Functionnaly, webmarket provides features such as : 
1. Users management (administrators or customer accounts)
2. Items management & search (by criteria)
3. Item referencing management
4. Ordering management

From a technical point of view, webmarket is divided into 2 modules : 
1. Server side : Designed in Java and based on JERSEY (JAX-RS) for the the REST services layer. It provides some persistence support like in-memory or MongoDB 
2. Client side : Built on AngularJS for UI logic & on Twitter Bootstrap for UI components.

The choice of this above technical stack was clearly motivated by my desire to exploit AngularJS in complete application framework. 

Some screens & stories associated :

* An unconnected user can view the list of available items
![ScreenShot](https://raw.github.com/walien/webmarket/master/screenshots/screenshot_full_items_list.png "Desktop Preview")

* An unconnected user can view the detail of an item
![ScreenShot](https://raw.github.com/walien/webmarket/master/screenshots/screenshot_item_consult.png "Desktop Preview")

* An administrator can manage users 
![ScreenShot](https://raw.github.com/walien/webmarket/master/screenshots/screenshot_admin_users.png "Desktop Preview")

* An administrator can manage the list of items 
![ScreenShot](https://raw.github.com/walien/webmarket/master/screenshots/screenshot_admin_items.png "Desktop Preview")

* An administrator can edit an existing item
![ScreenShot](https://raw.github.com/walien/webmarket/master/screenshots/screenshot_item_editor.png "Desktop Preview")

* An administrator has access to customer orders
![ScreenShot](https://raw.github.com/walien/webmarket/master/screenshots/screenshot_admin_orders.png "Desktop Preview")
