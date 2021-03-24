package server.adore_server.model.clcker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "option_values")
public class OptionValues {

    public OptionValues() {
    }
    public OptionValues(long ovalue_id, String value) {
        this.ovalue_id = ovalue_id;
        this.value = value;
    }

    @Id
    @Column(name = "ovalue_id")
    private long ovalue_id;

    @Column(name = "value")
    private String value;

    public long getOvalue_id() {
        return ovalue_id;
    }

    public void setOvalue_id(long ovalue_id) {
        this.ovalue_id = ovalue_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
