package com.arun.orderbook;

import com.arun.orderbook.com.arun.order.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    OrderBook orderBook;
    //Map to store all orders
    private Map<Long, Order> orders;

    public OrderService() {
        orders = new HashMap<>();
        orderBook = new OrderBook();
    }

    /**
     * Add an order
     * @param orderId
     * @param side
     * @param size
     * @param price
     */
    public void add(long orderId, char side, long size, double price) {
        if (orders.containsKey(orderId))
            return;

        Order order = new Order(orderId, side, size, price);

        orderBook.addOrder(order);

        orders.put(orderId, order);
    }

    /**
     * Delete an order by order id
     * @param orderId
     */
    public void deleteOrder(long orderId) {
        Order order = orders.get(orderId);

        if (order == null)
            return;

        if (orderBook.deleteOrder(order))
            orders.remove(orderId);
    }

    /**
     * Update an order's size by orderId
     * @param orderId
     * @param size
     */
    public void updateOrder(long orderId, long size) {
        Order order = orders.get(orderId);

        if (order == null)
            return;

        orderBook.updateOrder(order, size);

        order.setSize(size);
    }

    /**
     * Get Price for given side and level
     * Returns 0 if no matching records found
     * @param side
     * @param level
     * @return
     */
    public double getPriceForSideAndLevel(char side, int level) {
        return orderBook.getPriceForSideAndLevel(side, level);
    }

    /**
     * Get size for given side and level
     * Returns 0 if no matching records found
     * @param side
     * @param level
     * @return
     */
    public long getSizeForSideAndLevel(char side, int level) {
        return orderBook.getSizeForSideAndLevel(side, level);
    }

    /**
     * Get all orders for a given side
     * Will return an empty list if no orders found
     * @param side
     * @return
     */
    public List<Order> getAllOrdersForSide(char side) {
        return orderBook.getAllOrdersForSide(side);
    }
}
