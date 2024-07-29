/*-- main.cpp------------------------------------------------------------
   This file implements the driver for the BSTArray
-------------------------------------------------------------------------*/
#include "BSTArray.h"

#include <iostream>
using namespace std;

int main()
{
	// Create object
	BSTArray* array = new BSTArray();

	//Error Checking
	array->search(1);
	array->findmin();
	array->findmax();
	
	// Call methods on object
	array->insert(5);
	array->insert(8);
	array->insert(3);
	array->insert(1);
	array->insert(4);
	array->insert(9);
	array->insert(18);
	array->insert(20);
	array->insert(19);
	array->insert(2);
	array->print();

	//Maximum and Minimum Calls
	cout << "The maximum value in the BST Array is: " << array->findmax() << endl;
	cout << "The minimum value in the BST Array is: " << array->findmin() << endl;

	//Calls of Search Function
	if (array->search(3)) std::cout << "3 was found" << endl;
	if (array->search(18)) std::cout << "18 was found" << endl;
	if (array->search(19)) std::cout << "19 was found" << endl;


	// Destroy object
	delete array;
}

