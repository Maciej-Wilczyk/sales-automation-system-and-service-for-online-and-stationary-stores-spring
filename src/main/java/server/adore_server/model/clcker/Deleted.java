package server.adore_server.model.clcker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deleted")
public class Deleted {

    public Deleted() {
    }

    @Id
    @Column(name = "stock_id")
    private long stock_id;

    @Column(name = "productID")
    private int productId;

    @Column(name = "active")

    private int active;

    @Column(name = "extended")
    private int extended;

    @Column(name = "ean")
    private Long ean;

    @Column(name = "name")
    private String name;

    @Column(name = "options")
    private String options;

    @Column(name = "price")
    private Double price;

    @Column(name = "promo_price")
    private Double promo_price;

    @Column(name = "stock")
    private int stock;

    @Column(name = "tags")
    private String tags;

    @Column(name = "producer")
    private String producer;

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public long getStock_id() {
        return stock_id;
    }

    public void setStock_id(long stock_id) {
        this.stock_id = stock_id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getExtended() {
        return extended;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getPromo_price() {
        return promo_price;
    }

    public void setPromo_price(Double promo_price) {
        this.promo_price = promo_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
