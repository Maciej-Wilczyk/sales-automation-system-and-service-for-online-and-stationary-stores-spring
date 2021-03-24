package server.adore_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import server.adore_server.model.client.ClientCard;
import server.adore_server.model.client.ClientShopping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sale_record")
public class SaleRecord {
    public SaleRecord() {
        productId = -1;
        stock_id = -1;
        baselinker_fullname = "";
        ean = -1;
        options = "";
        returned = 0;
        returned_comment = "";
        returned_date = "";
        returned_employee = "";
        comment = "";
        change_=0;
        price_brutto = 0;
        price = 0;
        delivery_price = 0;
        bank = 0;
        card = 0;
        cash = 0;
        phoneNr = 0;
        producer = "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private long recordId;

    @Column(name = "EAN")
    private long ean;

    @Column(name = "stock_id")
    private long stock_id;

    @Column(name = "productID")
    private int productId;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "date")
    private String date;

    @Column(name = "all_discc_per")
    private double alldiscper;

    @Column(name = "all_discc_mon")
    private double alldiscmon;

    @Column(name = "discc_per")
    private double discper;

    @Column(name = "transaction_id")
    private int transaction_id;

    @Column(name = "after_disc")
    private double afterdisc;

    @Column(name = "options")
    private String options;

    @Column(name = "phone_nr")
    private int phoneNr;

    @Column(name = "where_")
    private String where_;

    @Column(name = "disco_mon")
    private double discmon;

    @Column(name = "comment")
    private String comment;

    @Column(name = "cash")
    private double cash;

    @Column(name = "card")
    private double card;

    @Column(name = "bank")
    private double bank;

    @Column(name = "employee")
    private String employee;

    @Column(name = "delivery_price")
    private double delivery_price;

    @Column(name = "price_brutto")
    private double price_brutto;

    @Column(name = "baselinker_fullname")
    private String baselinker_fullname;

    @Column(name = "baselinker_id")
    private long baselinker_id;

    @Column(name = "returned")
    private int returned;

    @Column(name = "returned_comment")
    private String returned_comment;

    @Column(name = "returned_date")
    private String returned_date;

    @Column(name = "returned_employee")
    private String returned_employee;

    @Column(name = "change_")
    private int change_;

    @Column(name = "producer")
    private String producer;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="card_id", referencedColumnName = "card_id")
    private ClientCard clientCard1;

    public ClientCard getClientCard() {
        return clientCard1;
    }

    public void setClientCard(ClientCard clientCard) {
        this.clientCard1 = clientCard;
    }

    //    @Column(name = "card_nr")
//    private String cardNr;

//    @JsonIgnore
//    @JoinColumn(name="card_id", referencedColumnName = "card_id")
//    @ManyToMany(cascade = CascadeType.ALL)
//    private ClientShopping clientShopping;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "clientCard")
//    private List<ClientShopping> clientShopping = new ArrayList<>();

//    public ClientShopping getClientShopping() {
//        return clientShopping;
//    }
//
//    public void setClientShopping(ClientShopping clientShopping) {
//        this.clientShopping = clientShopping;
//    }

//
//    public List<ClientShopping> getClientShopping() {
//        return clientShopping;
//    }
//
//    public void setClientShopping(List<ClientShopping> clientShopping) {
//        this.clientShopping = clientShopping;
//    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getChange_() {
        return change_;
    }

    public void setChange_(int change_) {
        this.change_ = change_;
    }

    public String getReturned_employee() {
        return returned_employee;
    }

    public void setReturned_employee(String returned_employee) {
        this.returned_employee = returned_employee;
    }

    public double getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(double delivery_price) {
        this.delivery_price = delivery_price;
    }

    public double getPrice_brutto() {
        return price_brutto;
    }

    public void setPrice_brutto(double price_brutto) {
        this.price_brutto = price_brutto;
    }

    public String getBaselinker_fullname() {
        return baselinker_fullname;
    }

    public void setBaselinker_fullname(String baselinker_fullname) {
        this.baselinker_fullname = baselinker_fullname;
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

    public long getBaselinker_id() {
        return baselinker_id;
    }

    public void setBaselinker_id(long baselinker_id) {
        this.baselinker_id = baselinker_id;
    }

    public double getAlldiscper() {
        return alldiscper;
    }

    public void setAlldiscper(double alldiscper) {
        this.alldiscper = alldiscper;
    }

    public double getAlldiscmon() {
        return alldiscmon;
    }

    public void setAlldiscmon(double alldiscmon) {
        this.alldiscmon = alldiscmon;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWhere_() {
        return where_;
    }

    public void setWhere_(String where_) {
        this.where_ = where_;
    }

    public int getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(int phoneNr) {
        this.phoneNr = phoneNr;
    }

    public double getAfterdisc() {
        return afterdisc;
    }

    public void setAfterdisc(double afterdisc) {
        this.afterdisc = afterdisc;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getEan() {
        return ean;
    }

    public void setEan(long ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String productName) {
        this.name = productName;
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


    public double getDiscper() {
        return discper;
    }

    public void setDiscper(double discper) {
        this.discper = discper;
    }

    public double getDiscmon() {
        return discmon;
    }

    public void setDiscmon(double discmon) {
        this.discmon = discmon;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transactionId) {
        this.transaction_id = transactionId;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCard() {
        return card;
    }

    public void setCard(double card) {
        this.card = card;
    }

    public double getBank() {
        return bank;
    }

    public void setBank(double bank) {
        this.bank = bank;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public String getReturned_comment() {
        return returned_comment;
    }

    public void setReturned_comment(String returned_comment) {
        this.returned_comment = returned_comment;
    }

    public String getReturned_date() {
        return returned_date;
    }

    public void setReturned_date(String returned_date) {
        this.returned_date = returned_date;
    }
}
