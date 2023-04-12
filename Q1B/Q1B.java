//-----------------------------------------------------
// Title: Programming Assignment 1 - Question 1 Part B
// Author: Burak Güçlü
// Description: This class takes the inputs and implements
// them to check whether any node from list1
// is connected to any node in list2.
//-----------------------------------------------------

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Q1B {

	public static void main(String[] args) throws IOException {

		Scanner input = new Scanner(System.in);
		String filename1 = input.next();
		filename1 = filename1.substring(1, filename1.length() - 1);
		String filename2 = input.next();
		filename2 = filename2.substring(1, filename2.length() - 1);
		String filename3 = input.next();
		filename3 = filename3.substring(1, filename3.length() - 1);
		// Since we are taking three input as the txt filenames, I created the different
		// Strings to save them.
		// Also, given Strings are written with quotations marks so, I used
		// "String.substring()" method to get exact file names.

		int length = 0, height = 0, temp = 0;
		BufferedReader read1 = new BufferedReader(new FileReader(filename1));
		for (String line = read1.readLine(); line != null; line = read1.readLine()) {
			String[] lines = line.split(" ");
			length = lines.length;
			height++;
		}
		read1.close();
		// This part is being used for learning the sizes of the matrix that we are
		// given.
		// This will be used for creating 2-D array to contain data.

		int[][] matrix = new int[height][length];
		// I created matrix by using the information that I got in the previous part.

		BufferedReader read2 = new BufferedReader(new FileReader(filename1));
		for (String line = read2.readLine(); line != null; line = read2.readLine()) {
			String[] lines = line.split(",");
			for (int i = 0; i < lines.length; i++) {
				String[] abc = lines[i].split(" ");
				for (int k = 0; k < abc.length; k++)
					matrix[temp][k] = Integer.parseInt(abc[k]);
			}
			temp++;
		}
		read2.close();
		// This part is used for adding the values in the matrix by taking the number in
		// the txt files one by one.
		// Since the reading method returns String, I used "Integer.parseInt()" method
		// to turn them to integer.

		BufferedReader read3 = new BufferedReader(new FileReader(filename2));
		String line1 = read3.readLine();
		String[] lines1 = line1.split("-");
		// I used same method to take the positions which are written in the format
		// "row,col-row,col" in the txt file.
		// To do so, I used "String.split()" method to take the positions separately.

		BufferedReader read4 = new BufferedReader(new FileReader(filename3));
		String line2 = read4.readLine();
		String[] lines2 = line2.split("-");
		// Again, I used same method to take the positions which are written in the
		// format "row,col-row,col" in the second txt file.
		// To do so, I used "String.split()" method to take the positions separately.

		SLinkedList list1 = new SLinkedList();
		SLinkedList list2 = new SLinkedList();
		// We are expected to use linked list. I created two different linked list to
		// hold the items in the txt files.

		for (String values : lines1) {
			int number1 = matrix[Integer.parseInt(values.substring(0, 1))][Integer.parseInt(values.substring(2, 3))];
			// To turn the positions, which is initially Strings, into integer I used
			// "Integer.parseInt()" method.
			list1.addLast(new Node(number1, null));
		}
		// This part is being used for adding elements into linked list for the list1.

		for (String values : lines2) {
			int number2 = matrix[Integer.parseInt(values.substring(0, 1))][Integer.parseInt(values.substring(2, 3))];
			// To turn the positions, which is initially Strings, into integer I used
			// "Integer.parseInt()" method.
			list2.addLast(new Node(number2, null));
		}
		// This part is being used for adding elements into linked list for the list2.

		boolean last = true;
		for (String value : lines2) {
			last = last && checkForNeighbours(list1, list2, value, matrix);
			// Since we are looking for all the numbers in the list2, all of them have to return true.
			// If any of them return false, last's value will be false too.
		}

		if (last)
			System.out.println("TRUE");
		else
			System.out.println("FALSE");

	}

	static boolean checkForNeighbours(SLinkedList list1, SLinkedList list2, String item, int[][] matrix) {
		// --------------------------------------------------------
		// Summary: This method is being used for checking whether
		// neighborhood of list1 contains list2 or not.
		// Precondition: list1 is linked list that contains the items
		// in first txt file. list2 is linked list that contains the
		// items in the second txt file. item is String that represent
		// the positions in the list. matrix is 2-D array that contains
		// both list1 and list2.
		// Postcondition: The method returns true if neighborhood of
		// list1 contains list2, otherwise false.
		// --------------------------------------------------------
		boolean result1 = false, result2 = false, result3 = false, result4 = false;
		// Since the nodes to the right, left, under and above should be checked,
		// it would be nice to have four different boolean variable.

		int right, left, col, top, bottom, row;
		// These integers will hold the positions.

		right = left = col = Integer.parseInt(item.substring(2, 3));
		top = bottom = row = Integer.parseInt(item.substring(0, 1));
		// Giving the values to integers which represent the positions is done.

		if (col != 0) { // Checking for boundaries.
			left--;
			result1 = list1.contains(matrix[row][left]) || list2.contains(matrix[row][left]);
			// Checking for left side of the list2 whether list1 contains it.
			// Also, it might be in the list2 so, looking for it is necessary.
		}
		if (col != matrix[0].length - 1) {// Checking for boundaries.
			right++;
			result2 = list1.contains(matrix[row][right]) || list2.contains(matrix[row][right]);
			// Checking for right side of the list2 whether list1 contains it.
			// Also, it might be in the list2 so, looking for it is necessary.
		}
		if (row != 0) {// Checking for boundaries.
			top--;
			result3 = list1.contains(matrix[top][col]) || list2.contains(matrix[top][col]);
			// Checking for above of the list2 whether list1 contains it.
			// Also, it might be in the list2 so, looking for it is necessary.
		}
		if (row != matrix.length - 1) {// Checking for boundaries.
			bottom++;
			result4 = list1.contains(matrix[bottom][col]) || list2.contains(matrix[bottom][col]);
			// Checking for under of the list2 whether list1 contains it.
			// Also, it might be in the list2 so, looking for it is necessary.
		}

		if (result1 == true && result2 == true && result3 == true && result4 == true)
			return true;
		else
			return false;
		// If the all the neighborhood numbers in the list2, exist in the list1 method
		// returns true.
		// Otherwise, it returns false.
	}

}

