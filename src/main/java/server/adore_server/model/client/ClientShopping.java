package server.adore_server.model.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import server.adore_server.model.SaleRecord;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "client_shopping")
public class ClientShopping implements Serializable {

    public ClientShopping() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_shopping_id")
    private long clientShoppingId;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="card_id", referencedColumnName = "card_id")
    private ClientCard clientCard;



//    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy="clientShopping")
//    private List<SaleRecord> saleRecords;

//    @ManyToMany(
//            cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "sr_cs",
//            joinColumns = @JoinColumn(name = "card_id"),
//            inverseJoinColumns = @JoinColumn(name = "record_id")
//    )
//    private List<SaleRecord> saleRecords = new ArrayList<>();

//    public List<SaleRecord> getSaleRecords() {
//        return saleRecords;
//    }
//
//    public void setSaleRecords(List<SaleRecord> saleRecords) {
//        this.saleRecords = saleRecords;
//    }
//
//    public void addSaleRecord(SaleRecord saleRecord) {
//        this.saleRecords.add(saleRecord);
//        saleRecord.getClientShopping().add(this);
//    }
//
//    public void removeGroup(SaleRecord saleRecord) {
//        this.saleRecords.remove(saleRecord);
//        saleRecord.getClientShopping().remove(this);
//    }

    @Column(name="points")
    private double points;

    @Column(name="date")
    private String date;

    @Column(name="transaction_id")
    private int transaction_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public long getClientShoppingId() {
        return clientShoppingId;
    }

    public void setClientShoppingId(long clientShoppingId) {
        this.clientShoppingId = clientShoppingId;
    }

    public ClientCard getClientCard() {
        return clientCard;
    }

    public void setClientCard(ClientCard clientCard) {
        this.clientCard = clientCard;
    }

//    public List<SaleRecord> getSaleRecords() {
//        return saleRecords;
//    }
//
//    public void setSaleRecords(List<SaleRecord> saleRecords) {
//        this.saleRecords = saleRecords;
//    }
}
