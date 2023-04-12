//-----------------------------------------------------
// Title: Programming Assignment 1 - Question 1 Part A
// Author: Burak Güçlü
// Description: This class takes a 2-D as the input 
// and contains a method that travels in the spiral order,
// add the number to linked list.
//-----------------------------------------------------

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Q1A {

	public static void main(String[] args) throws IOException {

		Scanner input = new Scanner(System.in);

		System.out.println("Input filename:");
		String filename = input.next();

		int length = 0, height = 0, temp = 0;

		BufferedReader read1 = new BufferedReader(new FileReader(filename));
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

		BufferedReader read2 = new BufferedReader(new FileReader(filename));
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

		System.out.println(spiralMatrix(matrix));
		// It writes the spiral matrix.

	}

	static String spiralMatrix(int[][] matrix) {
		// --------------------------------------------------------
		// Summary: This method is used for traveling the matrix in the
		// specific direction which will be down until the end of the column,
		// then right until the end of the row, then up until the top of the column,
		// and then left until the end of the row.
		// Precondition: int [][]matrix is 2-D array which will be traveling by this
		// method to add the items into the linked list in the correct order.
		// Postcondition: This method will return a String which is a representation of
		// the items in the linked list that is created by taking the items in the 2-D
		// array in the spiral path.
		// --------------------------------------------------------

		SLinkedList list = new SLinkedList();
		// I created a linked list to hold the numbers in it in a specific order.

		String array_to_string = "[";

		int top = 0;
		int bottom = matrix.length - 1;
		int right = matrix[0].length - 1;
		int left = 0;
		// These integers will hold the boundaries.

		String direction = "top -> bottom";
		// Since our traveling direction is
		// 1- up to down
		// 2- left to right
		// 3- down to up
		// 4- right to left
		// 5- up to down
		// ....
		// So, starting direction must be "top -> bottom"

		boolean process = true;
		// Since program should stop when it sees -1.
		// I created a boolean variable that help me to check if the next item is -1 or
		// not.

		while (top <= bottom && left <= right && process) {
			// When we reach any boundary program should stop so,
			// top value must always be larger than bottom value
			// and right value must always be larger than left value.
			// Also, number must not equal to -1.
			// Otherwise while loop ends.

			if (direction.equals("top -> bottom")) {
				for (int i = top; i <= bottom; i++) { // It goes until the end of the line.
					if (matrix[i][left] == -1) // Checks for -1
						process = false;
					else if (!process)
						break;
					else
						list.addLast(new Node(matrix[i][left], null));
				}
				left++; // Increase the left boundary.
				direction = "left -> right"; // Change the direction.
			} else if (direction.equals("left -> right")) {
				for (int i = left; i <= right; i++) { // It goes until the end of the line.
					if (matrix[bottom][i] == -1) // Checks for -1
						process = false;
					else if (!process)
						break;
					else
						list.addLast(new Node(matrix[bottom][i], null));
				}
				bottom--; // Decrease the bottom boundary.
				direction = "bottom -> top"; // Change the direction.
			} else if (direction.equals("bottom -> top")) {
				for (int i = bottom; i >= top; i--) { // It goes until the end of the line.
					if (matrix[i][right] == -1) // Checks for -1
						process = false;
					else if (!process)
						break;
					else
						list.addLast(new Node(matrix[i][right], null));
				}
				right--; // Decrease the right boundary.
				direction = "right -> left"; // Change the direction.
			} else if (direction.equals("right -> left")) {
				for (int i = right; i >= left; i--) { // It goes until the end of the line.
					if (matrix[top][i] == -1) // Checks for -1
						process = false;
					else if (!process)
						break;
					else
						list.addLast(new Node(matrix[top][i], null));
				}
				top++; // Increase the top boundary.
				direction = "top -> bottom"; // Change the direction.
			}
		}

		if (list.size == 0)
			return "";
		// If the value at starting point is equal to -1, list will be null.
		// In order to check it, I added this if statement.

		else {
			array_to_string += (list.printList(list)).substring(0, list.printList(list).length() - 2) + "]";

			return array_to_string;
		}
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
		return result;
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