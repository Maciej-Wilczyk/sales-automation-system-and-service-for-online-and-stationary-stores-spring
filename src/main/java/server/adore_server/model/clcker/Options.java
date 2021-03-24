package server.adore_server.model.clcker;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "options")
public class Options {

    public Options() {
    }

    @Id
    @Column(name = "option_id")
    private long option_id;

    @Column(name = "name")
    private String name;


    public long getOption_id() {
        return option_id;
    }

    public void setOption_id(long option_id) {
        this.option_id = option_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Options(long option_id, String name) {
        this.option_id = option_id;
        this.name = name;
    }

}
