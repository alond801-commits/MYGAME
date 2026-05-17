package com.idodrori.mygame.modle;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Order implements Serializable {
    private String orderId;
    private List<HairCut> hairCuts;
    private double totalPrice;
    private String status;



    private User user;

    private long timestamp;

    private long dateHairCut;
    public Order(String orderId, List<HairCut> hairCuts, double totalPrice, String status, User user, long timestamp) {
        this.orderId = orderId;
        this.hairCuts = hairCuts;
        this.totalPrice = totalPrice;
        this.status = status;
        this.user = user;
        this.timestamp = timestamp;
        this.dateHairCut=0;
    }

    public Order(String orderId, List<HairCut> hairCuts, double totalPrice, String status, User user, long timestamp, long dateHairCut) {
        this.orderId = orderId;
        this.hairCuts = hairCuts;
        this.totalPrice = totalPrice;
        this.status = status;
        this.user = user;
        this.timestamp = timestamp;
        this.dateHairCut = dateHairCut;
    }

    public Order() {
    }

    private double calculateTotalPrice() {
        double sum = 0;
        for (HairCut hairCut : hairCuts) {
            sum += hairCut.getPrice();
        }
        return sum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<HairCut> getHairCuts() {
        return hairCuts;
    }

    public void setHairCuts(List<HairCut> hairCuts) {
        this.hairCuts = hairCuts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(this.timestamp));
    }

    public String getFormattedDate( long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public long getDateHairCut() {
        return dateHairCut;
    }

    public void setDateHairCut(long dateHairCut) {
        this.dateHairCut = dateHairCut;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", hairCuts=" + hairCuts +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", timestamp=" + timestamp +
                ", dateHairCut=" + dateHairCut +
                '}';
    }
}
