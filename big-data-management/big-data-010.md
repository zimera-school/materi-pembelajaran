# Operasi Dasar MongoDB

## Operasi CRUD

```bash
> use wabi
switched to db wabi
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
> db.training.insert({name: "Apache Hadoop + MongoDB", duration: 10})
WriteResult({ "nInserted" : 1 })
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
wabi    0.000GB
> db.training.insert({
... name: "Corda Blockchain", duration: 15
... show dbs^C

> db.training.insert({
... name: "Corda Blockchain",
... duration: 15,
... trainer: "bpdp"
... })
WriteResult({ "nInserted" : 1 })
> db.training.find()
{ "_id" : ObjectId("5e65798563c03c90844a22be"), "name" : "Apache Hadoop + MongoDB", "duration" : 10 }
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
> db.training.insertOne(
... { name: "Apache Airflow for Data Engineering", duration: 10, trainer: "bpdp", discount: 10 }
... )
{
	"acknowledged" : true,
	"insertedId" : ObjectId("5e657e7063c03c90844a22c0")
}
> db.training.insertMany([
... { name: "TypeScript Programming", duration: 10, trainer: "bpdp" },
... { name: "Native Cloud App using Ballerina", duration: 15, trainer: "bpdp", discount: 15, weekend: "yes" }
... ])
{
	"acknowledged" : true,
	"insertedIds" : [
		ObjectId("5e657f2b63c03c90844a22c1"),
		ObjectId("5e657f2b63c03c90844a22c2")
	]
}
> db.training.find()
{ "_id" : ObjectId("5e65798563c03c90844a22be"), "name" : "Apache Hadoop + MongoDB", "duration" : 10 }
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657e7063c03c90844a22c0"), "name" : "Apache Airflow for Data Engineering", "duration" : 10, "trainer" : "bpdp", "discount" : 10 }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c1"), "name" : "TypeScript Programming", "duration" : 10, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c2"), "name" : "Native Cloud App using Ballerina", "duration" : 15, "trainer" : "bpdp", "discount" : 15, "weekend" : "yes" }
> db.training.find().limit(2)
{ "_id" : ObjectId("5e65798563c03c90844a22be"), "name" : "Apache Hadoop + MongoDB", "duration" : 10 }
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
> db.training.find({trainer:"bpdp"})
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657e7063c03c90844a22c0"), "name" : "Apache Airflow for Data Engineering", "duration" : 10, "trainer" : "bpdp", "discount" : 10 }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c1"), "name" : "TypeScript Programming", "duration" : 10, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c2"), "name" : "Native Cloud App using Ballerina", "duration" : 15, "trainer" : "bpdp", "discount" : 15, "weekend" : "yes" }
> db.training.update(
... { name: "Corda Blockchain" },
... { $set: {name: "Getting Started with Corda Blockchain"}}
... )
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.training.find()
{ "_id" : ObjectId("5e65798563c03c90844a22be"), "name" : "Apache Hadoop + MongoDB", "duration" : 10 }
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Getting Started with Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657e7063c03c90844a22c0"), "name" : "Apache Airflow for Data Engineering", "duration" : 10, "trainer" : "bpdp", "discount" : 10 }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c1"), "name" : "TypeScript Programming", "duration" : 10, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c2"), "name" : "Native Cloud App using Ballerina", "duration" : 15, "trainer" : "bpdp", "discount" : 15, "weekend" : "yes" }
> db.training.deleteOne({discount: 10})
{ "acknowledged" : true, "deletedCount" : 1 }
> db.training.find()
{ "_id" : ObjectId("5e65798563c03c90844a22be"), "name" : "Apache Hadoop + MongoDB", "duration" : 10 }
{ "_id" : ObjectId("5e657a2f63c03c90844a22bf"), "name" : "Getting Started with Corda Blockchain", "duration" : 15, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c1"), "name" : "TypeScript Programming", "duration" : 10, "trainer" : "bpdp" }
{ "_id" : ObjectId("5e657f2b63c03c90844a22c2"), "name" : "Native Cloud App using Ballerina", "duration" : 15, "trainer" : "bpdp", "discount" : 15, "weekend" : "yes" }
>
```


