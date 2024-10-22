# Meet Datomic: the immutable and functional database.

> "When you combine two pieces of data you get data. When you combine two machines you get trouble." - Rich Hickey presenting [The Functional Database](https://www.infoq.com/presentations/datomic-functional-database/).

Concepts focused on functional programming, mainly on immutability, have been increasingly present in our daily lives, therefore, nothing more fair than getting to know a database whose philosophy is the immutability of data, bringing control of facts in a format completely different from what we are used to.

In this article, we will get to know Datomic, which has this name precisely because it brings data in a format slightly different from the conventional one, seeking to bring data immutability closer to the database level, with a functional approach focused to work well with distributed systems.

## Table of Contents
- [What is Datomic?](#what-is-datomic)
- [Architecture](#architecture)
- [Data structure](#data-structure)
- [How a transaction works?](#how-a-transaction-works)
- [Conclusion](#conclusion)

## What is Datomic?
At the beginning of 2012, the Relevance team (later joining Metadata to form Cognitec), together with Rich Hickey, launched Datomic, which they began working on in 2010, with the main motivation being to transfer a substantial part of the power assigned to database servers for application servers, so that the programmer would have more programming power with data within the application logic.

Datomic Cloud was released in early 2018 using Amazon's components:
- DynamoDB, EFS, EBS and S3 as storage services;
- CloudFormation for deployment;
- AWS Cloudwatch for logging, monitoring, and metrics.

Cognitect (the company previously responsible for developing Datomic) was acquired by Nubank in 2020, and Nubank announced in April 2023 that the Datomic binaries are publicly available and free to use (this means that its Pro version is now free to use).

Written in Clojure, Datomic works a little differently from the databases we are used to using, being used to manage data, but not store it. We'll go through more details about its architecture, but in short, it means that Datomic can use several other data storage services to store transactions, even other databases, which can result in a nice combination.

### Concept
The main operating concept of Datomic is that the data is immutable as a whole. An interesting analogy for you to imagine and understand how it works a little better is:
- Imagine a database that you are used to working with, such as PostgreSQL or MySQL;
- Imagine now that you have two tables, a product table, but you also have a log table for these products, storing every modification that was made to the original product table;
- When you update an item, this item has its data modified in the product table, but we add its previous value to the log table, highlighting what was done (in our case an update on its value);
- For Datomic, there would only be the product "table" (in our case [schema](https://docs.datomic.com/pro/schema/schema.html)), with an additional column, indicating whether that item is true or not, so, when we update the product value, a new line would be added with the new value, however, the old line now has the check column value set to false, after all, it is no longer true at the current time.

This means that past data continues to exist, but there is this representation that indicates whether the value of the product is valid or not. These lines are called facts, so when the word "fact" is mentioned, remember this analogy.

One point to highlight: remember that no matter how much the value of the product has been changed, it remains a fact - no longer valid for the current time, however, it is still a fact that occurred, after all, the product in the past had this value.

## Architecture
To better understand how everything works within Datomic, we first need to better understand how its architecture works.

### How can I store data?
As previously mentioned, Datomic is not characterized by storing data as a database in the same way as we are used to, being used mainly to "transact and manage data". You can combine Datomic in different ways, such as:
- SQL databases (such as PostgreSQL and MySQL);
- DynamoDB (if you choose to use Datomic Cloud, the Indexes will be stored in S3, Transaction Log in DynamoDB and Cache in EFS);
- Cassandra and Cassandra2;
- Dev mode, storing data in memory.
How it works is very simple: depending on your choice, a table will be created within your database in order to store all the data, which Datomic will manage.

### Peers
Every type of interaction that an application will make with Datomic will be through a Peer, responsible for carrying out a small cache control, assembling and executing queries, bringing indexing data in addition to sending commits. Peers can be used in two main ways, namely:
- Peer Library: a [library](https://mvnrepository.com/artifact/com.datomic/peer) to be added to your dependencies that will always be working with your application, after all, it is through it that you will carry out any type of action with your database;
- Peer Server: being used mainly with the Datomic in Cloud format, your application now has only a [Client library](https://mvnrepository.com/artifact/com.datomic/client-cloud), responsible for communicating with the Peer Server that will perform direct actions with Datomic, as well as the Peer Library.

Peers work with a given database value for an extended period of time without concern. These values are immutable and provide a stable, consistent view of data for as long as a needs one, functioning as a kind of "snapshot" of the database to allow data to be returned in a more practical way without overloading with multiple queries in real time . This is a marked difference from relational s which require work to be done quickly using a short-lived. You can see more about the description of how Peers work in [official documentation](https://docs.datomic.com/pro/overview/architecture.html#storage-services).

An important point to highlight is: if you are using the Peer Library, each node of your application, each service will have a Peer running alongside it, therefore, this means that in the process of a distributed system, these multiple Peers will be responsible for sending commits to your database, however, in a distributed system it is extremely important to control data consistency, after all, if I have multiple services sending queries in parallel, how can I control that the data is truthful and correctly passes a race condition? Well, to understand this better, let's understand what Transactor is.

### Transactor
The *Transactor* is responsible for transacting the received commits and storing data within our database. The architecture of an application with Datomic is characterized by having multiple Peers working and sending commits (after all, we will have multiple services), but only a single Transactor, guaranteeing total data consistency. This means that regardless of whether the Transactor is receiving **n** commits per second, they will all be queued to guarantee total consistency.

The main point of this architectural format comes from understanding that dealing with concurrency in general, allowing multiple data to be stored in parallel in our database, especially in case of a distributed system, could negatively affect the consistency of stored data, being a drastic problem.

Now, we can look in more detail at a diagram that demonstrates how this entire architecture behaves:
![Datomic architecture](https://docs.datomic.com/pro/images/clientarch_orig.svg)

Note in the diagram above that the Transactor is responsible for all activities that require direct communication with the data storage service used, in addition to controlling data indexing formats, working with a memcached cluster, and responding to commits. Thus, we can state that Datomic deals with [ACID](https://www.databricks.com/glossary/acid-transactions) transactions, an acronym that refers to the set of 4 key properties that define a transaction: **Atomicity , Consistency, Isolation, and Durability**.

### Storage Services
> "Peers read facts from the Storage Services. The facts the Storage Service returns never change, so Peers do extensive caching. Each Peer's cache represents a partial copy of all the facts in the database. The Peer cache implements a least-recently used policy for discarding data, making it possible to work with databases that won't fit entirely in memory. Once a Peer's working set is cached, there is little or no network traffic for reads." - from [Datomic Pro Documentation](https://docs.datomic.com/pro/overview/architecture.html#storage-services).

Storage Services can be configured however you like, all you need to do is create a properties file (called `transactor.properties`) to represent how your Transactor will be created and managed.

So, if you use PostgreSQL, for example, you will have to configure the driver that will be used, the connection url, username and password, and you can also configure values for `memory-index-threshold`, `memory-index-max` , `object-cache-max`, `read-concurrency` and even `write-concurrency`, ultimately creating a table named `datomic_kvs` within your PostgreSQL:
```sql
CREATE TABLE datomic_kvs (
  id text NOT NULL,
  rev integer,
  map text,
  val bytea,
  CONSTRAINT pk_id PRIMARY KEY (id)
) WITH (OIDS=FALSE);
```

## Data structure
Well, we've already talked about the architecture of how Datomic as a whole works, so now let's better visualize what the basis of Datomic's data structure is like, starting with *Datoms*.

### Datoms
> _"A **datom** is an immutable atomic fact that represents the addition or retraction of a relation between an entity, an attribute, a value, and a transaction."_

So basically a datom is a simple fact in log, representing data changes of a relation. We can express a datom as a five-tuple:
- an entity id (E)
- an attribute (A)
- a value for the attribute (V)
- a transaction id (Tx)
- a boolean (Op) indicating whether the datom is being added or retracted

All of these are from [Datomic Cloud documentation](https://docs.datomic.com/cloud/whatis/data-model.html#datoms). Look at the example below:

| E   | 42                   |
| --- | -------------------- |
| A   | :user/favorite-color |
| V   | :blue                |
| Tx  | 1234                 |
| Op  | true                 |

### Entities
> *"A Datomic entity provides a lazy, associative view of all the information that can be reached from a Datomic entity id."*

Looking into an [Entity](https://docs.datomic.com/pro/javadoc/datomic/Entity.html) we can visualize as a table:

| E   | A                    | V      | Tx   | Op    |
| --- | -------------------- | ------ | ---- | ----- |
| 42  | :user/favorite-color | :blue  | 1234 | true  |
| 42  | :user/first-name     | "John" | 1234 | true  |
| 42  | :user/last-name      | "Doe"  | 1234 | true  |
| 42  | :user/favorite-color | :green | 4567 | true  |
| 42  | :user/favorite-color | :blue  | 4567 | false |

The *Transaction Id* can be visualized as a point of the time which represents that data. In the example above we have `1234` and `4567`. Look at `1234`... In the first row, the `:user/favorite-colour` attribute has the value `:blue`, with `op` as `true`. But, in the future, at  `4567` now the attribute has the `op` set to false for the attribute with the value `:blue` (now `:green` is set for true).

For us, we haven't changed manually the `Op`. Datomic automatically made this when we updated the value for `:user/favorite-color`. That means: Datomic automatically manage our data and set or update values, and we have the exactly point in time which the `:user/favorite-color` have been changed.

### Schemas
As the [documentation](https://docs.datomic.com/cloud/schema/defining-schema.html) says: *Attributes are defined using the same data model used for application data. That is, attributes are themselves defined by entities with associated attributes.*

Well, for defining a new attribute we need to define:
- `:db/ident`, a name that is unique within the database
- `:db/cardinality`, specifying whether entities can have one or a set of values for the attribute
- `:db/valueType`, the type allowed for an attribute's value
- `:db/doc` (optional), the attribute's description/documentation

Look, all of these `:db/ident`, `:db/cardinality` and etc are only simple entities which pointer to each other. They are automatically generated by Datomic in the initial stage. This means: they have a default `entity id`.

## How a transaction works?
> *"Every transaction in Datomic is its own entity, making it easy to add facts about why a transaction was added (or who added it, or from where, etc.)"*

We have "two options" for transactions: `add` or `retraction`. Every transaction returns the transaction id and the database state before and after the transaction. The forms can be:
```clojure
[:db/add entity-id attribute value]
[:db/retract entity-id attribute value]
```
> How we saw before: every transaction occur in a queued mode. 

If a transaction completes successfully, data is committed to the database and we have a transaction report returned as a map with the following keys:

| key        | usage                                  |
|------------|----------------------------------------|
| :db-before | database value before the transaction  |
| :db-after  | database value after the transaction   |
| :tx-data   | datoms produced by the transaction     |
| :tempids   | map from temporary ids to assigned ids |
> The database value is like a "snapshot" from the database, as we saw before.

Let's see an example of how `:db/add` works. Look at the example below:
```clojure
;; We have this schema
{:internal/id ...
 :internal/value 0
 :internal/key " "}

;; Making a simple transaction
[[:db/add id :internal/value 1]]
;; This will update the value...

;; But, we can perform multiple
;; transactions, look:
[[:db/add id :internal/value 1]
 [:db/add id :internal/key "another"]]
;; It will work fine.

;; But, when we perform something like:
[[:db/add id :internal/value 1]
 [:db/add id :internal/value 2]]
;; We will have a conflict
```

The conflict occur when we have a change in the same entity with the same attribute. That's make sense, because we can't have a fact updating multiple times in the same time lapse.

A cool fact: if we perform a multiple transaction they occur in parallel (with multiple processing). This is secure because as we saw before, the same attribute can't be updated in the same transaction.

## Conclusion

This article and the beginning of this series of articles aims to introduce Datomic and present its various possibilities and advantages for general use. It is important to highlight that the official Datomic documentation is excellent, therefore, for further in-depth research it is extremely important that you use it! And of course, if you want to take your first steps with Datomic, feel free to use Getting Started from the official documentation, but if you want a repository with the codes used, I've made a [repository available](https://github.com/lanjoni/datomic-getting-started) on my GitHub (don't forget to give a star)!
