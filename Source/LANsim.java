import java.io.IOException;

public class LANsim
{

    public static void main(String[] args)
    {

	ListInterface laptops = new ReferenceBasedList();
	Laptop temp = null;
	Server server = null;
	int choice;

	do
	{
	    // Display Menu to user and present options to be chosen
	    displayMenu();
	    choice = EasyIn.getIntWithMsg("Hello user, please pick an option from 1 - 3\n");
	    switch (choice)
	    {

		case 1:
		// Server Menu
		do
		{

		    // Display server menu options to be selected
		    displayServer();
		    choice = EasyIn.getIntWithMsg("Please pick an option from 1 - 5\n");

		    switch (choice)
		    {

			case 1:
			// Server Execution
			if (server == null)
			{
			    server = new Server();
			    System.out.println("\nServer started\n");

			    try
			    {
				server.setUsers(server.readFromFile(
					System.getProperty("user.home") + "/Desktop/Accounts_DataBase.txt"));
			    }
			    catch (IOException e)
			    {
				System.out.println("Users file was not loaded\n");
			    }
			}
			else
			{
			    System.out.println("\nServer already running\n");
			}

			break;

			case 2:
			// Add user to server
			if (server == null)
			{
			    System.out.println("\nServer not running\n");
			}
			else
			{
			    try
			    {
				server.addUser(EasyIn.getStringWithMsg("Enter new Username\n"),
					EasyIn.getStringWithMsg("\nEnter new Password\n"));
				System.out.println("User added\n");
			    }
			    catch (Exception e)
			    {
				System.out.println(e.getMessage());
			    }
			}

			break;

			case 3:
			// Remove user from server
			if (server == null)
			{
			    System.out.println("\nServer not running\n");
			}
			else
			{
			    try
			    {
				if (server.removeUser(EasyIn.getStringWithMsg("Enter Username\n"),
					EasyIn.getStringWithMsg("\nEnter Password\n")))
				{
				    System.out.println("\nUser removed\n");
				}
				else
				{
				    System.out.println("\nUser not found\n");
				}
			    }
			    catch (Exception e)
			    {
				System.out.println(e.getMessage());
			    }
			}

			break;

			case 4:
			// Display server connections
			if (server == null)
			{
			    System.out.println("\nServer not running\n");
			}
			else
			{

			    try
			    {
				server.viewConnections();
			    }
			    catch (Exception e)
			    {
				System.out.println(e.getMessage());
			    }
			}

			break;
		    }
		}
		while (choice != 5);
		break;

		case 2:
		// User menu
		do
		{
		    // Display user menu options to be selected
		    displayUser();
		    choice = EasyIn.getIntWithMsg("Please pick an option from 1 - 6\n");
		    switch (choice)
		    {

			case 1:
			// Connect selected laptop to server
			if (laptops.isEmpty())
			{
			    System.out.println("\nNo laptops are available\n");
			}
			else
			{

			    laptops.printList();
			    try
			    {
				temp = (Laptop) laptops
					.get(EasyIn.getIntWithMsg("Enter number from 1 to " + laptops.size()));
				temp.connectTo(server);
				System.out.println("laptop has been connected\n");
			    }
			    catch (ListIndexOutOfBoundsException e)
			    {
				System.out.println("Invalid Number");
			    }
			    catch (Exception e)
			    {
				System.out.println(e.getMessage());
			    }

			}

			break;

			case 2:
			// Disconnect selected laptop from server
			if (laptops.isEmpty())
			{
			    System.out.println("No laptops are available\n");
			}
			else
			{

			    try
			    {
				laptops.printList();
				temp = (Laptop) laptops
					.get(EasyIn.getIntWithMsg("Enter number from 1 to " + laptops.size()));
				
				temp.disconnect(server);
				System.out.println("\nlaptop disconnected\n");
			    }
			    catch (ListIndexOutOfBoundsException e)
			    {
				System.out.println("Invalid Number");
			    }
			    catch (Exception e)
			    {
				System.out.println(e.getMessage());
			    }
			}

			break;

			case 3:
			// Ping a laptop connected to the server from a selected laptop

			if (laptops.isEmpty())
			{
			    System.out.println("\nNo laptops are available\n");
			}
			else
			{

			    laptops.printList();

			    try
			    {
				temp = (Laptop) laptops
					.get(EasyIn.getIntWithMsg("Enter number from 1 to " + laptops.size()));
				if (temp.ping(server, EasyIn.getStringWithMsg("Enter IP Address or HostName to ping")))
				{
				    System.out.println("\nlaptop has been found\n");
				}
				else
				{
				    System.out.println("\nlaptop not found\n");
				}
			    }
			    catch (ListIndexOutOfBoundsException e)
			    {
				System.out.println("Invalid Number");
			    }
			    catch (Exception e)
			    {
				System.out.println(e.getMessage());
			    }
			}

			break;

			case 4:
			// Add new laptop
			laptops.append(new Laptop(EasyIn.getStringWithMsg("Enter new HostName\n")));
			System.out.println("\nlaptop added\n");
			break;

			case 5:
			// Remove selected laptop
			if (laptops.isEmpty())
			{
			    System.out.println("\nNo laptops are available\n");
			}
			else
			{

			    laptops.printList();

			    try
			    {
				temp = (Laptop) laptops
					.get(EasyIn.getIntWithMsg("Enter number from 1 to " + laptops.size()));

				if (server.hasConnection(temp))
				{
				    // If the selected laptop is still connected
				    if (EasyIn.getIntWithMsg(
					    "laptop has connection with server. Enter 1 to disconnect and deleted laptop, otherwise enter 0") == 1)
				    {
					temp.disconnect(server);
					System.out.println("laptop disconnected\n");
					laptops.removeSpecific(temp);
					System.out.println("laptop removed\n");
				    }
				    else
				    {
					System.out.println("Action canceled\n");
					break;
				    }
				}
				else
				{
				    laptops.removeSpecific(temp);
				    System.out.println("laptop removed\n");
				}

			    }
			    catch (ListIndexOutOfBoundsException e)
			    {
				System.out.println("Invalid Number");
			    }
			    catch (Exception e)
			    {
				System.out.println("\nInvalid Number\n");
			    }
			}
			break;
		    }
		}
		while (choice != 6);
		break;

		case 3:
		// Program exit
		if (server != null)
		{
		    try
		    {
			server.writeToFile(System.getProperty("user.home") + "/Desktop/Accounts_DataBase.txt");
			System.out.println("User file list saved succsessfully");
		    }
		    catch (Exception e)
		    {
			System.out.println("File creation for users failed");
		    }
		}

		System.out.println("\nProgram Exiting...");
		System.exit(0);
	    }
	}
	while (choice != 3);
    }

    public static void displayMenu()
    {
	/*
	 * Method displayMenu()
	 * 
	 * Precondition: None.
	 * 
	 * Postcondition: Prints Main Menu to console.
	 */

	System.out.println("####LAN Network Simulator####\n\n1. Network Server\n2. Network User\n3. Exit Program");
    }

    public static void displayServer()
    {
	/*
	 * Method displayServer()
	 * 
	 * Precondition: None.
	 * 
	 * Postcondition: Prints Server Menu to console.
	 */
	System.out.println("1. Start Server\n2. Add User\n3. Remove User\n4. View laptops\n5. Go back");
    }

    public static void displayUser()
    {
	/*
	 * Method displayUser()
	 * 
	 * Precondition: None.
	 * 
	 * Postcondition: Prints user Menu to console.
	 */
	System.out.println(
		"1. Connect to Network\n2. Disconnect from Network\n3. Ping laptop\n4. Add laptop\n5. Remove laptop\n6. Go back");
    }
}
