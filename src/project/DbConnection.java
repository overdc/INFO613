package project;

import java.sql.*;
import java.util.*;


/**
 * DbConnection Class
 * This class is used to establish the connection between JDBC and Oracle
 * @author Dustin Overmiller Justin Kambic
 */
public class DbConnection 
{
   /* define the private member variables */ 
   private static String m_URL = "jdbc:oracle:thin:@oracle.cis.drexel.edu:1521:orcl";
   private static Connection m_Con = null;

   
   /**
   *  getConnection(String user, String pass)
   *  This function takes in the user name and password and will establish the connection with the Oracle Database
   *  @param user    The username to connect with
   *  @param pass    The password of the user to connect with
   *  @return Connection      The connection to the database or null if the connection failed
   *  
   */
   public static Connection getConnection(String user, String pass)
   {
      /* see if the connection was already made */
      if (m_Con == null)
      {
         try
         {
            /* make the connection to the database using JDBC */
        	 Class.forName("oracle.jdbc.driver.OracleDriver");
        	 m_Con = DriverManager.getConnection(m_URL,user,pass);   
         }
         catch (Exception e)
         {
            /* failed to create the connection */
            m_Con = null;
         }
      }
      
      /* return the connection */
      return m_Con;   
   }
   
   /**
   *  closeConnection()
   *  This function closes the connection with the Oracle Database
   *  @param none
   *  @return none
   *  
   */
   public static void closeConnection()
   {
      /* see if the connection exists */
      if (m_Con != null)
      {
         try
         {
            /* close the connection */    
            m_Con.close();
         }
         catch (Exception e)
         {
            /* nothing to do here since the connection failed to close */
         }
         /* set the object back to null */
         m_Con = null;
      }
   }
   
   
}
