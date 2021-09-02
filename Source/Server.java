import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Comparable;

public class Server implements Comparable<Object>
{

    // Attributes
    private ListInterface connections;
    private ListInterface users;
    private ListInterface availableIP;
    // The Constant "SIZE" can be changed to any int value to control how many IP's
    // are to be generated in a Class C network.
    final int SIZE = 5;

    // Constructors
    public Server()
    {
	connections = new ReferenceBasedList();
	availableIP = new ReferenceBasedList();
	users = new ReferenceBasedList();
	//
	for (int i = 1; i <= SIZE; i++)
	{
	    String temp = "192.168.10.";
	    availableIP.append(temp += i);
	}
    }

    // Setters and getters
    public ListInterface getConnections()
    {
	return connections;
    }

    public void setConnections(ListInterface connections)
    {
	this.connections = connections;
    }

    public ListInterface getUsers()
    {
	return users;
    }

    public void setUsers(ListInterface users)
    {
	this.users = users;
    }

    public ListInterface getAvailableIP()
    {
	return availableIP;
    }

    public void setAvailableIP(ListInterface availableIP)
    {
	this.availableIP = availableIP;
    }

    // Methods
    public void addIP(String ip)
    {
	/*
	 * Method addIP(String ip) adds a new IP into the Servers list of available IPs.
	 * 
	 * Precondition: Method expects to receive a String "ip" with no special
	 * formatting checks being performed or expected.
	 * 
	 * Postcondition: As a result the String "ip" is inserted into the ListInterface
	 * of "avaliableIP".
	 * 
	 */
	availableIP.insert(ip);
    }

    public String allocateIP() throws ListIsEmptyException
    {
	/*
	 * Method allocateIP() returns a IP from the available IPs the Server has.
	 * 
	 * Precondition: None.
	 * 
	 * Postcondition: Method returns a newly removed String from the ListInterface
	 * "availableIP".
	 * 
	 * Note:Exception is thrown if the ListInterface "availableIP" is empty.
	 * 
	 */
	if (availableIP.isEmpty()) throw new ListIsEmptyException("No IP available");

	return (String) availableIP.removeFront();
    }

    public void addUser(String username, String password) throws IllegalArgumentException
    {
	/*
	 * Method addUser(String username, String password) adds a new user/Account to
	 * the Server.
	 * 
	 * Precondition:Method expects to receive String "username" and "password" where
	 * the "username' has not been used to create the another account before. No
	 * special formatting checks are performed or expected.
	 * 
	 * Postcondition:A new User object is added into the "users" ListInterface and
	 * nothing is returned.
	 * 
	 * Note:Exceptions are thrown if the parameters are null or the User already
	 * exists.
	 */
	// checks for nulls
	if (username == null || password == null) throw new IllegalArgumentException("Argument is null");
	// stores the trimmed versions of the strings temporarily
	String tempUsername = username.trim();
	String tempPassword = password.trim();
	// checks if the field was empty
	if (tempUsername == "" || tempPassword == "") throw new IllegalArgumentException("Field is empty");
	// adds the username and password trimmed
	users.addSorted(new User(tempUsername, tempPassword));

    }

    public boolean removeUser(String username, String password)
	    throws IllegalArgumentException, UserAlreadyLoggedInException
    {
	/*
	 * Method removeUser(String username, String password) removes an existing
	 * account from the server.
	 * 
	 * Precondition: Method expects to receive String "username" and "password" that
	 * have been used before together to create an existing account. No special
	 * formatting checks are performed or expected.
	 * 
	 * Postcondition:As a result method returns true if the Account has been found
	 * and deleted from the "Users" ListInterface and false if not.
	 * 
	 * Note: Exception is thrown if the parameters are null.
	 * 
	 */
	// checks if the username or password are null for future developers.
	if (username == null || password == null) throw new IllegalArgumentException("Argument is null");
	// stores the trimmed versions of the strings temporarily
	username = username.trim();
	password = password.trim();
	if (users.isEmpty())
	{
	    throw new ListIsEmptyException("There are no users to remove");
	}

	if (this.isUserAvailable(username))
	{
	    return (users.removeSpecific(new User(username, password)));
	}
	else
	{
	    throw new UserAlreadyLoggedInException("User is still logged in");
	}

    }