class SLinkedList {
	// --------------------------------------------------------
	// Summary: This class is being used for creating linked
	// list to contain nodes in it.
	// --------------------------------------------------------

	Node head, tail;
	int size;

	public SLinkedList() {
		// --------------------------------------------------------
		// Summary: This method is a constructor method that
		// is used for creating initial linked list.
		// --------------------------------------------------------
		head = tail = null;
		size = 0;
	}

	void insertAfter(Node v, Node n) {
		// --------------------------------------------------------
		// Summary: This method is being used for adding a new
		// node after the node that is chosen.
		// Precondition: v is a Node that you want add after the
		// chosen node which is Node n.
		// Postcondition: The node is set after the chosen node.
		// --------------------------------------------------------
		n.setNext(v.getNext());
		v.setNext(n);
		size++;
	}

	void addLast(Node n) {
		// --------------------------------------------------------
		// Summary: This method is being used for adding a new
		// node at last point of the linked list.
		// Precondition: n is a Node that you want add at the
		// last point of the linked list.
		// Postcondition: The node is set after the last point.
		// --------------------------------------------------------
		if (tail == null) {
			n.setNext(null);
			head = tail = n;
		} else {
			tail.setNext(n);
			n.setNext(null);
			tail = n;
		}
		size++;
	}

	String printList(SLinkedList list) {
		// --------------------------------------------------------
		// Summary: This method is being used for writing the
		// all elements of nodes in the linked list.
		// Precondition: list is linked list that you want to
		// write its elements.
		// Postcondition: It returns String that contains all the
		// elements in the list.
		// --------------------------------------------------------
		String result = "";
		Node temp = list.head;

		while (temp != null) {
			result += temp.getData() + ", ";
			temp = temp.getNext();
		}
		return result.substring(0, result.length() - 2);
	}

	boolean contains(int a) {
		// --------------------------------------------------------
		// Summary: This method is being used for checking
		// whether any integer exits in the linked list.
		// Precondition: a is the integer that you want to look for
		// in the linked list.
		// Postcondition: It returns true if the integer exists in the
		// linked list, otherwise false.
		// --------------------------------------------------------
		Node temp = this.head;
		while (temp != null) {
			if (temp.getData() == a)
				return true;
			temp = temp.getNext();
		}
		return false;
	}
}

class Node {
	// --------------------------------------------------------
	// Summary: This class is being used for creating nodes
	// to contain data in the linked list.
	// --------------------------------------------------------

	int data;
	Node next;

	public Node(int s, Node n) {
		// --------------------------------------------------------
		// Summary: This method is a constructor method that
		// is used for creating initial node.
		// --------------------------------------------------------
		data = s;
		next = n;
	}

	public int getData() {
		// --------------------------------------------------------
		// Summary: This method is used for reaching the data
		// which is stored in the node.
		// --------------------------------------------------------
		return data;
	}

	public Node getNext() {
		// --------------------------------------------------------
		// Summary: This method is used for reaching the next
		// node to do something with it.
		// --------------------------------------------------------
		return next;
	}

	public void setData(int newData) {
		// --------------------------------------------------------
		// Summary: This method is used for setting data for
		// the specific node.
		// Precondition: newData is a integer.
		// Postcondition: The value of the variable is set.
		// --------------------------------------------------------
		data = newData;
	}

	public void setNext(Node newNext) {
		// --------------------------------------------------------
		// Summary: This method is used for setting the next node
		// for the chosen node.
		// Precondition: newNext is a Node.
		// Postcondition: The next node is set.
		// --------------------------------------------------------
		next = newNext;
	}
}