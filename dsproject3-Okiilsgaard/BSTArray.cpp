#include "BSTArray.h"
// Create BSTArray methods here
/*-- BSTArray.cpp------------------------------------------------------------
   This file implements the BSTstd member functions.
   Insert:Inserts a value into the Array using tree navigation strategies
   FindMin:Navigates the Array using tree implementation to find furthest left value in tree
   FindMax:Navigates the Array using tree implementation to find the furthest right value
   Search:Navigates the tree based on given imput
   Print:Outputs the contents of the tree, using a linear navigation system
-------------------------------------------------------------------------*/
#include <iostream>
using namespace std;

/* Insert an element into the BST */
void BSTArray::insert(ElementType element)
{
	int parent = 0; // pointer to parent of current node
	int index = 1;
	bool found = false; // indicates if item already in BST

	//If the tree is empty, the element becomes root node
	if (tree[index] == 0)
		tree[index] = element;
	else
	{
		//While the location is not found, and there is data stored in at that index
		while (!found && tree[index] != 0 && index < 255)
		{
			parent = index;
			if (element < tree[index])
			{
				// descend left
				parent = index;
				index = index * 2;

			}
			else if (tree[index] < element) // descend right
			{
				parent = index;
				index = index * 2 + 1;

			}
			else
			{
				// item found
				found = true;
			}

		}
		//found = true
		if (!found)
		{
			if (parent == 0) // empty tree
				//set element as the root node
				tree[1] = element;
			else
				//Using the already changed index, insert the element into the BST
				tree[index] = element;
		}
		else
			cout << "Item already in the tree\n";

	}
}

/* Find the minimum value recursively starting from root node */
int BSTArray::findmin()
{
	int condition = false;
	int index = 1;

	//If the value of the root is zero, display error message and break
	if (tree[index] == 0)
	{
		cout << "\nThe Array Is Empty, there is no maximum value.\n";
		return 0;
		
	}
	else
	{
		//While there is data in the array
		while (tree[index] != 0)
		{
			index = index * 2;
			//If the index incrememented too far, return to the index of the parent of the farthest left node
			if (tree[index] == 0)
			{
				index = index / 2;
				break;
			}
		}
	}
	return tree[index];

}

/* Find the maximum value recursivly starting from a specific node */
int BSTArray::findmax()
{
	int condition = false;
	int index = 1;
	//If the tree is empty
	if (tree[index] == 0)
	{
		cout << "\nThe Tree Is Empty, there is no minimum value.\n";
		return 0;
	}
	else
	{
		//While there is data at the index
		while (tree[index] != 0 )
		{
			//Descend to the right in the tree
			index = index * 2+1;
			//If the index incrememented too far, return to the index of the parent of the farthest right node
			if (tree[index] == 0)
			{
				index = index / 2;
				break;
			}
		}
	}
	return tree[index];
}

/*Search the array for a given element using non linear traversal methods*/
int BSTArray::search(ElementType element)
{
	int index = 1;
	bool found = false;

	//Check if the tree is empty
	if (tree[index] == 0)
	{
		cout << "\nThe Tree is empty, there is no value to search.\n";
		return -1;
	}
	else
	{	//While the element is not found, and while still in the bounds of the array
		while (found == false && index < 255)
		{
			//condition to move left in tree
			if (element < tree[index])
			{
				index = index * 2;
			}
			//Navigate to the next greatest position in the tree
			else if (element > tree[index])
			{
				index = (index * 2) + 1;
			}
			//If the element is found, return the index of the value
			else if (tree[index] == element)
			{
				found = true;
			}
			//If the element is not found, and at the end of the tree, show error message and return -1
			else if (tree[index] != element && found == false && tree[index * 2] == 0 && tree[(index * 2) + 1] == 0)
			{
				cout << "\nThe element is not in the tree.\n";
				return -1;
			}
		}
	}
	return index;
}
void BSTArray::print()
{
	//Linear Naviagation of the List
	for (int i = 0; i < 256; i++)
	{
		//If the element is not zero
		if (tree[i] != 0)
		{
			cout << "\nIndex: " << i << " , Value: " << tree[i] << endl;	
		}

	}
}



