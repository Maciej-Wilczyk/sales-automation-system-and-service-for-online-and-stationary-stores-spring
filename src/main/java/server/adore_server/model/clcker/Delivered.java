package server.adore_server.model.clcker;

import javax.persistence.*;


@Entity
@Table(name = "delivered")
public class Delivered {
    public Delivered() {
        comment = "";
        is_corrected = 0;
        correct_date = "";
        correct_comment = "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "stock_id")
    private long stock_id;

    @Column(name = "productID")
    private int productId;

    @Column(name = "returned")
    private int returned;

    @Column(name = "delivered_id")
    private int delivered_id;

    @Column(name = "ean")
    private Long ean;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "options")
    private String options;

    @Column(name = "price")
    private Double price;

    @Column(name = "purchase_price")
    private Double purchase_price;

    @Column(name = "suppliers")
    private String suppliers;

    @Column(name = "invoice")
    private String invoice;

    @Column(name = "comment")
    private String comment;

    @Column(name = "correct_date")
    private String correct_date;

    @Column(name = "correct_comment")
    private String correct_comment;

    @Column(name = "is_corrected")
    private int is_corrected;

    public String getCorrect_date() {
        return correct_date;
    }

    public void setCorrect_date(String correct_date) {
        this.correct_date = correct_date;
    }

    public String getCorrect_comment() {
        return correct_comment;
    }

    public void setCorrect_comment(String correct_comment) {
        this.correct_comment = correct_comment;
    }

    public int getIs_corrected() {
        return is_corrected;
    }

    public void setIs_corrected(int is_corrected) {
        this.is_corrected = is_corrected;
    }

    public int getDelivered_id() {
        return delivered_id;
    }

    public void setDelivered_id(int delivered_id) {
        this.delivered_id = delivered_id;
    }

    public int getReturned() {
        return returned;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(Double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(String suppliers) {
        this.suppliers = suppliers;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
