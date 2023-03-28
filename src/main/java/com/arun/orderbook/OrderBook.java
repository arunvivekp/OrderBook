package com.arun.orderbook;

import java.lang.Double;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.arun.orderbook.com.arun.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderBook
{
    private final Logger logger = LoggerFactory.getLogger(OrderBook.class);

    // Storing list of orders for a given price
    private Map<Double, List<Order>> bidMap;
    private Map<Double, List<Order>> offerMap;

    private Queue<Double> bidMaxPriceList;
    private Queue<Double> offerMinPriceList;

    private final char BID = 'B';
    private final char OFFER = 'O';

    public OrderBook()
    {
        bidMap = new HashMap<>();
        offerMap = new HashMap<>();

        bidMaxPriceList = new PriorityQueue<>(50, Collections.reverseOrder()); // sorted descending, top is max bid price
        offerMinPriceList = new PriorityQueue<>();  // sorted ascending, top is min offer price
    }

    /**
     * Add order to corresponding bucket grouped by price
     * @param order
     */
    public void addOrder(Order order) {
        logger.info("type : {}, size : {}, price : {}", order.getSide(), order.getSize(), order.getPrice());

        switch (order.getSide()) {
            case BID:
                List<Order> bidBucket = getBucket(bidMap, order.getPrice());
                bidBucket.add(order);
                bidMap.put(order.getPrice(), bidBucket);
                bidMaxPriceList.add(order.getPrice());
                return;
            case OFFER:
                List<Order> offerBucket = getBucket(offerMap, order.getPrice());
                offerBucket.add(order);
                offerMap.put(order.getPrice(), offerBucket);
                offerMinPriceList.add(order.getPrice());
        }
    }

    /**
     * Deletes an order based on the order object
     * @param order
     * @return
     */
    public boolean deleteOrder(Order order) {
        switch (order.getSide()) {
            case BID:
                List<Order> bidBucket = getBucket(bidMap, order.getPrice());
                return bidBucket.remove(order);

            case OFFER:
                List<Order> offerBucket = getBucket(offerMap, order.getPrice());
                return offerBucket.remove(order);
        }

        return false;
    }

    /**
     * Update order's size based on the order
     * @param order
     * @param size
     */
    public void updateOrder(Order order, long size) {
        switch (order.getSide()) {
            case BID:
                List<Order> bidBucket = getBucket(bidMap, order.getPrice());
                bidBucket.stream().filter(e -> e.getId() == order.getId()).forEach(i -> i.setSize(size));
                return;
            case OFFER:
                List<Order> offerBucket = getBucket(offerMap, order.getPrice());
                offerBucket.stream().filter(e -> e.getId() == order.getId()).forEach(i -> i.setSize(size));
        }
    }

    /**
     * Get Price for given side and level
     * level 1 would mean the first element in the queue
     *
     * @param side
     * @param level
     * @return
     */
    public double getPriceForSideAndLevel(char side, int level) {
        switch(side) {
        case BID:
            PriorityQueue<Double> bidMaxPriceListCopy = new PriorityQueue<>(bidMaxPriceList);
            if (level > bidMaxPriceListCopy.size()) return 0.0;
            for (int i=1; i<=level; i++) {
                Double price = bidMaxPriceListCopy.poll();
                if (i == level) {
                    return price;
                }
            }

        case OFFER:
            PriorityQueue<Double> offerMinPriceListCopy = new PriorityQueue<>(offerMinPriceList);
            if (level > offerMinPriceListCopy.size()) return 0.0;
            for (int i=1; i<=level; i++) {
                Double price = offerMinPriceListCopy.poll();
                if (i == level) {
                    return price;
                }
            }
        }

        //Return 0 if level not matched
        return 0.0;
    }

    /**
     * Get total size of all orders matching given side and level
     * @param side
     * @param level
     * @return
     */
    public long getSizeForSideAndLevel(char side, int level) {
        Double price = getPriceForSideAndLevel(side, level);
        switch(side) {
            case BID:
                return bidMap.get(price).stream().mapToLong(o -> o.getSize()).sum();

            case OFFER:
                return offerMap.get(price).stream().mapToLong(o -> o.getSize()).sum();
        }
        return 0;
    }

    /**
     * Get all orders for given side by price and time
     * @param side
     * @return
     */
    public List<Order> getAllOrdersForSide(char side) {
        switch(side) {
            case BID:
                List<Order> bidOrderList = new LinkedList<>();
                PriorityQueue<Double> bidMaxPriceListCopy = new PriorityQueue<>(bidMaxPriceList);
                while(!bidMaxPriceListCopy.isEmpty()) {
                    Double price = bidMaxPriceListCopy.poll();
                    bidOrderList.addAll(bidMap.get(price));
                }
                return bidOrderList;

            case OFFER:
                List<Order> offerOrderList = new LinkedList<>();
                PriorityQueue<Double> offerMinPriceListCopy = new PriorityQueue<>(offerMinPriceList);
                while (!offerMinPriceListCopy.isEmpty()) {
                    Double price = offerMinPriceListCopy.poll();
                    offerOrderList.addAll(offerMap.get(price));
                }
                return offerOrderList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Returns bucket list if price match, otherwise returns new list
     * @param hashmap
     * @param price
     * @return
     */
    public List<Order> getBucket(Map<Double, List<Order>> hashmap, Double price)
    {
        List<Order> bucket;
        if(hashmap.containsKey(price))
        {
            bucket = hashmap.get(price);
        }
        else
        {
            bucket = new LinkedList<>();
        }
        return bucket;
    }

    public Map<Double, List<Order>> getBidMap() {
        return bidMap;
    }

    public Map<Double, List<Order>> getOfferMap() {
        return offerMap;
    }

    public Queue<Double> getBidMaxPriceList() {
        return bidMaxPriceList;
    }

    public Queue<Double> getOfferMinPriceList() {
        return offerMinPriceList;
    }
}