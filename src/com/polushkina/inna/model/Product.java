package com.polushkina.inna.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * The class Product for working with products data
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private String producer;
    private int price;
    private Date life;
    private int count;


    public Product(int id, String name, String producer, int price, Date life, int count) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.life = life;
        this.count = count;
    }

    public Product() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getLife() {
        return life;
    }

    public void setLife(Date life) {
        this.life = life;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", life=" + life +
                ", count=" + count +
                '}';
    }
}
