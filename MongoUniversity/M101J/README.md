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
This will take a lot of time (> 30 mins) depending on the hardware configuration of the machine.

When loading is happening, in Mongo db server console, we see something like below:
```
2017-02-04T11:31:40.345+0530 I COMMAND  [conn1] CMD: drop school.students
2017-02-04T11:32:50.742+0530 I STORAGE  [FileAllocator] allocating new datafile E:\mongodb\data\school.1, filling with zeroes...
2017-02-04T11:32:50.742+0530 I STORAGE  [FileAllocator] creating directory E:\mongodb\data\_tmp
2017-02-04T11:32:51.254+0530 I STORAGE  [FileAllocator] done allocating datafile E:\mongodb\data\school.1, size: 128MB,  took 0.51 secs
2017-02-04T11:32:51.255+0530 I COMMAND  [conn1] command school.students command: insert { insert: "students", documents: [ { _id: ObjectId('58956e8a2fcfbcd9c8516bfc'), student_id: 14764.0, scores: [ { type: "exam", score: 57.00238919372653 }, { type: "quiz", score: 52.48765338006469 }, { type: "homework", score: 16.05309896618723 }, { type: "homework", score: 16.81842436585328 } ], class_id: 258.0 } ], ordered: true } ninserted:1 keyUpdates:0 writeConflicts:0 numYields:0 reslen:25 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 2 } }, Database: { acquireCount: { w: 1 } }, Collection: { acquireCount: { W: 1 } }, Metadata: { acquireCount: { W: 1 } } } protocol:op_command 513ms2017-02-04T11:32:57.203+0530 I COMMAND  [conn1] command school.students command: insert { insert: "students", documents: [ { _id: ObjectId('58956e912fcfbcd9c8519901'), student_id: 15916.0, scores: [ { type: "exam", score: 44.47453246657889 }, { type: "quiz", score: 82.18937421401455 }, { type: "homework", score: 83.12285389876118 }, { type: "homework", score: 21.08711701138441 } ], class_id: 380.0 } ], ordered: true } ninserted:1 keyUpdates:0 writeConflicts:0 numYields:0 reslen:25 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 2 } }, Database: { acquireCount: { w: 1 } }, Collection: { acquireCount: { W: 1 } } } protocol:op_command 125ms
2017-02-04T11:35:23.887+0530 I STORAGE  [FileAllocator] allocating new datafile E:\mongodb\data\school.2, filling with zeroes...
2017-02-04T11:35:24.917+0530 I STORAGE  [FileAllocator] done allocating datafile E:\mongodb\data\school.2, size: 256MB,  took 1.028 secs
2017-02-04T11:35:24.918+0530 I COMMAND  [conn1] command school.students command: insert { insert: "students", documents: [ { _id: ObjectId('58956f232fcfbcd9c8568e59'), student_id: 48411.0, scores: [ { type: "exam", score: 19.55097253168092 }, { type: "quiz", score: 63.89583186808366 }, { type: "homework", score: 83.50886772617812 }, { type: "homework", score: 12.00642268082501 } ], class_id: 439.0 } ], ordered: true } ninserted:1 keyUpdates:0 writeConflicts:0 numYields:0 reslen:25 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 2 } }, Database: { acquireCount: { w: 1 } }, Collection: { acquireCount: { W: 1 } }, Metadata: { acquireCount: { W: 1 } } } protocol:op_command 1030ms
2017-02-04T11:41:24.591+0530 I STORAGE  [FileAllocator] allocating new datafile E:\mongodb\data\school.3, filling with zeroes...
2017-02-04T11:41:26.829+0530 I STORAGE  [FileAllocator] done allocating datafile E:\mongodb\data\school.3, size: 512MB,  took 2.237 secs
2017-02-04T11:41:26.829+0530 I STORAGE  [conn1] MmapV1ExtentManager took 2 seconds to open: E:\mongodb\data\school.3
2017-02-04T11:41:26.830+0530 I COMMAND  [conn1] command school.students command: insert { insert: "students", documents: [ { _id: ObjectId('5895708c2fcfbcd9c86330a6'), student_id: 131209.0, scores: [ { type: "exam", score: 81.4390071413487 }, { type: "quiz", score: 34.43411702085631 }, { type: "homework", score: 60.91781410491238 }, { type: "homework", score: 7.865856439801144 } ], class_id: 296.0 } ], ordered: true } ninserted:1 keyUpdates:0 writeConflicts:0 numYields:0 reslen:25 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 2 } }, Database: { acquireCount: { w: 1 } }, Collection: { acquireCount: { W: 1 } }, Metadata: { acquireCount: { W: 1 } } } protocol:op_command 2239ms
2017-02-04T11:49:28.660+0530 I STORAGE  [FileAllocator] allocating new datafile E:\mongodb\data\school.4, filling with zeroes...
2017-02-04T11:49:30.742+0530 I STORAGE  [FileAllocator] done allocating datafile E:\mongodb\data\school.4, size: 512MB,  took 2.08 secs
2017-02-04T11:49:30.743+0530 I STORAGE  [conn1] MmapV1ExtentManager took 2 seconds to open: E:\mongodb\data\school.4
2017-02-04T11:49:30.743+0530 I COMMAND  [conn1] command school.students command: insert { insert: "students", documents: [ { _id: ObjectId('589572702fcfbcd9c874b2b4'), student_id: 245950.0, scores: [ { type: "exam", score: 7.874309508623045 }, { type: "quiz", score: 63.05183584245054 }, { type: "homework", score: 78.03716337089925 }, { type: "homework", score: 65.29313430172454 } ], class_id: 135.0 } ], ordered: true } ninserted:1 keyUpdates:0 writeConflicts:0numYields:0 reslen:25 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 2 } }, Database: { acquireCount: { w: 1 } }, Collection: { acquireCount: { W: 1 } }, Metadata: { acquireCount: { W: 1 } } } protocol:op_command 2083ms
```

