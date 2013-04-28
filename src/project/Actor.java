package project;

/**
 * Actor Class
 * This class will be used to store the first and last names of actors
 * @author Dustin Overmiller Justin Kambic
 */
public class Actor 
{
   /* these are the private member variables for the class */
   private String m_firstName;
   private String m_lastName;
   
   /**
   * Actor(String firstname, String lastname)
   * This is the constructor for the class that will take in and store the first and last names of the actor
   * @param firstname - String value of the Actor's first name
   * @param lastname - String value of the Actor's last name
   * @return Actor - an Actor object containing these values
   */
   public Actor(String firstname, String lastname)
   {
      m_firstName = firstname;
      m_lastName = lastname;
   }
   
   /**
   * getFirstName()
   * This function will return the value stored for the first name
   * @param none
   * @return String - returns the value of the first name member variable
   */
   public String getFirstName()
   {
      return m_firstName;
   }
   
   /**
   * setFirstName(String firstname)
   * This function will set the value stored for the first name
   * @param firstname - String to set the first name member variable to
   * @return none
   */
   public void setFirstName(String firstname)
   {
      m_firstName = firstname;
   }
   
   /**
   * getLastName()
   * This function will return the value stored for the last name
   * @param none
   * @return String - returns the value of the last name member variable
   */
   public String getLastName()
   {
      return m_lastName;
   }
   
   /**
   * setLastName(String lastname)
   * This function will set the value stored for the last name
   * @param lastname - String to set the last name member variable to
   * @return none
   */
   public void setLastName(String lastname)
   {
      m_lastName = lastname;
   }
     
}
