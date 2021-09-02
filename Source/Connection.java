import java.lang.Comparable;
import java.util.Objects;

public class Connection implements Comparable<Object>
{
    // Attributes
    private Laptop laptop;
    private String username;

    // Constructors
    public Connection(Laptop laptop, String username)
    {
	this.laptop = laptop;
	this.username = username;
    }

    // Setters and Getters
    public Laptop getLaptop()
    {
	return laptop;
    }

    public void setLaptop(Laptop laptop)
    {
	this.laptop = laptop;
    }

    public String getUsername()
    {
	return username;
    }

    public void setUsername(String username)
    {
	this.username = username;
    }

    // Must-Have Methods
    public boolean equals(Object o)throws IllegalArgumentException
    {
	/*
	 * Method equals(Object o) shows if two connections are exactly the same.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * connection.
	 * 
	 * Postcondition: Method returns true if all the attributes of this instance and
	 * "o" are equal (exactly the same value).
	 */
	if (o instanceof Connection)
	    return (Objects.equals(username, ((Connection) o).getUsername())
		    && Objects.equals(laptop, ((Connection) o).getLaptop()));
	else throw new IllegalArgumentException("Object is not a conenction therefore it cannot be equal");
    }

    @Override
    public int compareTo(Object o)
    {
	/*
	 * Method compareTo(Object o) shows how different two objects of the same
	 * instance are based on their state.
	 * 
	 * Precondition: Method expects to receive an Object "o" that is an instance of
	 * a connection object.
	 * 
	 * Postcondition: Method returns an int value. The value is negative if the
	 * calling instance is considered inferior to the argument and the opposite
	 * applies. Should both instances be equal, "0" is returned.
	 * 
	 * Note: What is considered "Inferior" or "Superior" (giving a bigger or smaller
	 * value) depends on the code decided.
	 * 
	 */
	return this.username.compareTo(((Connection) o).getUsername())
		+ this.laptop.compareTo(((Connection) o).getLaptop());
    }

    @Override
    public String toString()
    {
	/*
	 * method toString() prints all of the attributes values on the console.
	 * 
	 * Precondition:None.
	 * 
	 * Postcondition: All of the instances attributes values are printed in the
	 * console.
	 * 
	 */
	return "Connection [Laptop = " + laptop + ", Username of User = " + username + "]";
    }

}
