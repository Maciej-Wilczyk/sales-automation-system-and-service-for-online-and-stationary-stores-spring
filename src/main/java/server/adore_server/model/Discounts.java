package server.adore_server.model;

import javax.persistence.*;

@Entity
@Table(name = "discounts")
public class Discounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private long discountId;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "value")
    private double value;

    @Column(name = "discount_type")
    private int discountType;

    @Column(name = "minimum_value")
    private double minimumValue;

    public Discounts() {
    }


    public double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }
}
