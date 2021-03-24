package server.adore_server.response;

public class StockTableResponse3 {
    public StockTableResponse3(Long ean, double price, double promo_price, int productId, long stock_id, int isDiscounted) {
        this.ean = ean;
        this.price = price;
        this.promo_price = promo_price;this.productId = productId;
        this.stock_id = stock_id;
        this.isDiscounted = isDiscounted;

    }

    private int productId;

    private long stock_id;

    private Long ean;

    private double price;

    private double promo_price;

    private int isDiscounted;

    public int getIsDiscounted() {
        return isDiscounted;
    }

    public void setIsDiscounted(int isDiscounted) {
        this.isDiscounted = isDiscounted;
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
}
