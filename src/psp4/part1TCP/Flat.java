package psp4.part1TCP;

import java.io.Serializable;

public class Flat implements Serializable {
    double price;
    private String street;
    private int flatNum;


    public Flat() {
    }

    public Flat(double price, String street, int flatNum) {
        this.price = price;
        this.street = street;
        this.flatNum = flatNum;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "\nFlat" +
                "\nprice: " + price +
                "\naddress: " +
                "\n\tstreet: " + street +
                "\n\tflatNum: " + flatNum ;
    }
}
