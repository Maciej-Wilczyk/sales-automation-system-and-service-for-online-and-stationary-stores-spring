package server.adore_server.request;

import javax.persistence.Column;

public class AddRequest {

    private int ean;

    private String productName;

    private double price;

    private String date;

    private String payment;

    private double discount;

    private int afterdisc;

    public int getEan() {
        return ean;
    }

    public void setEan(int ean) {
        this.ean = ean;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getAfterdisc() {
        return afterdisc;
    }

    public void setAfterdisc(int afterdisc) {
        this.afterdisc = afterdisc;
    }
}
