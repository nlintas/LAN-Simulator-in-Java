import java.lang.Comparable;

public class ReferenceBasedList implements ListInterface, Comparable<Object>
{
    private ListNode head;
    private ListNode tail;
    int numItems;

    public ReferenceBasedList()
    {
	head = tail = null;
	numItems = 0;
    }

    public int size()
    {
	return numItems;
    }

    public boolean isEmpty()
    {
	return (numItems == 0);
    }

    public void removeAll()
    {
	head = tail = null;
	numItems = 0;
    }

    private ListNode find(int index)
    {
	ListNode curr = head;
	for (int skip = 1; skip < index; skip++)
	    curr = curr.getNext();
	return curr;
    }

    public Object get(int index) throws ListIndexOutOfBoundsException
    {
	if (index >= 1 && index <= numItems)
	{
	    ListNode curr = find(index);
	    return curr.getItem();
	}
	else
	{
	    throw new ListIndexOutOfBoundsException("List index out of bounds exception on get");
	}
    }

    public void add(int index, Object newDataItem) throws ListIndexOutOfBoundsException
    {
	if (index >= 1 && index <= numItems + 1)
	{
	    if (index == 1)
	    {
		ListNode newNode = new ListNode(newDataItem, head);
		head = newNode;

		if (tail == null) tail = head;
	    }
	    else if (index == numItems + 1)
	    {
		ListNode newNode = new ListNode(newDataItem);
		tail.setNext(newNode);
		tail = newNode;
	    }
	    else
	    {
		ListNode prev = find(index - 1);
		ListNode newNode = new ListNode(newDataItem, prev.getNext());
		prev.setNext(newNode);
	    }
	    numItems++;
	}
	else
	{
	    throw new ListIndexOutOfBoundsException("List index out of bounds exception on add");
	}
    }

    public void insert(Object newDataItem)
    {
	this.add(1, newDataItem);
    }

    public void append(Object newDataItem)
    {
	this.add(numItems + 1, newDataItem);
    }

    public Object showFront()
    {
	return this.get(1);
    }

    public Object showLast()
    {
	return this.get(numItems);
    }

    public void remove(int index) throws ListIndexOutOfBoundsException
    {
	if (index >= 1 && index <= numItems)
	{
	    if (index == 1)
	    {
		head = head.getNext();
		if (head == null) tail = null;
	    }
	    else
	    {
		ListNode prev = find(index - 1);
		ListNode curr = prev.getNext();
		prev.setNext(curr.getNext());
		if (index == numItems) tail = prev;
	    }
	    numItems--;
	}
	else
	{
	    throw new ListIndexOutOfBoundsException("List index out of bounds exception on remove");
	}
    }

    public boolean exists(Object dataItem)
    {
	for (ListNode tmp = head; tmp != null; tmp = tmp.getNext())
	    if (tmp.getItem().equals(dataItem)) return true;
	return false;
    }

    public Object removeLast() throws ListException
    {
	if (isEmpty())
	    throw new ListException("The linked list is empty");
	else
	{
	    Object lastDataItem = tail.getItem();
	    if (head == tail)
		head = tail = null;
	    else
	    {
		ListNode tmp = head;
		while (tmp.getNext().getNext() != null)
		    tmp = tmp.getNext();
		tail = tmp;
		tail.setNext(null);
	    }
	    numItems--;
	    return lastDataItem;
	}
    }

    public Object removeFront() throws ListException
    {
	if (isEmpty())
	    throw new ListException("The linked list is empty");
	else
	{
	    Object frontDataItem = head.getItem();
	    head = head.getNext();
	    if (head == null) tail = null;
	    numItems--;
	    return frontDataItem;
	}
    }

    public void addSorted(Comparable<Object> data) throws ListException, IllegalArgumentException
    {
	/*
	 * Method addSorted(Comparable data) adds new data items in list according to
	 * their compareTo values.
	 * 
	 * Precondition: the data given is comparable.
	 * 
	 * Postcondition: it adds the data in a sorted manner.
	 * 
	 * Note: throws an IllegalArgumentException if the data is null and
	 * ListException if the data already exists.
	 */

	if (data == null) throw new IllegalArgumentException("Argument is null");

	ListNode current;
	ListNode newNode;
	// When the head is null, make a new node and make it the head and the tail.
	if (head == null)
	{
	    current = new ListNode(data);
	    tail = head = current;
	    numItems++;
	}
	// when the data is smaller than the head create a new node and place it before
	// the head make it then head and the old head making it the tail.
	else if (data.compareTo(head.getItem()) < 0)
	{
	    current = new ListNode(data, head);
	    tail = head;
	    head = current;
	    numItems++;
	}
	else
	{
	    current = head;
	    newNode = new ListNode(data);
	    // While where I point is not the last position and data I am trying to insert
	    // is bigger or equal to 0, traverse to the next node.
	    while (current.getNext() != null && data.compareTo(current.getNext().getItem()) >= 0)
	    {
		current = current.getNext();
	    }
	    // if you find the data you are trying to enter is exactly the same with another
	    // node's data stop and throw an exception.
	    if (((Comparable<Object>) current.getItem()).compareTo(data) == 0)
	    {
		throw new ListException("Data Already Exists");
	    }
	    // if I am at the final node make it the tail.
	    if (current.getNext() == null) tail = newNode;

	    newNode.setNext(current.getNext());
	    current.setNext(newNode);

	    numItems++;
	}
    }

