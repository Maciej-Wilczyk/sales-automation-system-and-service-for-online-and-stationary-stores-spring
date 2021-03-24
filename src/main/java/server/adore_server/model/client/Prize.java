package server.adore_server.model.client;

import javax.persistence.*;

@Entity
@Table(name = "prize")
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="prize_id")
    private long prizeId;

    @Column(name="needed_points")
    private int neededPoints;

    @Column(name="description")
    private String description;

    public long getPrizeId() {
        return prizeId;
    }

    public int getNeededPoints() {
        return neededPoints;
    }

    public void setNeededPoints(int neededPoints) {
        this.neededPoints = neededPoints;
    }

    public void setPrizeId(long prizeId) {
        this.prizeId = prizeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
