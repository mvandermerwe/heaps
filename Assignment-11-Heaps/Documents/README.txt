Roman Clark
u0944898
April 14, 2017
CS2420
Assignment 11 - Heaps

Our Timing class 

      I pledge that the work done here was my own and that I have learned how to write
	      this program (such that I could throw it out and restart and finish it in a timely
	      manner).  I am not turning in any work that I cannot understand, describe, or
	      recreate.  Any sources (e.g., web sites) other than the lecture that I used to
	      help write the code are cited in my work.  When working with a partner, I have
	      contributed an equal share and understand all the submitted work.  Further, I have
	      helped write all the code assigned as pair-programming and reviewed all code that
	      was written separately.
	                      Roman Clark
	                     
Project Description:
(From Analysis Doc) The binary heap is an implementation of a priority queue. Each node in this min heap is smaller
than its children. Heaps are complete, as every level of the tree is filled, except possibly the bottom row. This means
that roughly half of the data, and exactly half of the data if the bottom row is filled, is found in the bottom row. This
makes most operations more efficient on average than the worst-case scenario, because as we will prove in our timing 
analysis, you do not have to traverse through the entire tree with the majority of the data being closer to the bottom, 
at the point of initial insertion (Half of the data being in the bottom row, one-fourth of the data in the second-lowest 
row, and so on). 
	             
Notes to the TA:
 - Our timing code has been abstracted into 4 methods, one for each of the main algorithms we wrote (heap sort,
 insertion, dequeue, and build heap from array).

Design Decisions:
 - We create an Enum for timing code that allows us to change the data being inserted or the data in the array
 being inserted. You can change that easily in the main method. Enum types: RANDOM - random data, IN_ORDER - sorted data,
 BACK_ORDER - reverse sorted info.
 - Our testing code takes advantage of CSV files (Comma Separated Values). This means we can easily write our
 data to a file that is easy to perform analysis on.	                     

Problems We Faced:
 - There were not any significant problems or design issues we ran into with this project.
