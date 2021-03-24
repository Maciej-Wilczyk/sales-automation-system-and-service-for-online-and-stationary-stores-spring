package server.adore_server.model.clcker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tags {

    public Tags() {
    }

    public Tags(int tag_id, String name, int lang_id) {
        this.tag_id = tag_id;
        this.name = name;
        this.lang_id = lang_id;
    }

    @Id
    @Column(name = "tag_id")
    private int tag_id;

    @Column(name = "name")
    private String name;

    @Column(name = "lang_id")
    private int lang_id;

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLang_id() {
        return lang_id;
    }

    public void setLang_id(int lang_id) {
        this.lang_id = lang_id;
    }
}