#### Explain command to check if a query uses Indexes
explain() runs on top of collection and then chain what we want to do.
E.g. To use explain() with find() command, use it like below.

```javascript
> db.students.explain().find({student_id:1});
{
	"queryPlanner": {
		"plannerVersion": 1,
		"namespace": "school.students",
		"indexFilterSet": false,
		"parsedQuery": {
			"student_id": {
				"$eq": 1
			}
		},
		"winningPlan": {
			"stage": "COLLSCAN",   // COLLSCAN means it is scanning all documents and not using any index
			"filter": {
				"student_id": {
					"$eq": 1
				}
			},
			"direction": "forward"
		},
		"rejectedPlans": []
	},
	"serverInfo": {
		"host": "IN1W7L-300705",
		"port": 27017,
		"version": "3.2.11",
		"gitVersion": "009580ad490190ba33d1c6253ebd8d91808923e4"
	},
	"ok": 1
}
```

If we use findOne() in large collection that does not have any index, it is still faster as it quits as soon as it finds first document.
Assumption is that documents in students collection are in the order of student_id.

### Add index
Use createIndex() on a collection to create index. This function takes a JSON that has details of on which fields we want to create index. 
In below example, we are creating index on student_id field. 
In this 1 after student_id means we are creating index for ascending. -1 means descending.
```javascript
> db.students.createIndex({student_id : 1});
{
	"createdCollectionAutomatically" : false,
    "numIndexesBefore" : 1,
    "numIndexesAfter" : 2,
    "ok" : 1
}
```
In Mongo server console, after creating index, it shows:
```
2017-02-04T13:52:38.287+0530 I INDEX    [conn3] build index on: school.students properties: { v: 1, key: { student_id: 1.0 }, name: "student_id_1", ns: "school.students" }
2017-02-04T13:52:38.288+0530 I INDEX    [conn3]          building index using bulk method
2017-02-04T13:52:41.000+0530 I -        [conn3]   Index Build: 1218800/6172445 19%
2017-02-04T13:52:44.000+0530 I -        [conn3]   Index Build: 2610800/6172445 42%
2017-02-04T13:52:49.771+0530 I -        [conn3]   Index Build: 3382600/6172445 54%
2017-02-04T13:52:52.000+0530 I -        [conn3]   Index Build: 4315800/6172445 69%
2017-02-04T13:52:55.000+0530 I -        [conn3]   Index Build: 5707000/6172445 92%
2017-02-04T13:53:08.376+0530 I INDEX    [conn3]          done building bottom layer, going to commit
2017-02-04T13:53:08.594+0530 I INDEX    [conn3] build index done.  scanned 6172445 total records. 30 secs
2017-02-04T13:53:08.595+0530 I COMMAND  [conn3] command school.$cmd command: createIndexes { createIndexes: "students", indexes: [ { ns: "school.students", key: { student_id: 1.0 }, name: "student_id_1" } ] } keyUpdates:0 writeConflicts:0 numYields:0 reslen:98 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 12344894 } }, Database: { acquireCount: { W: 1 } }, Collection: { acquireCount: { W: 1 } }, Metadata: { acquireCount:{ W: 10 } } } protocol:op_command 30383ms
```

After creating index, if we use explain() now then it shows which index is being used.

```javascript
> db.students.explain().find({student_id:1});
{
	"queryPlanner": {
		"plannerVersion": 1,
		"namespace": "school.students",
		"indexFilterSet": false,
		"parsedQuery": {
			"student_id": {
				"$eq": 1
			}
		},
		"winningPlan": {
			"stage": "FETCH",
			"inputStage": {
				"stage": "IXSCAN",
				"keyPattern": {
					"student_id": 1
				},
				"indexName": "student_id_1",    // SHOWS HERE WHICH INDEX IS BEING USED
				"isMultiKey": false,
				"isUnique": false,
				"isSparse": false,
				"isPartial": false,
				"indexVersion": 1,
				"direction": "forward",
				"indexBounds": {
					"student_id": [
						"[1.0, 1.0]"
					]
				}
			}
		},
		"rejectedPlans": []
	},
	"serverInfo": {
		"host": "IN1W7L-300705",
		"port": 27017,
		"version": "3.2.11",
		"gitVersion": "009580ad490190ba33d1c6253ebd8d91808923e4"
	},
	"ok": 1
}
```

