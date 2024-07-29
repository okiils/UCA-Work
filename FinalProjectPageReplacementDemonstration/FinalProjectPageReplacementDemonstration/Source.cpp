/*Owen Kiilsgaard
* Dr. Sun-Operating Systems 2024
* Final Project-Page Replacement Demonstration
* This program simulates three different page replacement algorithms: FIFO, Optimal (OPT), and Least Recently Used (LRU).
* It reads page reference sequences from a file, 
* along with the number of frames in memory and the chosen algorithm identifier.
* For each algorithm, it calculates the number of page faults incurred by simulating the page replacement process.
* The user can run the program multiple times with different input files or settings.
*/
#include <iostream>          // for standard input/output
#include <fstream>           // for file input/output operations
#include <sstream>           // for string stream operations
#include <unordered_set>     // for unordered set data structure
#include <queue>             // for queue data structure
#include <unordered_map>     // for unordered map data structure
#include <vector>            // for vector data structure
#include <algorithm>         // for std::find algorithm
using namespace std;

// Function to simulate FIFO page replacement
// Inputs: pages - vector of page references
//         capacity - number of frames in memory
// Output: number of page faults occurred
int fifoPageFaults(vector<int>& pages, int capacity) {
    unordered_set<int> s;    // Set to store current pages in memory
    queue<int> indexes;       // Queue to maintain order of page references
    int page_faults = 0;      // Counter for page faults

    // Iterate through page references
    for (int i = 0; i < pages.size(); i++) {
        // If memory has space, add the page
        if (s.size() < capacity) {
            // If page not in memory, add it and count as a page fault
            if (s.find(pages[i]) == s.end()) {
                s.insert(pages[i]);
                page_faults++;
                indexes.push(pages[i]); // Update queue with page reference
            }
        }
        // If memory is full, perform page replacement
        else {
            // If page not in memory, replace using FIFO
            if (s.find(pages[i]) == s.end()) {
                int val = indexes.front(); // Get the oldest page
                indexes.pop();             // Remove from queue
                s.erase(val);              // Remove from memory
                s.insert(pages[i]);        // Add new page to memory
                page_faults++;             // Increment page fault counter
            }
        }
    }
    return page_faults; // Return total page faults
}

// Function to simulate Optimal (OPT) page replacement
// Inputs: pages - vector of page references
//         capacity - number of frames in memory
// Output: number of page faults occurred
int optPageFaults(vector<int>& pages, int capacity) {
    unordered_set<int> s;              // Set to store current pages in memory
    unordered_map<int, int> indexes;   // Map to store index of next occurrence of each page
    int page_faults = 0;               // Counter for page faults

    // Iterate through page references
    for (int i = 0; i < pages.size(); i++) {
        // If memory has space, add the page
        if (s.size() < capacity) {
            // If page not in memory, add it and count as a page fault
            if (s.find(pages[i]) == s.end()) {
                s.insert(pages[i]);
                page_faults++;
            }
            // Update index of next occurrence of page
            indexes[pages[i]] = i;
        }
        // If memory is full, perform page replacement
        else {
            // If page not in memory, replace using OPT
            if (s.find(pages[i]) == s.end()) {
                int farthest = i, val;
                // Find page in memory with farthest next occurrence
                for (auto it = s.begin(); it != s.end(); ++it) {
                    if (indexes[*it] > farthest) {
                        farthest = indexes[*it];
                        val = *it;
                    }
                }
                // Remove farthest page from memory and add new page
                s.erase(val);
                s.insert(pages[i]);
                page_faults++; // Increment page fault counter
            }
        }
    }
    return page_faults; // Return total page faults
}


// Function to simulate Least Recently Used (LRU) page replacement
// Inputs: pages - vector of page references
//         capacity - number of frames in memory
// Output: number of page faults occurred
int lruPageFaults(vector<int>& pages, int capacity) {
    unordered_set<int> s;              // Set to store current pages in memory
    unordered_map<int, int> indexes;   // Map to store index of last occurrence of each page
    int page_faults = 0;               // Counter for page faults

    // Iterate through page references
    for (int i = 0; i < pages.size(); i++) {
        // If memory has space, add the page
        if (s.size() < capacity) {
            // If page not in memory, add it and count as a page fault
            if (s.find(pages[i]) == s.end()) {
                s.insert(pages[i]);
                page_faults++;
            }
            // Update index of last occurrence of page
            indexes[pages[i]] = i;
        }
        // If memory is full, perform page replacement
        else {
            // If page not in memory, replace using LRU
            if (s.find(pages[i]) == s.end()) {
                int lru = INT_MAX, val;
                // Find least recently used page in memory
                for (auto it = s.begin(); it != s.end(); ++it) {
                    if (indexes[*it] < lru) {
                        lru = indexes[*it];
                        val = *it;
                    }
                }
                // Remove least recently used page from memory and add new page
                s.erase(val);
                s.insert(pages[i]);
                page_faults++; // Increment page fault counter
            }
        }
        // Update index of last occurrence of current page
        indexes[pages[i]] = i;
    }
    return page_faults; // Return total page faults
}

int main() {
    string filename; // Input filename
    char choice;     // User choice to continue or exit program

    do {
        cout << "Enter the filename: ";
        cin >> filename;

        ifstream infile(filename); // Open input file
        if (!infile) {
            cerr << "Error: Unable to open input file." << endl;
            return 1;
        }

        char algorithm;             // Page replacement algorithm identifier
        int num_frames;             // Number of frames in memory
        string reference_string;    // String representing page references

        string line;
        getline(infile, line); // Read first line of input file
        stringstream ss(line);
        string segment;
        vector<int> pages; // Vector to store page references

        // Parse the first line to extract algorithm and page references
        while (getline(ss, segment, ',')) {
            if (segment.size() > 0) {
                if (isdigit(segment[0])) {
                    pages.push_back(stoi(segment)); // Store page reference
                }
                else {
                    algorithm = segment[0]; // Store algorithm identifier
                }
            }
        }

        num_frames = pages[0];            // Get number of frames
        pages.erase(pages.begin());       // Remove the first element (number of frames)

        int page_faults = 0;
        // Choose appropriate page replacement algorithm based on identifier
        if (algorithm == 'F') {
            page_faults = fifoPageFaults(pages, num_frames); // Execute FIFO
        }
        else if (algorithm == 'O') {
            page_faults = optPageFaults(pages, num_frames); // Execute OPT
        }
        else if (algorithm == 'L') {
            page_faults = lruPageFaults(pages, num_frames); // Execute LRU
        }
        else {
            cerr << "Invalid algorithm specified." << endl;
            return 1;
        }

        cout << "Number of page faults: " << page_faults << endl;

        cout << "Do you want to run the program again? (y/n): ";
        cin >> choice;
    } while (choice == 'y' || choice == 'Y');

    return 0;
}
