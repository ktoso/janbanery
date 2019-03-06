Janbanery - Java Kanbanery API Connector
========================================
Kanbanery
---------
<a href="https://kanbanery.com/" style="display:block; background:black;">
<img src="https://sckrk.kanbanery.com/images/kanbanery-logo-small.png?1303131477" alt="[kanbanery]" style="background:black;"/><br/>
</a>

<a href="https://kanbanery.com/">Kanbanery</a> is a web app for managing your projects Kanban Style (somewhat similiar to SCRUM, but less restrictive and strict).
Kanbanery has an web interface, as well as a iPad and iPhone version. The RESTful API is open so anyone can write his
favourite tool to be integrated with it - and that's what I'm doing. This connector will be used in the upcomming Intellij
IDEA plugin as well as the Android version of the app.

Janbanery
---------
**Janbanery** is a **Java library** that provides a **fluent** and **typesafe** (no magic strings etc) API to access 100% of Kanbanery's features.
Well, in fact, even above 100% (sic!) as we've added some filtering and mass operations if you'd want to count those as "atomic from the users perspective".

Feel free to use this library in any project integrating Kanbanery with your app or tool. Also, please share any thoughts about it and request new features where you'd see some possible cool things.

If you have any comments, feel free to open issues or contact me <a href="mailto:konrad.malawski@java.pl">via email</a>.

Get started
===========
Maven
-----
Janbanery is deployed to the OSS **Sonatype** Maven repository. To use it, just add the following repository to your `pom.xml`:

Use this repository for **STABLE** releases. 
By STABLE I mean the full API is covered, all methods are documented and the API will not have breaking changes with minor revision number updates.

```
<repositories>
    <repository>
        <id>sonatype-releases</id>
        <name>Sonatype Releases</name>
        <url>https://oss.sonatype.org/content/repositories/releases/</url>
    </repository>
</repositories>

<dependencies>
    <!-- ... -->

    <dependency>
        <groupId>pl.project13.janbanery</groupId>
        <artifactId>janbanery</artifactId>
        <version>1.0</version>
    </dependency>

</dependencies>
```

Use the following repository for access to **SNAPSHOTS** these are very frequently updated and are "bleeding edge", so you may cut yourself 
when trusting that the API won't change. But as it's updated quicker, there are by far more features in it.

```
<repositories>
    <repository>
        <id>sonatype-snapshots</id>
        <name>Sonatype Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
</repositories>

<dependencies>
    <!-- ... -->

    <dependency>
        <groupId>pl.project13.janbanery</groupId>
        <artifactId>janbanery</artifactId>
        <version>1.0</version>
    </dependency>

</dependencies>
```

Download the JAR
----------------
Or simply download the jar version of this project.

Clone or Fork me!
-----------------
You may also want to *fork* or just do a clone *clone* this repository, to compile it from source and maybe add some patches to it.
To do so, first just get the source (by `git clone` for example) and run `mvn install`.

Be sure to let me know via **pull requests** and **opening issues** that you found some kind of bug, would like to add a new feature etc.
Be sure the Kanbanery API does allow it though - not all Entities can be created via the API for example.

Usage examples
==============
These are just a "quick start", if you need more help or convicting why you should use Janbanery take a look at our Wiki, where all flows are described (soon!).

Logging in
----------
*Easy as goo pie.* To start coding with **Janbanery**, I've prepared a nice factory for you, here's how you may use it:

You can connect to Kanbanery using your API key which can be found **on your account settings page** ( the url is https://kanbanery.com/users/YOUR_USER_ID/api ).

```java
String mySecretApiKey = "abcabcabcabcabcabcabcabcabc";

Janbanery janbanery = new JanbaneryFactory().connectUsing(apiKey);
```

This would be the easiest method, but there's more. You may log in using your username and password, if you want to allow users of your app to easily log in this is the way to go.

```java
Janbanery janbanery = new JanbaneryFactory().connectUsing(username, password);
```

Notice that the above call **will fetch the API KEY for this user and all consequent calls will use the more secure API KEY method instead of sending the password around in the internet!** :-)
If you really want to keep using username/pass authentification though, here's how:

```java
Janbanery janbanery = new JanbaneryFactory().connectAndKeepUsing(user, password);
```

