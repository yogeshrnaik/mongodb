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
### Week 2 - Homework 2.2
Import json file into database.
```
> mongoimport --drop -d students -c grades grades.json
```

#### Answer

```javascript
> db.grades.find({score: {$gte: 65}}).sort({score: 1});
{ "_id" : ObjectId("50906d7fa3c412bb040eb5cf"), "student_id" : 22, "type" : "exam", "score" : 65.02518811936324 }
{ "_id" : ObjectId("50906d7fa3c412bb040eb70a"), "student_id" : 100, "type" : "homework", "score" : 65.29214756759019 }
{ "_id" : ObjectId("50906d7fa3c412bb040eb676"), "student_id" : 63, "type" : "homework", "score" : 65.31038121884853 }
{ "_id" : ObjectId("50906d7fa3c412bb040eb77a"), "student_id" : 128, "type" : "homework", "score" : 65.47002803265133 }
{ "_id" : ObjectId("50906d7fa3c412bb040eb743"), "student_id" : 115, "type" : "exam", "score" : 65.47329199925679 }
{ "_id" : ObjectId("50906d7fa3c412bb040eb80c"), "student_id" : 165, "type" : "quiz", "score" : 65.54110645268801 }
{ "_id" : ObjectId("50906d7fa3c412bb040eb695"), "student_id" : 71, "type" : "homework", "score" : 65.54625488975057 }
```
******************************************************************************************************************************
### Week 2 - Homework 2.4

#### Answer
Code changes are in UserDAO.java from project https://github.com/yogeshrnaik/mongodb/tree/master/MongoUniversity/M101J/week-02/create_blog_authors
******************************************************************************************************************************
### Week 2 - Homework 2.5
Find the title of a movie from the year 2013 that is rated PG-13 and won no awards? Please query the video.movieDetails collection to find the answer.

NOTE: There is a dump of the video database included in the handouts for the "Creating Documents" lesson. Use that data set to answer this question.

#### Answer:
```javascript
> db.movieDetails.find({year: 2013, rated: 'PG-13', "awards.wins" : 0}).pretty();
{
        "_id" : ObjectId("5692a3e124de1e0ce2dfda22"),
        "title" : "A Decade of Decadence, Pt. 2: Legacy of Dreams",
        "year" : 2013,
        "rated" : "PG-13",
        "released" : ISODate("2013-09-13T04:00:00Z"),
        "runtime" : 65,
        "countries" : [
                "USA"
        ],
        "genres" : [
                "Documentary"
        ],
        "director" : "Drew Glick",
        "writers" : [
                "Drew Glick"
        ],
        "actors" : [
                "Gordon Auld",
                "Howie Boulware Jr.",
                "Tod Boulware",
                "Chen Drachman"
        ],
        "plot" : "A behind the scenes look at the making of A Tiger in the Dark: The Decadence Saga.",
        "poster" : null,
        "imdb" : {
                "id" : "tt2199902",
                "rating" : 8,
                "votes" : 50
        },
        "awards" : {
                "wins" : 0,
                "nominations" : 0,
                "text" : ""
        },
        "type" : "movie"
}
```
******************************************************************************************************************************
### Week 2 - Homework 2.6
Using the video.movieDetails collection, how many movies list "Sweden" second in the the list of countries.

#### Answer:
```javascript
> db.movieDetails.find({"countries.1" : "Sweden"}).count();
6
```
******************************************************************************************************************************
### Week 4
#### Run javascript code from .js file to import data into Mongodb
```javascript
db=db.getSiblingDB("school");
db.students.drop();
types = ['exam', 'quiz', 'homework', 'homework'];
// 1 million students
for (i = 0; i < 1000000; i++) {

    // take 10 classes
    for (class_counter = 0; class_counter < 10; class_counter ++) {
	scores = []
	    // and each class has 4 grades
	    for (j = 0; j < 4; j++) {
		scores.push({'type':types[j],'score':Math.random()*100});
	    }

	// there are 500 different classes that they can take
	class_id = Math.floor(Math.random()*501); // get a class id between 0 and 500

	record = {'student_id':i, 'scores':scores, 'class_id':class_id};
	db.students.insert(record);

    }

}
```
Let us say we have above code written into create_scores2.js file.
Then use following command in mongo shell to load this file and execute the code in it.
```
mongoshell> load('create_scores2.js');
```

******************************************************************************************************************************
