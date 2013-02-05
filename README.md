LiveVoting
==========



LiveVoting is a software project, originally started as part of a german bachelor assignment called
"LiveVoting - Entwurf und Implementierung eines auf Zeichnungen ausgelegten Online-Abstimmungssystems"
by Alexander Dümont.

It started at the institute for computer science of [Freie Universität Berlin](http://www.mi.fu-berlin.de/inf/). The project was supervised
by [Prof. Dr. Margarita Esponda-Argüero](http://www.esponda.de/), who still has strong influence in the ongoing development.
The idea of LiveVoting and finally the software project itself was started due to the lack of productive
tools, which could help to improve the attention and participation of students and teachers during
collaboration lessens.


![LiveVoting](https://raw.github.com/a-d/livevoting/master/LiveVotingServer-webapp/src/main/webapp/logo.png)




Requirements
=========
* Java 1.7 JDK
* Maven (eg. m2eclipse)



Server Installation
=========
1) Pull the required java project(s) directories from the repostory.

* If you want to use your already installed Apache Tomcat Webserver you can go ahead and take the actual application.
 * LiveVotingServer-webapp

* If you want to build a standalone server, without Apache Tomcat, you are going to need all 3 directories.
 * LiveVotingServer
 * LiveVotingServer-server
 * LiveVotingServer-webapp

2) Import the project(s) to your (Eclipse) workspace.

* Make sure you use Java JDK, since the build operation will fail without that.
* If class-path errors appear, look whether Maven support is activated on all projects.
  If there are still errors, have Maven update the project dependencies.

3) Congratulation, you are ready to go.




Server Usage
=========

There are 3 different ways to actual build and start the server.

*1. Apache Tomcat war fle*
* Run the following Maven command in the "LiveVotingServer-webapp" project:
 * clean package
* The Apache Tomcat supported *war* file is generated to "LiveVotingServer-webapp/target/LiveVoting-webapp.war"

*2. Standalone*
* Run the following Maven command in the "LiveVotingServer" project:
 * clean packge
* That will compile both builds:
 * The Apache Tomcat supported *war* file is generated to "LiveVotingServer-webapp/target/LiveVoting-webapp.war".
 * The standalone server application is generated to "LiveVotingServer-server/target/LiveVoting-server-X.X.jar". You can start it by simply using "java -jar" as you know it.

*3. Local/debug startup*
* Run the following Maven command in the "LiveVotingServer-webapp" project:
 * jetty:run
* That will start an easy debugable instance of the current LiveVoting server. Use this setting for development.



Server Configuration
=========
There are some things which can be configured.
 * *LiveVotingServer-webapp/livevoting.server.properties* can be used to set up the local image cache directory.
  * *port* and *context path* settings in there are not supported yet.
 * To change the *context path* for the Apache Tomcat war file, just alter the *war* filename.
 * To change the *context path* for the stand alone application, create a file "jetty.properties" and enter settings for "jetty.port" and "jetty.context".
 * To change the *context path* for the local/debug startup, look into the LiveVotingServer-webapp/pom.xml, in one of the build-plugin tags is a changeable *contextPath* configuration.
