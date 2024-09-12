#pragma once
#include "ElementType.h"
// Create BSTArray class defintiion here
// Remember, your underlying data type will be an array, not a linked list!
// Don't forget class definitions end with a semi-colon!
/*-- BSTArray.h ---------------------------------------------------------------
This header defines a binary search tree using an array implementation
  Basic operations are:
	 insert
	 findMin
	 findMax
	 search
	 print
-------------------------------------------------------------------------*/
#ifndef BST_Array
#define BST_Array


class BSTArray
{
public:

	/* insert an element in the binary tree */
	void insert(ElementType element);
	/* find the minimum value in the tree */
	int findmin();
	/* find the max value in the tree */
	int findmax();
	//Returns the Index of the element passed into the array
	int search(ElementType element);
	//Prints the Index and data in the array, skipping positions that don't have data
	void print();

private:
	int tree[256] = { 0 };
	



};
#endif