If we use explain(true) then it actually executes the query and tells us how many documents it examined.
Check for "executionStages.docsExamined" in below JSON.
```javascript
> db.students.explain(true).find({student_id:1});
{
	"queryPlanner": {
		"plannerVersion": 1,
		"namespace": "school.students",
		"indexFilterSet": false,
		"parsedQuery": {
			"student_id": {
				"$eq": 1
			}
		},
		"winningPlan": {
			"stage": "FETCH",
			"inputStage": {
				"stage": "IXSCAN",
				"keyPattern": {
					"student_id": 1
				},
				"indexName": "student_id_1",
				"isMultiKey": false,
				"isUnique": false,
				"isSparse": false,
				"isPartial": false,
				"indexVersion": 1,
				"direction": "forward",
				"indexBounds": {
					"student_id": [
						"[1.0, 1.0]"
					]
				}
			}
		},
		"rejectedPlans": []
	},
	"executionStats": {
		"executionSuccess": true,
		"nReturned": 10,
		"executionTimeMillis": 0,
		"totalKeysExamined": 10,
		"totalDocsExamined": 10,
		"executionStages": {
			"stage": "FETCH",
			"nReturned": 10,
			"executionTimeMillisEstimate": 0,
			"works": 11,
			"advanced": 10,
			"needTime": 0,
			"needYield": 0,
			"saveState": 0,
			"restoreState": 0,
			"isEOF": 1,
			"invalidates": 0,
			"docsExamined": 10,
			"alreadyHasObj": 0,
			"inputStage": {
				"stage": "IXSCAN",
				"nReturned": 10,
				"executionTimeMillisEstimate": 0,
				"works": 11,
				"advanced": 10,
				"needTime": 0,
				"needYield": 0,
				"saveState": 0,
				"restoreState": 0,
				"isEOF": 1,
				"invalidates": 0,
				"keyPattern": {
					"student_id": 1
				},
				"indexName": "student_id_1",
				"isMultiKey": false,
				"isUnique": false,
				"isSparse": false,
				"isPartial": false,
				"indexVersion": 1,
				"direction": "forward",
				"indexBounds": {
					"student_id": [
						"[1.0, 1.0]"
					]
				},
				"keysExamined": 10,
				"dupsTested": 0,
				"dupsDropped": 0,
				"seenInvalidated": 0
			}
		},
		"allPlansExecution": []
	},
	"serverInfo": {
		"host": "IN1W7L-300705",
		"port": 27017,
		"version": "3.2.11",
		"gitVersion": "009580ad490190ba33d1c6253ebd8d91808923e4"
	},
	"ok": 1
}
```

To create compound index, use following command:
```javascript
> db.students.createIndex({student_id: 1, class_id: -1});
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 2,
	"numIndexesAfter" : 3,
	"ok" : 1
}
```

In Mongo server console, we see:
```
2017-02-04T14:16:38.415+0530 I INDEX    [conn3] build index on: school.students properties: { v: 1, key: { student_id: 1.0, class_id: -1.0 }, name: "studen
t_id_1_class_id_-1", ns: "school.students" }
2017-02-04T14:16:38.415+0530 I INDEX    [conn3]          building index using bulk method
2017-02-04T14:16:41.000+0530 I -        [conn3]   Index Build: 1115200/6172445 18%
2017-02-04T14:16:44.000+0530 I -        [conn3]   Index Build: 2416900/6172445 39%
2017-02-04T14:16:47.807+0530 I -        [conn3]   Index Build: 2557600/6172445 41%
2017-02-04T14:16:50.000+0530 I -        [conn3]   Index Build: 3549800/6172445 57%
2017-02-04T14:16:53.000+0530 I -        [conn3]   Index Build: 4870100/6172445 78%
2017-02-04T14:16:57.451+0530 I -        [conn3]   Index Build: 5115100/6172445 82%
2017-02-04T14:17:10.927+0530 I INDEX    [conn3]          done building bottom layer, going to commit
2017-02-04T14:17:11.050+0530 I INDEX    [conn3] build index done.  scanned 6172445 total records. 32 secs
2017-02-04T14:17:11.053+0530 I COMMAND  [conn3] command school.$cmd command: createIndexes { createIndexes: "students", indexes: [ { ns: "school.students", key: { student_id: 1.0, class_id: -1.0 }, name: "student_id_1_class_id_-1" } ] } keyUpdates:0 writeConflicts:0 numYields:0 reslen:98 locks:{ Global: { acquireCount: { r: 1, w: 1 } }, MMAPV1Journal: { acquireCount: { w: 12344894 } }, Database: { acquireCount: { W: 1 } }, Collection: { acquireCount: { W: 1 } }, Metadata: { acquireCount: { W: 11 } } } protocol:op_command 32641ms
```

******************************************************************************************************************************
