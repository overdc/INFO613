Justin Kambic
Dustin Overmiller
INFO613
Project

This document outlines the installation procedure for setting up the INFO613 Database Project.

List of files included in this delivery:
README.txt
INFO613_Overmiller_Kambic.tar.gz

List of files included within INFO613_Overmiller_Kambic.tar.gz:
INFO613_Overmiller_Kambic/Actor.java
INFO613_Overmiller_Kambic/AddMovie.java
INFO613_Overmiller_Kambic/AddMovie.jsp
INFO613_Overmiller_Kambic/ConfigParser.java
INFO613_Overmiller_Kambic/Config.xml
INFO613_Overmiller_Kambic/Config.xsd
INFO613_Overmiller_Kambic/Database.java
INFO613_Overmiller_Kambic/DbConnection.java
INFO613_Overmiller_Kambic/FirstQuery.java
INFO613_Overmiller_Kambic/FirstQuery.jsp
INFO613_Overmiller_Kambic/MovieParser.java
INFO613_Overmiller_Kambic/movies/
INFO613_Overmiller_Kambic/movies/movies.dtd
INFO613_Overmiller_Kambic/SecondQuery.java
INFO613_Overmiller_Kambic/SecondQuery.jsp
INFO613_Overmiller_Kambic/setupProject.sh

To install the project perform the following steps:

1) Log into linux.cis.drexel.edu with your username and password

2) Copy the INFO613_Overmiller_Kambic.tar.gz file to your home directory

3) Change your current directory to your home directory, if needed
	>> cd ~

4) Untar the INFO613_Overmiller_Kambic.tar.gz file
	>> tar xzf INFO613_Overmiller_Kambic.tar.gz

5) Run the installer script to setup the system.
   The USERNAME should be the username you use to connect to the database (without @ike)
   The PASSWORD should be the password you use with that username to connect to the database
	>> cd INFO613_Overmiller_Kambic
	>> ./setupProject.sh USERNAME PASSWORD

6) You can now go to http://linux.cis.drexel.edu:8080/~USERNAME to see the project work.
   
   There will be three files: AddMovie.jsp, FirstQuery.jsp, and SecondQuery.jsp.
   There will also be one directory present: movies with the movies.dtd file within it.

   If you select the FirstQuery.jsp file it will display a list of movie titles and genres currently in 
   the database that were directed by Steven Spielberg and will show the query used to obtain those results.
   
   If you select the SecondQuery.jsp file it will display a list of movie titles and genres currently in 
   the database that were acted by Julia Roberts and will show the query used to obtain those results.

   If you select the AddMovie.jsp file it will allow you to select a movie XML file to add to the database.
   If successful it will add it to the database and upload the file to the movies subdirectory that 
   was in this current directory. If there is a failure, the page will report this failure.

An example project can be found at http://linux.cis.drexel.edu:8080/~dco28.
This project already has some movies added to the database such that both queries will produce results.

That is all you need to do to run the Database project. If you have any questions you can send us an email:
	Justin Kambic: jek5104@gmail.com
	Dustin Overmiller: overmillerdc@gmail.com



