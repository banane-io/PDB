
PDB
===

This is a small web application to create a text game for browser. The goal of the game is to create something similar to Ogame, but with your own character and some good rpg elements to it.


How to Help
===

If you want to help, here's what you can do to contribute to the project :

* Download and install dotnet 6
* Clone or fork the project.
* Go check the issue with the label help-wanted and start working.

You can always open an issue and ask question or give feedback if you want :).


What We Need
===
There is a great need to help with the layout and the aesthetics of the client (see the [pdb-frontend](https://github.com/banane-io/pdb-frontend) project for more information).

There are also a lot of small easy things to do. Come chat with me in [this chatroom](https://chat.stackexchange.com/rooms/16134/coding-projects-and-vue-js-heaven) if you have any idea or just create an issue here!

Running Database + PDB in Docker
===

    docker run --name pdb-db -d -p 5432:5432 -e POSTGRES_USER=pdb -e POSTGRES_PASSWORD=password1 postgres
    docker build . -t pdb
    docker run --name pdb-app  -p 3000:3000 pdb

How to Setup the Database
===

If you didn't use the docker instance, you will need a postgresql installation either on your local machine or elsewhere. For Windows, you can follow this [installation page](https://www.postgresql.org/download/windows/) and for Linux you can follow this [page of instructions](https://websiteforstudents.com/installing-postgresql-10-on-ubuntu-16-04-17-10-18-04/).

Once the installation is complete, you need to create a new db and a new user like so : 
```sql
CREATE DATABASE pdb;
CREATE USER pdb WITH ENCRYPTED PASSWORD 'password1';
GRANT ALL PRIVILEGES ON DATABASE pdb TO pdb;
```

This will create a user and database to connect to. Those information can be changed in the `appsettings.json` file, if you changed any of the provided one :

```
"ConnectionStrings": {
    "DefaultConnection": "host=localhost;database=postgres;user id=pdb;password=password1;"
}
```

You need to run all the migrations to make sure you're database is up to date.

How to Run the Application
===
This is really easy, all you have to do is run the correct command :

```
dotnet run
```

If everything is setup correctly, the server should start up :).

There is 4 users account included in the seed data testtest, testtest2, testtest3 and testtest4 (all the passsword are testtest).
