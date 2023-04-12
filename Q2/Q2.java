//-----------------------------------------------------
// Title: Programming Assignment 1 - Question 2
// Author: Burak Güçlü
// Description: This class takes 1-D array as the input
// and check for each element in this array if the value
// was higher than or equal to that value.
//-----------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class Q2 {

	public static void main(String[] args) throws FileNotFoundException {

		Stack tempStack = new Stack();
		Stack lastStack = new Stack();
		// I created two different stacks. One of them is for keeping the data for
		// writing elements.
		// Other one is for comparing the values with each other.

		String elements = "";

		Scanner input = new Scanner(System.in);
		System.out.println("Input filename:");
		String filename = input.next();

		Scanner tempnumbers = new Scanner(new File(filename));
		while (tempnumbers.hasNextInt())
			tempStack.push(tempnumbers.nextInt());
		// Temporary stack contains items in the txt file in this part.

		if (tempStack.isEmpty()) // Checking if stack is empty or not, by using "Stack.isEmpty()" method.
			System.out.println("There is no entry!");
		else {
			for (int k = 0; k < tempStack.size; k++) {
				elements = tempStack.pop() + ", " + elements;
			}
			elements = "[" + elements.substring(0, elements.length() - 2) + "]";
		}

		System.out.println(elements);
		// Writing the elements in the txt files is done here.

		// Now, comparing part is started here.
		System.out.println(compare(tempStack, lastStack, filename));
		// In the end, program writes the maximum number of consecutive days that the
		// value was higher than or equal to today's value.
		// Program is done here.
	}

	static String compare(Stack tempStack, Stack lastStack, String filename) throws FileNotFoundException {
		// --------------------------------------------------------
		// Summary: This method is used for comparing each element
		// with the previous elements in the stack.
		// Precondition: tempStack is used for using its size,
		// lastStack will be used to hold the data and filename will
		// be used to reach text file.
		// Postcondition: The method will return results for each element.
		// --------------------------------------------------------
		String results = "";
		for (int i = 0; i < tempStack.size; i++) {
			int[] tempArray = new int[tempStack.size];
			lastStack = new Stack();
			Scanner numbers = new Scanner(new File(filename));
			while (numbers.hasNextInt())
				for (int m = 0; m < tempStack.size; m++)
					tempArray[m] = numbers.nextInt();
			// Items in the txt file added into stack.

			for (int k = 0; k < tempArray.length / 2; k++) {
				int temp = tempArray[tempArray.length - 1 - k];
				tempArray[tempArray.length - 1 - k] = tempArray[k];
				tempArray[k] = temp;
			}
			// Since we need to start from the bottom, we need to change the order.
			// This part change order of numbers reverse.

			for (int a = tempStack.size - 1; a >= i; a--) {
				lastStack.push(tempArray[a]);
			}
			// Since the order of numbers is reversed in the previous,
			// now we can add them into stack by using this part.

			int count = 1;
			int temp = lastStack.pop(); // Taking first item of the stack to compare it with other ones.
			int other = 0;
			if (tempArray[tempArray.length - 1] == temp) {
				// This part is checking for the first item in list if it is same with the
				// comparable one.
				// If program goes into this part, it directly writes "1" as count.
				// Otherwise it goes into else part to check the number between previous
				// numbers.

			} else
				other = lastStack.pop();

			for (int b = 0; b < lastStack.size - 1; b++) {
				if (other > temp) { // Checking the number if the number is less than previous ones.
					count++;
					if (count == lastStack.size) // If there is no more item in the stack to compare, leave it.
						break;
					other = lastStack.pop(); // If there is more item to compare, continue.
				}

				if (other < temp) { // Checking the number if the number is larger than previous ones.
					break;
				}
			}
			// "int count" helped me to hold the number of times that the number was less
			// than or equal to today's value.

			results = count + ", " + results;
		}
		return "[" + results.substring(0, results.length() - 2) + "]";
	}
}

class Node {
	// --------------------------------------------------------
	// Summary: This class is being used for creating nodes
	// to contain data in the stack.
	// --------------------------------------------------------
	int item;
	Node next;
}

class Stack {
	// --------------------------------------------------------
	// Summary: This class is Stack class. It works for saving
	// items in it. Its working style is like "Last in, first out".
	// --------------------------------------------------------

	Node first = null;
	int size = 0;

	boolean isEmpty() {
		// --------------------------------------------------------
		// Summary: This method is used for checking whether
		// the stack is empty or not.
		// Postcondition: It will return true if stakc is empty,
		// otherwise false.
		// --------------------------------------------------------
		return first == null;
	}

	int pop() {
		// --------------------------------------------------------
		// Summary: This method is used for deleting the last element,
		// which is pushed into stack lastly, from the stack.
		// Postcondition: It will return the integer element.
		// --------------------------------------------------------
		int item = first.item;
		first = first.next;
		return item;
	}

	void push(int a) {
		// --------------------------------------------------------
		// Summary: This method is used for adding new elements
		// into the stack.
		// Precondition: a is integer which is chosen to add into
		// specific stack.
		// Postcondition: integer a will be added to stack.
		// --------------------------------------------------------
		Node oldfirst = first;
		first = new Node();
		first.item = a;
		first.next = oldfirst;
		size++;
	}

	public void printStack(Stack stack) {
		// --------------------------------------------------------
		// Summary: This method is used for printing all elements
		// in the given stack.
		// Precondition: stack is Stack which is chosen to print
		// its elements.
		// Postcondition: Since it return type is void, it writes
		// every element of stack by using System.out.println().
		// --------------------------------------------------------
		int k = stack.size;
		while (k > 0) {
			System.out.print(stack.pop() + " ");
			k--;
		}
		System.out.println();
	}

}