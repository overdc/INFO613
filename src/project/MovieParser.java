package project;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * MovieParser Class
 * This class is used to pass in the movie xml file and abstract the parsing of the information from the file
 * @author Dustin Overmiller Justin Kambic
 */
public class MovieParser 
{ 
   /* the XML Document object which parses and stores the information */
   private Document m_Doc;
   
   /**
   * MovieParser(String fileName)
   * This is the constructor class that will read in the file and try to parse it
   * @param fileName - location of the file to be read in and parsed
   * @return MovieParser - returns the new MovieParser object
   */
   public MovieParser(String fileName)
   {
      /* create the document object */
      try
      {
    	  ClassLoader classLoader = getClass().getClassLoader();
		  InputStream XmlStream = classLoader.getResourceAsStream(fileName); 
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		  m_Doc = dBuilder.parse(XmlStream);
		  m_Doc.getDocumentElement().normalize();
      }
      catch (Exception e)
      {
         /* error trying to create the Document object */
         m_Doc = null;
      }
   }
   
   /**
   * parsedFile()
   * This function can be called after the constructor to see if the file was successfully parsed
   * @param none
   * @return boolean - Returns true if the file was successfully parsed and false otherwise
   */
   public boolean parsedFile()
   {
      if (m_Doc == null)
      {
         return false;
      }
      
      return true;
   }
   
   /**
   * getMovieTitle()
   * This function will look for the "Title" element in the xml file and return that text value
   * @param none
   * @return String - text value of the "Title" element or an empty string if the document could not be properly parsed
   */
   public String getMovieTitle()
   {
      /* initialize the title object */
      String title = "";
      /* make sure the document is valid */
      if (m_Doc == null)
      {
         return title;
      }
      
      /* get the children from the document */
      Node rootNode = m_Doc.getDocumentElement();
      NodeList children = rootNode.getChildNodes().item(1).getChildNodes();
      
      /* get the Title from the Document */
      for (int iI = 0; iI < children.getLength(); iI++)
      {
    	  Node childNode = children.item(iI);
    	  if (childNode.getNodeName().equals("Title"))
    	  {
    		  title = childNode.getFirstChild().getNodeValue();
    	  }
      }
      
      /* return the string */
      return title;
   }
   
   /**
   * getMovieYear()
   * This function will look for the "Year" element in the xml file and return that integer value
   * @param none
   * @return int - int value of the "Year" element or -1 if the document could not be properly parsed
   */
   public int getMovieYear()
   {
      /* initialize the year object */
      int year = -1;
      if (m_Doc == null)
      {
         return year;
      }
      /* get the Year from the Document */
      try
      {
    	  /* get the children from the document */
          Node rootNode = m_Doc.getDocumentElement();
          NodeList children = rootNode.getChildNodes().item(1).getChildNodes();
          
          /* get the Year from the Document */
          for (int iI = 0; iI < children.getLength(); iI++)
          {
        	  Node childNode = children.item(iI);
        	  if (childNode.getNodeName().equals("Year"))
        	  {
        		  year = Integer.parseInt(childNode.getFirstChild().getNodeValue());
        	  }
          }
      }
      catch(Exception e)
      {
         /* format was not a number */
      }
      
      /* return the value */
      return year;
   }
   
   /**
   * getDirectors()
   * This function will look for the "Director" elements in the xml file and returns a comma seperated string containing all directors
   * @param none
   * @return String - String value of the "Director" elements or the empty string if the document could not be properly parsed
   */
   public String getDirectors()
   {
      /* initialize the directors object */
      String directors = "";
      
      /* make sure the document is valid */
      if (m_Doc == null)
      {
         return directors;
      }
      
      /* get the children from the document */
      Node rootNode = m_Doc.getDocumentElement();
      NodeList children = rootNode.getChildNodes().item(1).getChildNodes();
      
      /* search for the Directed_By element in the Document */
      for (int iI = 0; iI < children.getLength(); iI++)
      {
    	  Node childNode = children.item(iI);
    	  if (childNode.getNodeName().equals("Directed_By"))
    	  {
    		  /* found the Directed_By element so look for the list of directors */
    		  NodeList directorNodeList = childNode.getChildNodes();
    		  for (int jJ = 0; jJ < directorNodeList.getLength(); jJ++)
    		  {
    			  Node directorNode = directorNodeList.item(jJ);
    			  if (directorNode.getNodeName().equals("Director"))
    			  {
    				  if (directors.length() > 1)
    				  {
    					  /* not the first element in the list so add a comma */
    					  directors += ", ";
    				  }
    				  directors += directorNode.getFirstChild().getNodeValue();
    			  }
    		  }
    	  }
      }
      
      /* return the comma separated list */
      return directors;
   }
   
