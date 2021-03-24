package server.adore_server.response;

import server.adore_server.model.SaleRecord;

public class StockTableResponse2 {

    public StockTableResponse2(Long ean, double price, double promo_price, String options, int productId, long stock_id, int isDiscounted) {
        this.ean = ean;
        this.price = price;
        this.promo_price = promo_price;
        this.options = options;
        this.productId = productId;
        this.stock_id = stock_id;
        this.isDiscounted = isDiscounted;
    }

    private int productId;

    private long stock_id;

    private Long ean;

    private double price;

    private double promo_price;

    private String options;

    private int isDiscounted;

    public int getIsDiscounted() {
        return isDiscounted;
    }

    public void setIsDiscounted(int isDiscounted) {
        this.isDiscounted = isDiscounted;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getStock_id() {
        return stock_id;
    }

    public void setStock_id(long stock_id) {
        this.stock_id = stock_id;
    }

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPromo_price() {
        return promo_price;
    }

    public void setPromo_price(double promo_price) {
        this.promo_price = promo_price;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
