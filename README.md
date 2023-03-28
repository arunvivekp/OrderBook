# OrderBook
A simple implementation of OrderBook covering following use-cases

• Given an Order, add it to the OrderBook (order additions are expected to occur extremely frequently) <br />
• Given an order id, remove an Order from the OrderBook (order deletions are expected to occur at ap-
proximately 60% of the rate of order additions) <br />
• Given an order id and a new size, modify an existing order in the book to use the new size (size modi-
cations do not eect time priority) <br />
• Given a side and a level (an integer value >0) return the price for that level (where level 1 represents the
best price for a given side). For example, given side=B and level=2 return the second best bid price <br />
• Given a side and a level return the total size available for that level <br />
• Given a side return all the orders from that side of the book, in level- and time-order <br />

Implementation <br />
Uses priority queue(revereseOrder) to store bid and another priority queue to store offer price <br />
Orders are then stored in a HashMap to store orders grouped by price <br />
<br />
Order matching would need to use the highest bid price at the top of the queue and the lowest offer price at the top <br />
<br />
To Compile <br />
mvn clean <br />
mvn compile <br />
<br />
To test <br />
mvn test <br />
<br />
TODO <br />
Further possible improvements include adding a REST controller to add/update/delete an order <br />

