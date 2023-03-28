# OrderBook
A simple implementation of OrderBook covering following use-cases

• Given an Order, add it to the OrderBook (order additions are expected to occur extremely frequently)
• Given an order id, remove an Order from the OrderBook (order deletions are expected to occur at ap-
proximately 60% of the rate of order additions)
• Given an order id and a new size, modify an existing order in the book to use the new size (size modi-
cations do not eect time priority)
• Given a side and a level (an integer value >0) return the price for that level (where level 1 represents the
best price for a given side). For example, given side=B and level=2 return the second best bid price
• Given a side and a level return the total size available for that level
• Given a side return all the orders from that side of the book, in level- and time-order

Implementation
Uses priority queue(revereseOrder) to store bid and another priority queue to store offer price
Orders are then stored in a HashMap to store orders grouped by price

Order matching would need to use the highest bid price at the top of the queue and the lowest offer price at the top

To Complie
mvn clean
mvn compile

To test
mvn test

TODO
Further possible improvements include adding a REST controller to add/update/delete an order

