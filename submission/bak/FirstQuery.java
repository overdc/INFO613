package project;

import java.sql.*;
import java.util.*;
import java.io.*;


/**
 * FirstQuery Class
 * This class is the main class that will be used to answer the first query
 * @author Dustin Overmiller Justin Kambic
 */
public class FirstQuery 
{
   /**
    * doQuery
    * This is the main entry for the first query as outlined in the Project Specification
    * @params none
    * @return String - an html formatted string containing the results for the jsp file
    *
   */
   public static String doQuery()
   {
      /* initialize the user and password strings for the database connection */
      String user = "";
      String pass = "";
	  
	  /* initialize the string to be returned */
	  String result = "";
      
      /* read in the username and password from the configuration file */
      ConfigParser oConfigParser = new ConfigParser("./Config.xml");
      
      /* make sure the parser was able to read the file */
      if (!oConfigParser.parsedFile())
      {
         result += "<font color=\"red\"> Error: Could not read the configuration file </font></br>";
         return result;
      }
      
      /* fill in the user and password variables */
      user = oConfigParser.getUser();
      pass = oConfigParser.getPassword();
      
      /* make sure that the variables are not empty */
      if (user == "")
      {
         result += "<font color=\"red\"> Error: Could not get the username from configuration </font></br>";
         return result;
      }
      else if (pass == "")
      {
         result += "<font color=\"red\"> Error: Could not get the password from configuration </font></br>";
         return result;
      }

      /* create the connection to the database */
      Connection conn = DbConnection.getConnection(user, pass);
	  
	  if (conn == null)
	  {
	     result += "<b><font color=\"red\"> Error: Could not establish a connection to the database</font></b></br>";
         return result;
      }

      /* Create the query statement */
      String queryStmt = "SELECT title, Genre, Director FROM Movie WHERE Director LIKE '%Steven Spielberg%'";
      
      /* get the results from the query */
      ResultSet rset = Database.performQuery(conn, queryStmt);
      
      /* display the results */
      result += "Results from the query: <b><font color=\"blue\">" + queryStmt + "</font></b></br></br>";
      if (rset != null)
      {
    	  try
    	  {
		      while (rset.next())
		      {
		         result += "<b>Movie Title: " + rset.getString(1) + "</br>";
		         result += "Movie Genres: " + rset.getString(2) + "</b></br></br>";
		      } 
    	  }
    	  catch (Exception e)
    	  {
    		  result += "Exception:</br>";
    		  result += e.getMessage() + "</br>";
    	  }
      }
      
      /* Close the database connection */
      DbConnection.closeConnection();

      return result;
   }
     
}
