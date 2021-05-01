# Banking Transactions Environment in Erlang and JAVA ( Concurrent Message Exchange Application)
Our main objective is to model a simple banking transaction environment. Specifically, you will be given a small number of customers, each of whom will contact a set of banks to request a number of loans. Eventually, they will either receive all of the money they require or they will end up without completely meeting their original objective. The application will display information about the various banking transactions before it finishes.

Note that while Erlang is a functional language, in the same style as Clojure, our focus here is the concurrency model provided by Erlang. So, in addition to implementing the application in Erlang, you will implement the same application using Java, arguably the most popular imperative language in use today.
<br><br>
Full Project details in **Description.pdf** file.
<br><br>
*Software/IDE used* :  **IntelliJ IDEA CE**

To run in.. 

**(1) Java** 
	
		- javac money.java
		- java money

<br><br>
**(2) Erlang**


     Method 1 ->   (Here we need to compile all 3 Erlang files.)

		-    erl –noshell –s money –s init stop
		-    c(money).
		-    c(customer).
		-    c(bank).
		->   money:start().



     Method 2 ->

		-    erl -compile money
		-    erl -make
		-    erl -run money start
