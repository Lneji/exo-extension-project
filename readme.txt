About this Project
==================
This project builds an extension for the eXo Platform, and is only provided as an example demonstrate
the most basic possible example.

To Build
========
From folder where top level pom.xml is:
mvn package

To Deploy
=========
See eXo site. This maven project builds the WAR and JAR that eXo platform expects, per their documentation.
Basically, you just put the WAR in the tomcat webapps folder, and the JAR in the tomcat lib folder. And do the copies
without tomcat/eXo running and with an empty eXo database. Then just restart eXo, and go here:
http://localhost:8080/portal/mysite

Warning
=======
Due to apparent bug in eXo, you will need to deploy this extension into an empty (new) eXo database insallation.