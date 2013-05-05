package project;

import java.sql.*;
import java.util.*;
import java.io.*;

import javax.swing.JFileChooser;


/**
 * AddMovie Class
 * This class is the main class that will be used to parse a Movie XML File and add it to the database
 * @author Dustin Overmiller Justin Kambic
 */
public class AddMovie 
{
   /**
    * addMovie(String filename)
    * This is the main entry for adding movies to the database as outlined in the Project Specification
    * @params inputFile - the name/location of the file to add to the database
    * @return String - an html formatted string to display the results
    *
   */
   public static String addMovie(String inputFile)
   {
      /* initialize the user and password strings for the database connection */
      String user = "";
      String pass = ""; 
      	  
	  /* initialize the return String */
	  String result = "";
	  
	  /* make sure the input file is an xml file */
	  if (inputFile.indexOf(".xml") == -1)
	  {
         result += "<font color=\"red\"> Error: Proper XML file needs to be specified</font></br>";
         return result;
      }  		
	  
	  /* format the output file */
	  String filename = "../../movies/" + inputFile.substring(inputFile.lastIndexOf("/")+1);	  
	  String outputFile = filename.substring(filename.lastIndexOf("/")+1);
	        
      /* make sure the filename is not blank */
      if (filename == "")
      {
         result += "<font color=\"red\"> Error: Filename needs to be specified</font></br>";
         return result;
      }  
      
      /* read in the username and password from the configuration file */
      ConfigParser oConfigParser = new ConfigParser("Config.xml");
      
      /* make sure the parser was able to read the file */
      if (!oConfigParser.parsedFile())
      {
         result += "<font color=\"red\"> Error: Could not read the configuration file</font></br>";
         return result;
      }
      
      /* fill in the user and password variables */
      user = oConfigParser.getUser();
      pass = oConfigParser.getPassword();
      
      /* make sure that the variables are not empty */
      if (user == "")
      {
         result += "<font color=\"red\"> Error: Could not get the username from configuration</font></br>";
         return result;
      }
      else if (pass == "")
      {
         result += "<font color=\"red\"> Error: Could not get the password from configuration</font></br>";
         return result;
      }
      
      /* read in the Movie XML File from the input */
      MovieParser oMovieParser = new MovieParser(filename);
      
      /* make sure the parser was able to read the file */
      if (!oMovieParser.parsedFile())
      {
         result += "<font color=\"red\"> Error: Could not correctly parse " + outputFile + "</font></br>";
         return result;
      }

      /* create the connection to the database */
      Connection conn = DbConnection.getConnection(user, pass);
	  
	  if (conn == null)
	  {
	     result += "<font color=\"red\"> Error: Could not establish a connection to the database</font></br>";
         return result;
      }
      
      /* make sure the Database tables are created */
      if (!Database.createDbTables(conn))
	  {
	     result += "<font color=\"red\"> Error: Could not create the Database Tables</font></br>";
         return result;
      }
      
      /* Add the movie file to the database */
      if (!Database.addMovie(conn, oMovieParser))
      {
         result += "<font color=\"red\"> Error: Could not add " + outputFile + " to the database</font>";
         return result;
      }  
      
      /* Close the database connection */
      DbConnection.closeConnection();
      
      /* Successfully added the movie entry to the database */
      result += "<font color=\"green\"> Successfully added " + outputFile + " to the Database</font>";
      return result;   
   }
     
}
