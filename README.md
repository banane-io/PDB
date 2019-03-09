
PDB
===

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/banane-io/PDB?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This is a small web application to create a text game for browser. The goal of the game is to create something similar to Ogame, but with your own character and some good rpg elements to it.


How to Help
===

If you want to help, here's what you can do to contribute to the project :

* Download and install Java and Maven on your machine
* Clone or fork the project.
* Go check the issue with the label help-wanted and start working.

You can always go the gitter and ask question or give feedback if you want :).

What We Need
===
There is a great need to help with the layout and the aesthetics of the client (see the [pdb-frontend](https://github.com/banane-io/pdb-frontend) project for more information).

There are also a lot of small easy things to do. Come chat with me on Gitter or [this chatroom](https://chat.stackexchange.com/rooms/16134/coding-projects-and-vue-js-heaven) if you have any idea.

How to Setup the Database
===

You will need a local postgresql10 installation either on your local machine or elsewhere. For window, you can follow this [installation page](https://www.postgresql.org/download/windows/) and for Linux you can follow this [page of instructions](https://websiteforstudents.com/installing-postgresql-10-on-ubuntu-16-04-17-10-18-04/).

Once the installation is complete, you need to create a new db and a new user like so : 
```sql
CREATE DATABASE pdb;
CREATE USER pdb WITH ENCRYPTED PASSWORD 'password1';
GRANT ALL PRIVILEGES ON DATABASE pdb TO pdb;
```

This will create a user and database to connect to. Those information can be changed in the `application.properties` file, if you changed any of the provided one :

```
spring.datasource.url= jdbc:postgresql://localhost:5432/pdb
spring.datasource.username=pdb
spring.datasource.password=password1
```

The table will be created automatticaly by liquibase at the start of the application, so no need of any intervation.

How to Run the Application
===
This is really easy, all you have to do is run the correct mvn goal :

```
mvn spring-boot:run
```

If everything is setup correctly, the server should start up :).
