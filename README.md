# Content saver

This application allows you to save some content received from the user, and return the content to the user back. The peculiarity of this application is that the client receives a link through which he can get the content back at that moment, as soon as he contacts the server, i.e. without waiting for the content to be stored on the server.

### Getting started.

The application has a launch class `com.nc.contentsaver.StartPoint`. 
It takes the following command line arguments:
###### Connecting to database PostgreSQL:
* `db.nm=username` - where `username` is the username for connecting to the database
* `db.pw=password` - where `password` is the user password for connecting to the database
* `db.url=jUrl` - where `jUrl` is the database access jdbc-url
  
###### Server settings:
* `server.port=srvPort` - where `srvPort` is the port on which the server will be deployed (default is 1502).

It is also possible to customize ways to save content and information about the content. To start the server, you must create an object of class `com.nc.contentsaver.ContentSaver`, which is required argument interface object `com.nc.contentsaver.processes.managing.manager.DataManager`. To implement this interface, use the builder `com.nc.contentsaver.processes.managing.manager.DataSaverBuilder` - it will allow you to work with the data as you want it.

### Interaction with the server:

Currently, the server supports two types of requests: **PUT** and **GET**.

 - **PUT** request must contain content in the request body that needs to be saved. The server will return json to the client, which will contain the link field - the file ID on the server.
 - **GET** request must contain the file ID on the server in the path. Example: `host_url:1502/XjDlbUqmBgAmodDfmQPO`. If the content is found, the server in the response body will return its data to you. If for some reason the content was not found, the server will return json with the error description.
