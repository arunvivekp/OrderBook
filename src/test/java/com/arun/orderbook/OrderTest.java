package com.arun.orderbook;

import static org.junit.Assert.*;

import com.arun.orderbook.com.arun.order.model.Order;
import org.junit.Test;

public class OrderTest {
    private Order order;

    @Test
    public void getSizeShouldReturnCorrectSize()
    {
        order = new Order(1, 'O', 10, 12.0);
        assertEquals(10, order.getSize());
    }

    @Test
    public void getPriceShouldReturnCorrectPrice()
    {
        order = new Order(1, 'B', 10, 12.0);
        assertEquals(12.0, order.getPrice(), .00001);
    }
}