    public void reverse()
    {
	ListNode current = head;
	ListNode next = current.getNext();
	while (next != null)
	{
	    // Cache the next pointer to not lose the reference
	    ListNode temp = next.getNext();
	    next.setNext(current);
	    // Increment
	    current = next;
	    next = temp;
	}
	head = current;
    }

    public boolean removeSpecific(Object dataItem)
    {
	/*
	 * Method removeSpecific(Object dataItem) removes a specific object from this
	 * instance/List that is exactly the same as the one provided in the parameters
	 * 
	 * Precondition: Method expects to receive any kind of Object called internally
	 * "dataItem" with the assumption the object has its own equals method.
	 * 
	 * Postcondition: Method returns true if the specified object has been removed
	 * and false otherwise.
	 * 
	 * Note: Should the list be empty, false is returned and an exception is thrown
	 * is the argument is null.
	 * 
	 */

	if (dataItem == null) throw new IllegalArgumentException("Argument is null");

	ListNode curr = head;

	for (int i = 1; i <= numItems; i++)
	{

	    if (curr.getItem().equals(dataItem))
	    {
		this.remove(i);
		return true;
	    }
	    curr = curr.getNext();
	}
	numItems--;
	return false;
    }

    public void printList()
    {
	/*
	 * Method printList() prints all of the list elements on the Console
	 * 
	 * Precondition:None
	 * 
	 * Postcondition: All of the elements are printed to the console and nothing is
	 * returned.
	 * 
	 * Note: The list remains unaffected.
	 */
	int i = 1;
	if (this.isEmpty())
	    System.out.println("List is empty");
	else for (ListNode curr = head; curr != null; curr = curr.getNext())
	{
	    System.out.print(curr.getItem().toString() + " Position number: " + (i++) + "\n");
	}
    }

    public boolean equals(ListInterface otherList)
    {
	/*
	 * Method equals(ListInterface otherList) shows if two lists are exactly the
	 * same.
	 * 
	 * Precondition:Method expects to receive a ListInterface called "otherList".
	 * 
	 * Postcondition:Returns true if all the elements of this instance are equal to
	 * the "otherList".
	 * 
	 * Note:Lists remain unaffected and an exception is thrown if the argument in
	 * null.
	 * 
	 */
	ListNode curr = head;
	if (otherList.size() != this.size())
	    throw new ListException("Sizes of the 2 lists are not the same therefore the lists are not equal.");
	else
	{
	    for (int i = 1; i <= numItems; i++)
	    {
		if (!curr.getItem().equals(otherList.get(i))) return false;
	    }
	}
	return true;
    }

    @Override
    public int compareTo(Object o)
    {
	/*
	 * Method compareTo(Object o) shows how different two objects of the same
	 * instance are based on their state.
	 * 
	 * Precondition: Method expects to receive a Object "o" that is an instance of a
	 * ListInteface.
	 * 
	 * Postcondition: Method returns an int value. The value is negative if the
	 * calling instance is considered inferior to the argument and the opposite
	 * applies. Should both instances be equal, "0" is returned.
	 * 
	 * Note: What is considered "Inferior" or "Superior" (giving a bigger or smaller
	 * value) depends on the code decided.
	 * 
	 */
	if (o instanceof ListInterface)
	{
	    int sum = 0;
	    if (((ListInterface) o).size() != this.size())
		throw new ListException("Sizes of the 2 lists are not the same therefore the lists are not equal.");
	    for (int i = 1; i <= numItems; i++)
	    {
		Comparable<Object> listItem1 = (Comparable<Object>) this.get(i);
		Comparable<Object> listItem2 = (Comparable<Object>) ((ListInterface) o).get(i);
		sum += listItem1.compareTo(listItem2);
		sum += ((Comparable<Object>) this.get(i)).compareTo((Comparable<Object>) ((ListInterface) o).get(i));
	    }
	    return sum;
	}
	else
	{
	    throw new IllegalArgumentException(
		    "Data given is not a ListInterface therefore it cannot be compared with a list");
	}
    }

    @Override
    public String toString()
    {
	return "ReferenceBasedList [head=" + head + ", tail=" + tail + ", numItems=" + numItems + "]";
    }

}