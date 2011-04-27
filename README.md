Janbanery - Java Kanbanery API Connector
========================================
<a href="https://kanbanery.com/" style="display:block; background:black;">
<img src="https://sckrk.kanbanery.com/images/kanbanery-logo-small.png?1303131477" alt="[kanbanery]" style="background:black;"/><br/>
</a>

<a href="https://kanbanery.com/">Kanbanery</a> is a web app for managing your projects Kanban Style (somewhat similiar to SCRUM, but less restrictive and strict).
Kanbanery has an web interface, as well as a iPad and iPhone version. The RESTful API is open so anyone can write his
favourite tool to be integrated with it - and that's what I'm doing. This connector will be used in the upcomming Intellij
IDEA plugin as well as the Android version of the app.


If you have any comments, feel free to open issues or contact me <a href="mailto:konrad.malawski@java.pl">via email</a>.

Get started
===========
Maven
-----
Janbanery is deployed to the OSS **Sonatype** Maven repository. To use it, just add the following repository to your `pom.xml`:

Use this repository for **STABLE** releases. 
By STABLE I mean the full API is covered, all methods are documented and the API will not have breaking changes with minor revision number updates.

```
<repository>
    <id>sonatype-releases</id>
    <name>Sonatype Releases</name>
    <url>https://oss.sonatype.org/content/repositories/releases/</url>
</repository>
```

Use the following repository for access to **SNAPSHOTS** these are very frequently updated and are "bleeding edge", so you may cut yourself 
when trusting that the API won't change. But as it's updated quicker, there are by far more features in it.

```
<repository>
    <id>sonatype-snapshots</id>
    <name>Sonatype Snapshots</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
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

Usage
=====
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

Queries
-------

Commands
--------

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
<img style="float:right; padding:3px; " src="https://github.com/ktoso/janbanery/raw/master/lgplv3-147x51.png" alt="GNU LGPL v3"/><br/>
I'm releasing this plugin under the **GNU Lesser General Public License 3.0**.

You're free to use it as you wish, the license text is attached in the LICENSE file.
You may contact me if you want this to be released on a different license, just send me an email <a href="mailto:konrad.malawski@java.pl">konrad.malawski@java.pl</a> :-)