    public boolean userLogin(String username, String password)
	    throws IllegalArgumentException, ListIsEmptyException, UserAlreadyLoggedInException
    {
	/*
	 * Method accountLogin(String username, String password) logs in into a
	 * account/user, granting access to the server.
	 * 
	 * Precondition: Method expects to receive String "username" and "password" that
	 * have been used to create an existing account. No special formatting checks
	 * are performed or expected.
	 * 
	 * Postcondition: Method returns true if the account created from the arguments
	 * already exists on the server and if not then false.
	 * 
	 * Note:Method throws an exception if one of the arguments is null or if the
	 * "Users" ListInterface is empty. Finally should the newly created account
	 * given from the String arguments already be logged in a exception is thrown.
	 */
	// checks if the username or password are null for future developers.
	if (username == null || password == null) throw new IllegalArgumentException("Argument is null");
	// stores the trimmed versions of the strings
	username = username.trim();
	password = password.trim();
	// checks if the list is empty
	if (users.isEmpty()) throw new ListIsEmptyException("Their are no users to log into");
	// checks if the user exists and then if their already logged in it throws an
	// exception otherwise it connects and returns false
	if (!this.isUserAvailable(username))
	{
	    throw new UserAlreadyLoggedInException("User is already logged in");
	}
	return users.exists(new User(username, password));
    }

    public boolean isUserAvailable(String username)throws IllegalArgumentException
    {
	/*
	 * Method isUserAvailable(String username) shows if a user is still available or
	 * used in a connection
	 * 
	 * Precondition: Method expects to receive a String "username" that will be the
	 * username of a user.
	 * 
	 * Postcondition Method returns true if the user with the username argument
	 * provided is not found in any connetion objects of the "connections" list,
	 * otherwise false is returned.
	 * 
	 * Note:Expetions are thrown if the argument provided is null and the method
	 * always returns true when the "connections" list is empty.
	 * 
	 */
	// checks if the username or password are null for future developers.
	if (username == null) throw new IllegalArgumentException("Argument is null");
	// stores the trimmed version of the string
	username = username.trim();
	if (connections.isEmpty()) return true;

	ListInterface tempList = new ReferenceBasedList();
	Connection connection;
	boolean found = true;
	int numTraverses = connections.size();

	for (int i = 1; i <= numTraverses; i++)
	{

	    connection = (Connection) connections.removeFront();
	    tempList.append(connection);
	    if ((connection.getUsername().equals(username)))
	    {
		found = false;
		break;
	    }
	}

	if (tempList.size() < connections.size() && !found)
	{

	    while (!tempList.isEmpty())
	    {
		connections.insert(tempList.removeLast());
	    }
	    return found;
	}
	else if (tempList.size() >= connections.size() && !found)
	{
	    while (!connections.isEmpty())
	    {
		tempList.append(connections.removeLast());
	    }
	}

	this.setConnections(tempList);
	return found;
    }

    public void viewConnections() throws ListIsEmptyException
    {
	/*
	 * Method viewConnections() Displays all of the connections the Server has.
	 * 
	 * Precondition: None.
	 * 
	 * Postcondition: All of the elements of the ListInterface "connections" are
	 * printed on the console. Nothing is returned.
	 * 
	 * Note: If the ListInterface "connections" is empty then an exception is
	 * thrown.
	 */
	if (!connections.isEmpty())
	    connections.printList();
	else throw new ListIsEmptyException("Server has no Connections");
    }

    public void addConnection(Laptop Laptop, String username) throws IllegalArgumentException
    {
	/*
	 * Method: addConnetion(Laptop Laptop, String username) adds new connection to
	 * the Server
	 * 
	 * Precondition:Method expects to receive an Laptop called "Laptop" and String
	 * "username"
	 * 
	 * Postcondition:As a result a new Connection object is created with both
	 * parameters and appended into the "connections" ListInterface. Nothing is
	 * returned.
	 * 
	 * Note:If one of the arguments are null an exception is thrown.
	 */

	if (Laptop == null || username == null) throw new IllegalArgumentException("Argument is null");
	// stores the trimmed version of the string
	username = username.trim();
	connections.append(new Connection(Laptop, username));
    }

