package com.arun.orderbook.com.arun.order.model;

public class Order {
    private long id; // id of order
    private double price;
    private char side; // B "Bid" or O "Offer"
    private long size;

    public Order(long id, char side, long size, double price) {
        this.id = id;
        this.price = price;
        this.size = size;
        this.side = side;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public char getSide() {
        return side;
    }

    public long getSize() {
        return size;
    }
}