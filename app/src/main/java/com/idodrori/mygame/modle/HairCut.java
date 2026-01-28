package com.idodrori.mygame.modle;

public class HairCut {
    private String id;
    private String name;
    private double price;
    private String type;
    private String size;
    private String details;
    private String pic;

    public HairCut() {}

    public HairCut(String id, String name, double price, String type, String size, String details, String pic) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.size = size;
        this.details = details;
        this.pic = pic;
    }

    // Getters ו-Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "HairCut{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", details='" + details + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}