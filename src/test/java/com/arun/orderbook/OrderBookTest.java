package com.arun.orderbook;

import static org.junit.Assert.*;

import com.arun.orderbook.com.arun.order.model.Order;
import org.junit.Test;

public class OrderBookTest {

    private OrderBook orderBook;

    @Test
    public void testAddOrder_WithDataSortedByPrice() {
        orderBook = new OrderBook();
        Order order1 = new Order(1, 'B', 10, 12.0);
        Order order2 = new Order(2, 'B', 20, 13.0);
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);

        assertEquals(13.0, orderBook.getBidMaxPriceList().peek(), .0001);
    }

    @Test
    public void testAddOrder_WithDataSortedByPriceAndTime() {
        orderBook = new OrderBook();
        Order order1 = new Order(1, 'B', 10, 12.0);
        Order order2 = new Order(2, 'B', 30, 12.0);
        Order order3 = new Order(3, 'B', 20, 13.0);
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);

        assertEquals(30, orderBook.getBidMap().get(12.0).get(1).getSize());
    }

    @Test
    public void testDeleteOrder() {
        orderBook = new OrderBook();
        Order order1 = new Order(1, 'O', 10, 100.0);
        Order order2 = new Order(2, 'O', 30, 12.0);
        Order order3 = new Order(3, 'O', 20, 13.0);
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);

        orderBook.deleteOrder(order2);

        assertEquals(0, orderBook.getOfferMap().get(12.0).size());
    }

    @Test
    public void testUpdateOrder() {
        orderBook = new OrderBook();
        Order order1 = new Order(1, 'O', 10, 100.0);
        Order order2 = new Order(2, 'O', 30, 12.0);
        Order order3 = new Order(3, 'B', 20, 13.0);
        Order order4 = new Order(4, 'B', 40, 101.0);
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);
        orderBook.addOrder(order4);

        orderBook.updateOrder(order2, 50);

        assertEquals(50, orderBook.getOfferMap().get(12.0).get(0).getSize());
    }

    @Test
    public void testSize_GivenSide() {
        orderBook = new OrderBook();
        Order order1 = new Order(1, 'O', 10, 100.0);
        Order order2 = new Order(2, 'O', 30, 12.0);
        Order order3 = new Order(3, 'B', 20, 13.0);
        orderBook.addOrder(order1);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);

        assertEquals(2, orderBook.getAllOrdersForSide('O').size());
        assertEquals(1, orderBook.getAllOrdersForSide('B').size());
    }

    @Test
    public void testSize_GivenSideAndLevel() {
        orderBook = new OrderBook();
        Order order1 = new Order(1, 'O', 10, 100.0);
        Order order11 = new Order(11, 'O', 30, 100.0);
        Order order2 = new Order(2, 'O', 30, 99.0);
        Order order3 = new Order(3, 'B', 20, 13.0);
        Order order31 = new Order(31, 'B', 50, 13.0);
        orderBook.addOrder(order1);
        orderBook.addOrder(order11);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);
        orderBook.addOrder(order31);

        assertEquals(40, orderBook.getSizeForSideAndLevel('O', 2));
        assertEquals(70, orderBook.getSizeForSideAndLevel('B', 1));
    }
}
