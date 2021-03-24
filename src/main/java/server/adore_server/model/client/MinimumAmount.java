package server.adore_server.model.client;

import javax.persistence.*;

@Entity
@Table(name = "minimum_amount")
public class MinimumAmount {

    @Id
    @Column(name="amount")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