    public boolean removeConnection(Laptop laptop) throws ListIsEmptyException, IllegalArgumentException
    {
	/*
	 * Method: removeConnection(Laptop Laptop) removes a servers connection to the
	 * provided Laptop.
	 * 
	 * Precondition: Method expects to receive Laptop "Laptop" that has a connection
	 * with the Server instance.
	 * 
	 * Postcondition: Method returns true if it successfully found and removed the
	 * connection object in ListInterface "connections" with the specific Laptop
	 * given, otherwise false is returned.
	 * 
	 * Note:If the "connections" ListInterface is empty or if one of the arguments
	 * is null an exception is thrown
	 */

	ListInterface tempList = new ReferenceBasedList();
	Connection connection;
	boolean found = false;
	int numTraverses = connections.size();

	if (connections.isEmpty())
	    throw new ListIsEmptyException("The server does not have a connection to this laptop");

	if (laptop == null) throw new IllegalArgumentException("Argument is null");

	for (int i = 1; i <= numTraverses; i++)
	{

	    connection = (Connection) connections.removeFront();
	    if ((connection.getLaptop().equals(laptop)))
	    {
		availableIP.append(laptop.getIpAddress());
		found = true;
		break;
	    }
	    else
	    {
		tempList.append(connection);
	    }

	}

	if (tempList.size() < connections.size() && found)
	{

	    while (!tempList.isEmpty())
	    {
		connections.insert(tempList.removeLast());
	    }
	    return found;
	}
	else if (tempList.size() >= connections.size() && found)
	{
	    while (!connections.isEmpty())
	    {
		tempList.append(connections.removeLast());
	    }
	}

	this.setConnections(tempList);
	return found;
    }

    public boolean ping(String input) throws ListIsEmptyException, IllegalArgumentException
    {
	/*
	 * Method: ping(String input) server tries to ping all of its connected Laptops
	 * with the given input to find if a specific one is connected is connected
	 * 
	 * Precondition:Method expects to receive String "input" that will be the IP
	 * address or hostname of another Laptop. No special formatting checks are
	 * performed.
	 * 
	 * Postcondition: As a result method returns true if the Laptop is connected to
	 * the server or false.
	 * 
	 * Note:Excpetion is thrown if the "connections" ListInterface is empty or if
	 * any of the arguments is null
	 */
	ListInterface tempList = new ReferenceBasedList();
	boolean found = false;
	Connection connection;
	int numTraverses = connections.size();

	if (connections.isEmpty()) throw new ListIsEmptyException("The server does not have any connections");

	if (input == null) throw new IllegalArgumentException("Argument is null");
	
	input = input.trim();
	for (int i = 1; i <= numTraverses; i++)
	{

	    connection = (Connection) connections.removeFront();
	    tempList.append(connection);
	    if ((connection.getLaptop().getHostname().equals(input)
		    || connection.getLaptop().getIpAddress().equals(input)))
	    {
		found = true;
		break;
	    }
	}

	if (tempList.size() < connections.size() && found)
	{

	    while (!tempList.isEmpty())
	    {
		connections.insert(tempList.removeLast());
	    }
	    return found;
	}
	else if (tempList.size() >= connections.size() && found)
	{
	    while (!connections.isEmpty())
	    {
		tempList.append(connections.removeLast());
	    }
	}

	this.setConnections(tempList);
	return found;
    }

    public boolean hasConnection(Laptop laptop)throws IllegalArgumentException
    {
	/*
	 * Method hasConnection(Laptop Laptop) Server checks if it has a Connection with
	 * a specific Laptop.
	 * 
	 * Precondition:Method expects to receive a Laptop "Laptop".
	 * 
	 * Postcondition:As a result the method returns true if the Laptop "Laptop" has
	 * a connection with the instance by going through all the connections/elements
	 * the instance has in the ListInterface "connections".
	 * 
	 * Note:An exception is thrown if the argument is null or if the "connections"
	 * ListInterface is empty.
	 * 
	 */
	ListInterface tempList = new ReferenceBasedList();
	Connection connection;
	boolean found = false;
	int numTraverses = connections.size();

	if (laptop == null) throw new IllegalArgumentException("Argument is null");

	if (connections.isEmpty()) return false;

	for (int i = 1; i <= numTraverses; i++)
	{

	    connection = (Connection) connections.removeFront();
	    tempList.append(connection);
	    if ((connection.getLaptop().equals(laptop)))
	    {
		found = true;
		break;
	    }
	}

	if (tempList.size() < connections.size() && found)
	{

	    while (!tempList.isEmpty())
	    {
		connections.insert(tempList.removeLast());
	    }
	    return found;
	}
	else if (tempList.size() >= connections.size() && found)
	{
	    while (!connections.isEmpty())
	    {
		tempList.append(connections.removeLast());
	    }
	}
	this.setConnections(tempList);
	return found;
    }