   /**
   * getGenres()
   * This function will look for the "Genre" elements in the xml file and returns a comma seperated string containing all genres
   * @param none
   * @return String - String value of the "Genre" elements or the empty string if the document could not be properly parsed
   */
   public String getGenres()
   {
      /* initialize the genres object */
      String genres = "";
      
      /* make sure the document is valid */
      if (m_Doc == null)
      {
         return genres;
      }
      
      /* get the children from the document */
      Node rootNode = m_Doc.getDocumentElement();
      NodeList children = rootNode.getChildNodes().item(1).getChildNodes();
      
      /* search for the Genres element in the Document */
      for (int iI = 0; iI < children.getLength(); iI++)
      {
    	  Node childNode = children.item(iI);
    	  if (childNode.getNodeName().equals("Genres"))
    	  {
    		  /* found the Genres element so look for the list of genre elements */
    		  NodeList genreNodeList = childNode.getChildNodes();
    		  for (int jJ = 0; jJ < genreNodeList.getLength(); jJ++)
    		  {
    			  Node genreNode = genreNodeList.item(jJ);
    			  if (genreNode.getNodeName().equals("Genre"))
    			  {
    				  if (genres.length() > 1)
    				  {
    					  /* not the first element in the list so add a comma */
    					  genres += ", ";
    				  }
    				  genres += genreNode.getFirstChild().getNodeValue();
    			  }
    		  }
    	  }
      }
      
      /* return the comma separated list */
      return genres;
   }
   
   /**
   * getGenres()
   * This function will look for the "Actor" elements in the xml file and returns a list of all actors containing first and last names
   * @param none
   * @return ArrayList<Actor> - List of Actor objects (containing first and last names) from the "Actor" elements or an empty list if the document could not be parsed properly
   */
   public ArrayList getActorList()
   {
      /* initialize the list object */
      ArrayList actorList = new ArrayList();
      
      /* make sure the document is valid */
      if (m_Doc == null)
      {
         return actorList;
      }
      
      /* get the children from the document */
      Node rootNode = m_Doc.getDocumentElement();
      NodeList children = rootNode.getChildNodes().item(1).getChildNodes();
      
      /* search for the Cast element in the Document */
      for (int iI = 0; iI < children.getLength(); iI++)
      {
    	  Node childNode = children.item(iI);
    	  if (childNode.getNodeName().equals("Cast"))
    	  {
    		  /* found the Cast element so look for the list of Actor elements */
    		  NodeList actorNodeList = childNode.getChildNodes();
    		  for (int jJ = 0; jJ < actorNodeList.getLength(); jJ++)
    		  {
    			  Node actorNode = actorNodeList.item(jJ);
    			  if (actorNode.getNodeName().equals("Actor"))
    			  {
    				  String firstName = "";
    				  String lastName = "";
    				  /* found an Actor element so get the first and last name elements */
    				  NodeList nameNodeList = actorNode.getChildNodes();
    				  for (int kK = 0; kK < nameNodeList.getLength(); kK++)
    				  {
    					  Node nameNode = nameNodeList.item(kK);
    					  if (nameNode.getNodeName().equals("FirstName"))
    					  {
    						  firstName = nameNode.getFirstChild().getNodeValue();
    					  }
    					  else if (nameNode.getNodeName().equals("LastName"))
    					  {
    						  lastName = nameNode.getFirstChild().getNodeValue();
    					  }    					  
    				  }
    				  if (!firstName.equals("") && !lastName.equals(""))
    				  {
    					  Actor actor = new Actor(firstName, lastName);
    					  actorList.add(actor);
    				  }
    			  }
    		  }
    	  }
      }
      
      /* return the list of actors */
      return actorList;
   }

}
