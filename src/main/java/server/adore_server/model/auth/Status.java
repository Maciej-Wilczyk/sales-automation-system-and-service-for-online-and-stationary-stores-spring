package server.adore_server.model.auth;

public class Status {
    private long id;
    private String name;
    private String allegroOrShop;

    public Status(long id, String name,String allegroOrShop ) {
        this.id = id;
        this.name = name;
        this.allegroOrShop= allegroOrShop;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllegroOrShop() {
        return allegroOrShop;
    }

    public void setAllegroOrShop(String allegroOrShop) {
        this.allegroOrShop = allegroOrShop;
    }
}