    public void writeToFile(String fileName) throws IllegalArgumentException, IOException
    {
	/*
	 * Method writeToFile(String fileName) writes all the elements of the "Users"
	 * ListInterface into a file location given.
	 * 
	 * Precondition: Method expects to receive String "filename" that will contain a
	 * directory to any permitted file in windows.
	 * 
	 * Postcondition: As a result the method if needed creates or overwrites an
	 * existing file as named in the String "filename" with a new file containing
	 * the "Users" ListInterface elements written inside.
	 * 
	 * Note:If the Directory given by String "filename" is incorrect an exception is
	 * thrown
	 */
	User temp;

	if (fileName == null)
	{
	    throw new IllegalArgumentException("Argument is null");
	}

	/* Create a FileWriter object that handles the low-level details of writing */
	FileWriter theFile = new FileWriter(fileName);

	/* Create a PrintWriter object to wrap around the FileWriter object */
	/* This allows the use of high-level methods like println */
	PrintWriter fileOut = new PrintWriter(theFile);

	/* Print some lines to the file using the println method */
	for (int i = 1; i <= users.size(); i++)
	{
	    temp = (User) users.get(i);
	    fileOut.println(temp.getUsername());
	    fileOut.println(temp.getPassword());
	}

	/* Close the file so that it is no longer accessible to the program */
	fileOut.close();

	/* Handle the exception thrown by the FileWriter methods */
    }

    public ListInterface readFromFile(String fileName) throws IllegalArgumentException, IOException
    {
	/*
	 * Method readFromFile(String filename) reads a file from the directory given
	 * and creates a ReferenceBasedList containing a list of Users.
	 * 
	 * Precondition:Method expects to receive String "filename" containing a
	 * directory to a file to be read.
	 * 
	 * Postcondition:As a result the method returns a ListInterface of
	 * ReferencebasedList containing objects of type account with their attibutes
	 * being taken from the file.
	 * 
	 * Note: The odd lines of the file contains the Users String "username" and
	 * the even lines "password", this proccess repeats until there are no more
	 * lines to read.
	 * 
	 */

	if (fileName == null) throw new IllegalArgumentException("Argument is null");

	String oneLine;
	String twoLine;
	ListInterface tempAccountsList = new ReferenceBasedList();

	/* Create a FileWriter object that handles the low-level details of reading */
	FileReader theFile = new FileReader(fileName);

	/* Create a BufferedReader object to wrap around the FileWriter object */
	/* This allows the use of high-level methods like readline */
	BufferedReader fileIn = new BufferedReader(theFile);

	/* Read the first line of the file */
	oneLine = fileIn.readLine();
	twoLine = fileIn.readLine();

	/* Read the rest of the lines of the file and output them on the screen */
	while (oneLine != null && twoLine != null) /* A null string indicates the end of file */
	{
	    tempAccountsList.addSorted(new User(oneLine, twoLine));
	    oneLine = fileIn.readLine();
	    twoLine = fileIn.readLine();
	}
	/* Close the file so that it is no longer accessible to the program */
	fileIn.close();

	/* Handle the exception thrown by the FileReader methods */
	return tempAccountsList;
    }

    public boolean equals(Object o)throws IllegalArgumentException 
    {
	/*
	 * Method equals(Object o) shows if two servers are exactly the same.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * server.
	 * 
	 * Postcondition: Method returns true if all the attributes of this instance and
	 * "o" are equal (exactly the same value).
	 */
	if (o instanceof Server)
	    return ((Server) o).getUsers().equals(users) && ((Server) o).getConnections().equals(connections)
		    && ((Server) o).getAvailableIP().equals(availableIP);
	else throw new IllegalArgumentException("Object is not a server therefore it cannot be equal");
    }

    public void print()
    {
	/*
	 * method print() prints all of the lists values on the console, only their
	 * memory addresses because they are lists.
	 * 
	 * Precondition:None.
	 * 
	 * Postcondition: All of the lists values are printed in the console.
	 * 
	 */
	connections.printList();
	users.printList();
	availableIP.printList();
    }

    @Override
    public int compareTo(Object o)
    {
	/*
	 * Method compareTo(Object o) shows how different two objects of the same
	 * instance are based on their state.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of
	 * server.
	 * 
	 * Postcondition: Method returns an int value. The value is negative if the
	 * calling instance is considered inferior to the argument and the opposite
	 * applies. Should both instances be equal, "0" is returned.
	 * 
	 * Note: What is considered "Inferior" or "Superior" (giving a bigger or smaller
	 * value) depends on the code decided.
	 * 
	 */
	return connections.compareTo(((Server) o).getConnections()) + users.compareTo(((Server) o).getUsers())
		+ availableIP.compareTo(((Server) o).getAvailableIP());
    }

    @Override
    public String toString()
    {
	/*
	 * method toString() prints all of the attributes values on the console, only
	 * their memory addresses because they are lists.
	 * 
	 * Precondition:None.
	 * 
	 * Postcondition: All of the instances attributes values are printed in the
	 * console.
	 * 
	 */
	return "Server [connections=" + connections + ", Users=" + users + ", availableIP=" + availableIP + "]";
    }
}
