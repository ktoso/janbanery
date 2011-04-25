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
This project will be deployed to the **Sonatype** Maven repository when it's status reaches *BETA*.

Download the JAR
----------------
Or simply download the jar version of this project.
You may also consider building it from source by `git clone`-ing this project and running `mvn install`.

Usage
=====
Logging in
----------

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