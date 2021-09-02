Project Description:
     In this project we will make a software that demonstrates how laptops can connect into a wireless LAN in a simplified version. A hotspot is a geographic location in which an access point provides broadband network services to mobile users. When a laptop is equipped with a wireless interface module, it is picked up by the access point and it can automatically (initial configuration is actually required) then access the Internet. If a domain in the local area network exists (e.g. Cyberlink), the user of the laptop still has to authenticate to a server to have any access to the LAN’s services, as well as, the Internet. In addition, in order to be in a network, a laptop needs to have an IP address. Since the connections are dynamic, each laptop needs to have DHCP (Dynamic Host Configuration Protocol) installed, so it can be automatically assigned a free IP address from the server.

* Specification *
    Your goal is to design and implement in Java a computer program that meets the requirements of the above description. Therefore, the following abstract components and functionalities are required:

        1. Laptops can connect and disconnect from the wireless local area network

        2. A server authenticates users of these laptops through their username and password

        3. A server must be able to add and remove users from the network.
        4. A server assigns the first available free IP address from a bank of IP addresses

        5. Users are able to execute a ping in order to see if a specific machine is connected to the network either by the IP address or by the host name.

        6. A server must be able to view all the laptops that are currently connected to the network (all the properties)

        7. A user who has already authenticated from a specific laptop can not login from another one (done for security purposes)
* The Laptop Computer *
    Each laptop computer that will connect to the network will have the following two properties:
        - A Host Name (e.g. THANOS-PC)
        - An IP address (e.g. 192.68.10.54). As already mentioned, this is assigned automatically by the server.
    Computers may connect or disconnect from the network at any time.

* The Laptop User *
    The user of each laptop computer that will connect to the network will have the following attributes:
        - A username (e.g. “hatziapostolou”)
        - A password (e.g. “123abc”)
    A specific user will be allowed to connect to the network only if the server authenticates him/her. Otherwise the user will be denied access to the network. Furthermore, the user should be able to ping (find if they exist) any other computers on the network either through the IP address or the host name of the laptop computer.

* The Network Server *
The entire wireless local area network will be simulated through one server, and the server will be simulated through the use of a file. In other words, there exists a file that contains pairs of username-password through which the users of laptops authenticate. The structure of the file will be the following:
    Filename: server.dat or server.txt
    thanasis
    123abc
    mitsos
    dhr342 
    karamitros
    lod301
    patsakis
    kfg132
Note: users are always stored alphabetically (by username) whether in memory or in the file !!!!

An unlimited number of users may exist in the file. The usernames and passwords will be case-sensitive. The procedures related with the server are the following:
    - When the server loads, it reads the file of usernames and passwords into an appropriate structure in main memory
    - When a user wants to connect to the network the server first checks if the user has already authenticated. If he has, the server displays an appropriate message that the user has already authenticated from a laptop with a specific IP. However, if the user has not already authenticated, then the server scans the structure of users (not the file directly) in order to see if the user exists (valid username and password).
    - When new users are added to the domain of the network (new username and password), or when existing users are removed, the modifications are made in the structure in memory and not to the file directly.
    - When the server stops executing the (possibly updated) structure is placed back into the file that contains the usernames and passwords, overwriting the previous ones.
    
* IP Addresses *
As mentioned previously, the network server automatically assigns an IP address to a laptop computer after the user is successfully authenticated. There exist a finite number of IP addresses available in the local area network and, therefore, if all of them are allocated then no other laptop can connect to the network. In a real situation this finite number can be very large but for the purposes of this practical we will assume that there are only five (5) predefined IP addresses available for the wireless network. A sixth user will not be able to connect. When a user has authenticated, the server assigns the first available IP address. When a user disconnects, the server appends the IP address to the end of the available IP addresses. You can use the data type “String” for an IP address.

Aims:
    - Recognize many alternatives in designing a solution to a problem
    - Justify how the choice of data structures or ADTs impact the cost (in space and performance) 
    - Break down a large problem into simple manageable modules
    - Integrate small modules into a larger structure
    - Implement Abstract Data Types in Java
    - Utilize Abstract Data Type and data structures in an application
    - Develop specifications for modules

How to run:
    - On any IDE run the LANsim.java file and operate it with the provided CLI type GUI.