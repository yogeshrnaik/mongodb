M101J: MongoDB for Java Developers - Notes
=
Run MongoDB Server using following command:

```
mongod --storageEngine=mmapv1 --dbpath E:\mongodb\data
```
******************************************************************************************************************************
How to import data into MongoDB
=
Use mongorestore to restore the dump into your running mongod. 
Do this by opening a terminal window (mac) or cmd window (windows) and navigating to the directory so that 
you are in the parent directory of the dump directory (if you used the default extraction method, it should be hw1/). 
Now type:

```
mongorestore dump
```
******************************************************************************************************************************
Commands in Mongo Shell
=
```
> help
	db.help()                    help on db methods
	db.mycoll.help()             help on collection methods
	sh.help()                    sharding helpers
	rs.help()                    replica set helpers
	help admin                   administrative help
	help connect                 connecting to a db help
	help keys                    key shortcuts
	help misc                    misc things to know
	help mr                      mapreduce

	show dbs                     show database names
	show collections             show collections in current database
	show users                   show users in current database
	show profile                 show most recent system.profile entries with time >= 1ms
	show logs                    show the accessible logger names
	show log [name]              prints out the last segment of log in memory, 'global' is default
	use <db_name>                set current database
	db.foo.find()                list objects in collection foo
	db.foo.find( { a : 1 } )     list objects in foo where a == 1
	it                           result of the last line evaluated; use to further iterate
	DBQuery.shellBatchSize = x   set default number of items to display on shell
	exit                         quit the mongo shell
```

```
> show dbs
local  0.078GB
m101   0.078GB
test   0.078GB
video  0.078GB
```

Switch Database or Create Database if it does not already exists.
Creation of Database is lazy which means database is actually created only when we insert first document in it.

```
> use video
switched to db video
```
******************************************************************************************************************************



