import java.lang.Comparable;
import java.util.Objects;

public class User implements Comparable<Object>
{
    // attributes
    private String username;
    private String password;

    // getters and setters
    public String getUsername()
    {
	return username;
    }

    public void setUsername(String username)
    {
	this.username = username;
    }

    public String getPassword()
    {
	return password;
    }

    public void setPassword(String password)
    {
	this.password = password;
    }

    // constructors
    public User(String username, String password)
    {
	this.username = username;
	this.password = password;
    }

    // Must-have Methods
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
	return "Users [username = " + username + ", password = " + password + "]";
    }

    public boolean equals(Object o)
    {
	/*
	 * Method equals(Object o) shows if two Users are exactly the same.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * user.
	 * 
	 * Postcondition: Method returns true if all the attributes of this instance and
	 * "o" are equal (exactly the same value).
	 */
	if (o instanceof User)
	{
	    return (Objects.equals(username, ((User) o).getUsername())
			&& Objects.equals(password, ((User) o).getPassword()));
	}
	else throw new IllegalArgumentException("Object is not a user therefore it cannot be equal");
    }

    @Override
    public int compareTo(Object o)
    {
	/*
	 * Method compareTo(Object o) shows how different two objects of the same
	 * instance are based on their state.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * user.
	 * 
	 * Postcondition: Method returns an int value. The value is negative if the
	 * calling instance is considered inferior to the argument and the opposite
	 * applies. Should both instances be equal, "0" is returned.
	 * 
	 * Note: What is considered "Inferior" or "Superior" (giving a bigger or smaller
	 * value) depends on the code decided.
	 * 
	 */
	return this.username.compareTo(((User) o).getUsername());
    }
}
