package project;

import java.sql.*;
import java.util.*;


/**
 * Database Class
 * This class is used to create the relational database, and store the data in the database
 * @author Dustin Overmiller Justin Kambic
 */
public class Database 
{ 
   /* SQL statement to create the Actor Table */
   /* name - Unique String Upper(FirstName + LastName) which is a primary key */
   /* FirstName - String which contains the Actor's first name */
   /* LastName - String which contains the Actor's last name */
   private static final String ACTOR_TABLE = "CREATE TABLE ACTOR ("
   + " name VARCHAR2(100), FirstName VARCHAR2(50), LastName VARCHAR2(50), CONSTRAINT actor_pk PRIMARY KEY (name))";

   /* SQL statement to create the Movie Table */
   /* title - String for the Movie's title and is a primary key */
   /* year - Integer containing the Movie's release year and is a primary key */
   /* Director - multivalued attribute containing the String of Directors and is comma seperated */
   /* Genre - multivalued attribute containing the String of Genres and is comma seperated */
   private static final String MOVIE_TABLE = "CREATE TABLE MOVIE ( "
      + "title VARCHAR2(100), year NUMBER(4), Director VARCHAR2(2000), Genre VARCHAR2(2000), CONSTRAINT movie_pk PRIMARY KEY (title, year))";
   
   /* SQL statment to create the Movie_Actor Table */
   /* This table is a many to many table which maps Actors to Movies and Movies to Actors */
   /* name - Unique ID which is a foreign key to the ACTOR Table */
   /* title - String for the Movie's title and is a foreign key to the MOVIE Table */
   /* year - Integer containing the Movie's release year and is a foreign key to the MOVIE Table */
   private static final String MOVIE_ACTOR_TABLE = "CREATE TABLE MOVIE_ACTOR ( "
      + "name VARCHAR2(100), title VARCHAR2(100), year NUMBER(4), " 
	  + "CONSTRAINT actor_fk FOREIGN KEY (name) REFERENCES ACTOR(name) ON DELETE CASCADE, "
	  + "CONSTRAINT movie_fk FOREIGN KEY (title, year) REFERENCES MOVIE(title, year) ON DELETE CASCADE)";

   /**
   * createDbTables(String lastname)
   * This function will see if the database tables exist and if not it will create them
   * @param con - Connection object to the Oracle Database
   * @return boolean - Returns true if the tables are created successfully and false otherwise
   */
   public static boolean createDbTables(Connection con)
   {
      /* make sure the connection has been established */
      if (con == null)
      {
         return false;
      }
      
      /* see if the tables have already been created */
      try
      {
	      DatabaseMetaData dbm = con.getMetaData();
	      Statement stmt;
	      
	      /* check to see if the Actor table is there */
	      ResultSet tables = dbm.getTables(null, null, "ACTOR", null);
	      if (!tables.next()) 
	      {
	         /* need to create the Actor table */
	         stmt = con.createStatement();
	         stmt.execute(ACTOR_TABLE);
	         stmt.close();
	      }
	         
	      /* check to see if the Movie table is there */
	      tables = dbm.getTables(null, null, "MOVIE", null);
	      if (!tables.next())
	      {
	         /* need to create the Movie table */
	         stmt = con.createStatement();
	         stmt.execute(MOVIE_TABLE);
	         stmt.close();
	      }
	         
	      /* check to see if the Movie_Actor table is there */
	      tables = dbm.getTables(null, null, "MOVIE_ACTOR", null);
	      if (!tables.next())
	      {
	         /* need to create the Movie_Actor table */
	         stmt = con.createStatement();
	         stmt.execute(MOVIE_ACTOR_TABLE);
	         stmt.close();
	      }
      }
      catch (Exception e)
      {
    	  return false;
      }
   
      /* operations completed successfully */
      return true;
   }
   
