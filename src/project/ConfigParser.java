package project;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * ConfigParser Class
 * This class is used to read in all internal configuration parameters
 * @author Dustin Overmiller Justin Kambic
 */
public class ConfigParser 
{ 
   /* the XML Document object which parses and stores the information */
   private Document m_Doc;
   
   /**
   * ConfigParser(String fileName)
   * This is the constructor class that will read in the file and try to parse it
   * @param fileName - location of the file to be read in and parsed
   * @return ConfigParser - returns the new ConfigParser object
   */   
   public ConfigParser(String fileName)
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
   * getUser()
   * This function will look for the "USER" element in the xml file and return that text value
   * @param none
   * @return String - text value of the "USER" element or an empty string if the document could not be properly parsed
   */
   public String getUser()
   {
      /* initialize the title object */
      String user = "";
      /* make sure the document is valid */
      if (m_Doc == null)
      {
         return user;
      }
      
      /* get the children from the document */
      Node rootNode = m_Doc.getDocumentElement();
      NodeList children = rootNode.getChildNodes();
      
      /* get the user from the Document */
      for (int iI = 0; iI < children.getLength(); iI++)
      {
    	  Node childNode = children.item(iI);
    	  if (childNode.getNodeName().equals("USER"))
    	  {
    		  user = childNode.getFirstChild().getNodeValue();
    	  }
      }
      
      /* return the string */
      return user;
   }
   
   /**
   * getPassword()
   * This function will look for the "PASS" element in the xml file and return that text value
   * @param none
   * @return String - text value of the "PASS" element or an empty string if the document could not be properly parsed
   */
   public String getPassword()
   {
      /* initialize the title object */
      String pass = "";
      /* make sure the document is valid */
      if (m_Doc == null)
      {
         return pass;
      }
      
      /* get the children from the document */
      Node rootNode = m_Doc.getDocumentElement();
      NodeList children = rootNode.getChildNodes();
      
      /* get the password from the Document */
      for (int iI = 0; iI < children.getLength(); iI++)
      {
    	  Node childNode = children.item(iI);
    	  if (childNode.getNodeName().equals("PASS"))
    	  {
    		  pass = childNode.getFirstChild().getNodeValue();
    	  }
      }
      
      /* return the string */
      return pass;
   }
}
