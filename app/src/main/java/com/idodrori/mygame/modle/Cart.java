package com.idodrori.mygame.modle;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {

    /// unique id of the cart


    private ArrayList<HairCut> hairCuts;

    public Cart() {
        hairCuts = new ArrayList<>();
    }

    public Cart(ArrayList<HairCut> hairCuts) {
        this.hairCuts = hairCuts;
    }

    public HairCut removeHairCut(int index) {
        if (index < 0 || index >= hairCuts.size()) {
            return null;
        }
        return hairCuts.remove(index);
    }

    public void addHairCut(HairCut hairCut) {
        if (hairCut != null) {
            boolean found = false;

            for (int i = 0; i < this.hairCuts.size(); i++) {
                if (this.hairCuts.get(i).getId().equals(hairCut.getId())) {
                    found = true;

                }
            }

            if (!found) {
                this.getHairCuts().add(hairCut);


            }

        }

    }

    public HairCut getHairCut(int index) {
        if (index < 0 || index >= hairCuts.size()) {
            return null;
        }
        return hairCuts.get(index);
    }


    public double getTotalPrice() {
        double totalPrice = 0;
        for (HairCut hairCut : this.hairCuts) {
            totalPrice += hairCut.getPrice();
        }
        return totalPrice;
    }

    public void clear() {
        hairCuts.clear();
    }

    public ArrayList<HairCut> getHairCuts() {
        return hairCuts;
    }

    public void setHairCuts(ArrayList<HairCut> hairCuts) {
        this.hairCuts = hairCuts;
    }

    public void delHairCutFromCart(HairCut hairCut) {

        if (hairCut != null) {

            for (int i = 0; i < this.getHairCuts().size(); i++) {
                if (this.getHairCuts().get(i).getId().equals(hairCut.getId()))

                    this.getHairCuts().remove(i);

            }


        }
    }


    public void updateHairCutOrder(HairCut hairCut) {

        if (hairCut != null) {

            for (int i = 0; i < this.getHairCuts().size(); i++) {
                if (this.getHairCuts().get(i).getId().equals(hairCut.getId())) ;


            }


        }
    }


//    public ArrayList<HairCutOrder> getHairCutOrders() {
//
//        for (int i = 0; i < this.getHairCuts().size(); i++) {
//            if (this.getHairCuts().get(i).amount == 0)
//
//                this.getHairCuts().remove(i);
//
//        }
//        return getHairCuts();
//
//    }


    @Override
    public String toString() {
        return "Cart{" +
                "hairCuts=" + hairCuts +
                '}';
    }
}