   /**
   * addMovie(Connection con, MovieParser oParser)
   * This function will take the Database Connection and the Movie XML Parser and insert the data from the XML into the Database if it does not already exist
   * @param Connection con - The connection to the Oracle Database
   * @param MovieParser oParser - The Movie XML parsing object that stores all of the XML Data
   * @return boolean - This function will return true if the data was successfully added to the database and false otherwise
   */
   public static boolean addMovie(Connection con, MovieParser oParser)
   {
      /* make sure the Parser object is not null */
      if (oParser == null)
      {
         return false;
      }
      
      /* go through and get the data from the movie xml parser and insert it into the database */
      String title = oParser.getMovieTitle().replaceAll("'", "''");
      String genre = oParser.getGenres().replaceAll("'", "''");
      String directors = oParser.getDirectors().replaceAll("'", "''");
      int year = oParser.getMovieYear();
      
      if (title == "" || year == -1)
      {
         /* error getting the movie title or year */
         return false;
      }
      
      /* see if the entry already exists in the Movie database */
      try
      {
	      Statement stmt = con.createStatement();
	      String query = "SELECT title, year FROM Movie WHERE title = '" + title + "' AND year = " + year;
	      ResultSet rset = stmt.executeQuery(query);
	      if (!rset.next())
	      {
	         /* add the record to the table */
	         String insertStmt = "INSERT INTO Movie (title, year, Director, Genre) VALUES ('" + title + "', '" + year + "', '" + directors + "', '" + genre + "')";
	         stmt.executeUpdate(insertStmt);
	      }
	      
	      /* see if the actor list or Movie to Actor mapping table needs to be updated */
	      ArrayList actorList = oParser.getActorList();
	      for (int iI = 0; iI < actorList.size(); iI++)
	      {
	         Actor actor = (Actor)actorList.get(iI);
	         
	         /* see if the actor is already in the Actor table */
			 String firstname = actor.getFirstName().replaceAll("'", "''");
			 String lastname = actor.getLastName().replaceAll("'", "''");
	         query = "SELECT name FROM Actor WHERE name = '" + firstname.toUpperCase() + lastname.toUpperCase() + "'";
	         rset = stmt.executeQuery(query);
	            
	         if (!rset.next())
	         {
	            /* add the record to the table */
	            String insertStmt = "INSERT INTO Actor (name, FirstName, LastName) VALUES ('" + firstname.toUpperCase() + lastname.toUpperCase() + 
	               "', '" + firstname + "', '" + lastname + "')";
	            stmt.executeUpdate(insertStmt);
	         }
	            
	         /* see if the Movie to Actor mapping is already in the Movie_Actor table */
	         query = "SELECT name, title, year FROM Movie_Actor WHERE name = '" + firstname.toUpperCase() + lastname.toUpperCase() + "' AND title = '" +
	            title + "' AND year = " + year;
	         rset = stmt.executeQuery(query);
	         
	         if (!rset.next())
	         {
	            /* add the record to the table */
	            String insertStmt = "INSERT INTO Movie_Actor (name, title, year) VALUES ('" + firstname.toUpperCase() + lastname.toUpperCase() + 
	               "', '" + title + "', '" + year + "')";
	            stmt.executeUpdate(insertStmt);
	         }            
	      }
	           
	      stmt.close();
      }
      catch (Exception e)
      {
    	  return false;
      }
      
      /* everything completed successfully */
      return true;
   }
   
   /**
   * performQuery(Connection con, String queryStmt)
   * This function will take the Database Connection and the query string, query the database, and return the results
   * @param Connection con - The connection to the Oracle Database
   * @param String queryStmt - The String containing the query statment
   * @return ResultSet - This function will return the list of results from the query
   */
   public static ResultSet performQuery(Connection con, String queryStmt)
   {
      /* create the statment object */
	  try
	  {
	      Statement stmt = con.createStatement();
	      /* execute the query */
	      ResultSet rset = stmt.executeQuery(queryStmt);
	      /* return the results */
	      return rset;
	  }
	  catch (Exception e)
	  {
		  return null;
	  }
   }
}
