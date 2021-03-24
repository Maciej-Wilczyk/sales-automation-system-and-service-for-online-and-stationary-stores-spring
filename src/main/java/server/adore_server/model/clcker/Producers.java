package server.adore_server.model.clcker;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "producers")
public class Producers {

    public Producers() {
    }

    public Producers(long producer_id, String name) {
        this.producer_id = producer_id;
        this.name = name;
    }

    @Id
    @Column(name = "producer_id")
    private long producer_id;

    @Column(name = "name")
    private String name;

    public long getProducer_id() {
        return producer_id;
    }

    public void setProducer_id(long producer_id) {
        this.producer_id = producer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