Also, since Janbanery has quite some dependencies inside, the factory allows you to mock any thing out or replace it with your implementation simply by:

```java
AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
JanbaneryFactory janbaneryFactory = new JanbaneryFactory(asyncHttpClient);
```

That's all nice and cool, but the above methods would require you to search by hand for your passwords or even worse, keep them in your sourcefiles.
That's why I prepared a **PropertiesConfiguration** class for you, you may use it like this:

```java
Configuration configuration = new PropertiesConfiguration();
Janbanery janbanery = janbaneryFactory.connectUsing(configuration);
```

This method will seach your resources for a file named *janbanery.properties*, which should look like this:

```
apikey=fghfghfghfghfghfghfghfghfghfghfghfgh
username=example@example.com
password=example
```

And when found it will determine if it should user the user/pass method or apikey. If both of these are set, the API key method will be used, if no API KEY is set, the username/password properties will be used.
You may also pass the path to the properties file explicitely in the constructor of PropertiesConfiguration().

Some examples
-------------
Just to make you get the feel of Janbanery let's look at some examples right now:

**Ok, what projects can I access?**

```java
    List<Project> projects = janbanery.projects()
                                       .all();

    List<Project> projects = janbanery.usingWorkspace("janbanery").projects()
                                       .all();

    Project project = janbanery.projects().byName("janbaneryProject");

    List<Project> projects = janbanery.projects()
                                       .allAcrossWorkspaces();
```

**Get my User**

```java
    User me = janbanery.users()
                       .current();
```

**Take a look at the possible Task Types**

```java
    List<TaskType> all = janbanery.taskTypes()
                                  .all();

    TaskType any = janbanery.taskTypes()
                            .any();
```

**You can also work on columns**

```java
    Column column = new Column.Builder("Testing").capacity(5).build();

    Column last = janbanery.columns().last();
    Column beforeLast = janbanery.columns().before(last);

    column = janbanery.columns().create(column).after(beforeLast);

    janbanery.columns().create(column).beforeLast();
    janbanery.columns().move(column).toPosition(5);
```

**And here's how you could use the flows if you wanted to**

```java
    Task task = janbanery.tasks().create(task)
                         .assign().to(me) // task flow
                         .move().toNextColumn() // task movement flow
                         .move().toNextColumn() // task movement flow
                         .mark().asReadyToPull() // task flow
                         .get(); // task
```

**And there's much more...!**

* Tasks
* TaskSubscriptions
* TaskTypes
* SubTasks
* Estimates
* Comments
* Issues
* Columns
* Workspaces
* Projects
* ProjectMemberships
* Permissions

All ready to be used, so just *start coding your Kanbanery integration right now*!


Exceptions
----------
There are two kinds of exceptions, those who inherit from **KanbaneryException** and those that don't.
The first kind of exceptions are thrown by Kanbanery (serverside), so for example your Task title may be too long etc,
these exceptions are mapped back to Janbanery as appropriate exceptions and the response information from the server will
be passed allong with it so you know how to deal with it, for example:

```
pl.project13.janbanery.exceptions.kanbanery.InvalidEntityKanbaneryException: 422 - Unprocessable Entity
{"title":["Title must not be blank"],"task_type_id":["must be set"]}
	at pl.project13.janbanery.core.RestClient.verifyResponseCode(RestClient.java:79)
	at pl.project13.janbanery.core.dao.TasksImpl.create(TasksImpl.java:57)
```
So they're quite useful - please don't ignore them :-)

The other kind of exceptions are problems with the client side of the API, aby wait timeouts etc...

Links and resources
===================

* <a href="https://kanbanery.com">Kanbanery.com</a>
* Full <a href="http://support.kanbanery.com/entries/506142-api-version-1-2">Kanbanery REST API description (v1.2)</a>
* <a href="http://www.blog.project13.pl">blog.project13.pl</a> - my blog, feel free to comment about this library there

License
=======
<img style="float:right; padding:3px; " src="https://github.com/ktoso/janbanery/raw/master/feather-small.gif" alt="Apache License 2.0"/><br/>
I'm releasing this library under the **Apache License 2.0**.

You're free to use it as you wish, the license text is attached in the LICENSE file.
You may contact me if you want this to be released on a different license, just send me an email <a href="mailto:konrad.malawski@java.pl">konrad.malawski@java.pl</a> :-)
