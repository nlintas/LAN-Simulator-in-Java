import java.util.Objects;
import java.lang.Comparable;

public class Laptop implements Comparable<Object>
{
    // Attributes
    private String hostname;
    private String ipAddress;

    // Constructors
    public Laptop(String hostname)
    {

	this.hostname = hostname;
	ipAddress = null;
    }

    // Setters and Getters
    public String getHostname()
    {
	return hostname;
    }

    public void setHostname(String hostname)
    {
	this.hostname = hostname;
    }

    public String getIpAddress()
    {
	return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
	this.ipAddress = ipAddress;
    }

    public void disconnect(Server server) throws IllegalArgumentException, NotConnectedToServerException
    {
	/* Method disconnect(Server server) disconnects the laptop from a given server.
	 * 
	 * Precondition:Method expects to receive a Server object called "server" that this instance is 
	 * to disconnect from provided a connection exists to "server".
	 * 
	 * Postcondition:The String "ipAddress" is set to null and "server" removes its connection to 
	 * this instance. Nothing is returned.
	 * 
	 * Note:Should "server" be null or the instance not be connected to "server" an appropriate 
	 * exception is thrown. */

	if (server == null) throw new IllegalArgumentException("Server does not exist");

	if (!server.hasConnection(this)) throw new NotConnectedToServerException("Laptop is not connected to server");

	server.removeConnection(this);
	ipAddress = null;
    }

    public void connectTo(Server server) throws AlreadyConnectedToServerException, UserLoginFailedException, IllegalArgumentException, UserAlreadyLoggedInException
    {
	/* Method Connect(Server server) connects the laptop to a given server.
	 * 
	 * Precondition: Method expects to receive a Server object "server" that this instance is not connected to.
	 * The calling program is expected to  enter two Strings of "username" and "password" for account login 
	 * verification to be performed inside the "server".
	 * 
	 * Post-Condition:The instance has its String "ipAddress" set to a String value that "server" 
	 * returns and a connection is created in "server". Nothing is returned.
	 * 
	 * Note:Should the laptop already be connected or account login fail an exception is thrown. */

	String username;

	if (server == null) throw new IllegalArgumentException("Server does not exist");

	if (server.hasConnection(this)) throw new AlreadyConnectedToServerException("Laptop is already Connected to server");

	username = EasyIn.getStringWithMsg("Enter Username");

	if (!server.userLogin(username, EasyIn.getStringWithMsg("Enter Password")))
	    throw new UserLoginFailedException("Login failed");

	ipAddress = server.allocateIP();
	server.addConnection(this, username);
    }

    public boolean ping(Server server, String input) throws IllegalArgumentException, NotConnectedToServerException
    {
	/* Method ping(Server server, String input) ping's the given server with an IP or Host name given from the laptop.
	 * 
	 * Precondition:Method expects to receive a Server object "server" that the instance is connected to and a 
	 * String "input" with no attention to formatting.
	 * 
	 * Postcondition: Method returns true if "server" returns true for having a connection with the laptop based
	 * on the provided String "input". otherwise false is returned.
	 * 
	 * Note: Exceptions are thrown if the arguments are null or the instance does not have a connection to the server. */

	if (server == null || input == null) throw new IllegalArgumentException("Server does not exist");
	// stores the trimmed versions of the strings
	input = input.trim();
	if (!server.hasConnection(this)) throw new NotConnectedToServerException("Laptop is not connected to server");

	if (input.equals(hostname) || input.equals(ipAddress)) return true;

	return server.ping(input);
    }

    // Must-Have Methods
    @Override
    public String toString()
    {
	/* method toString() prints all of the attributes values on the console.
	 * 
	 * Precondition:None.
	 * 
	 * Postcondition: All of the instances attributes values are printed in the console. */
	
	return "Laptop [hostname = " + hostname + ", ipAddress = " + ipAddress + "]";
    }

    public boolean equals(Object o)throws IllegalArgumentException
    {
	/*
	 * Method equals(Object o) shows if two laptops are exactly the same.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * laptop.
	 * 
	 * Postcondition: Method return true if all the attributes of this instance and
	 * "o" are equal (exactly the same value).
	 */
	if(o instanceof Laptop)
	{
	    return (Objects.equals(hostname, ((Laptop) o).getHostname())
			&& Objects.equals(ipAddress, ((Laptop) o).getIpAddress()));
	}
	else throw new IllegalArgumentException("Object is not a laptop therefore it cannot be equal");
    }

    @Override
    public int compareTo(Object o)
    {
	/*
	 * Method compareTo(Object o) shows how different two objects of the same
	 * instance are based on their state.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * laptop.
	 * 
	 * Postcondition: Method returns an int value. The value is negative if the
	 * calling instance is considered inferior to the argument and the opposite
	 * applies. Should both instances be equal, "0" is returned.
	 * 
	 * Note: What is considered "Inferior" or "Superior" (giving a bigger or smaller
	 * value) depends on the code decided.
	 */
	
	if (this.getIpAddress() == null && ((Laptop) o).getIpAddress() == null)
	    return this.hostname.compareTo(((Laptop) o).getHostname());
	else return this.hostname.compareTo(((Laptop) o).getHostname())
		+ this.ipAddress.compareTo(((Laptop) o).getIpAddress());
    }

}
