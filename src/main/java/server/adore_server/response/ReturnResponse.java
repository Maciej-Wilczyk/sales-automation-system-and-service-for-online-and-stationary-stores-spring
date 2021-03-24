package server.adore_server.response;

public class ReturnResponse {
    int transaction_id;
    String date;
    String fullName;
    String name;
    String options;


    public ReturnResponse(int transaction_id, String date, String fullName, String name,String options ) {
        this.transaction_id = transaction_id;
        this.date = date;
        this.fullName = fullName;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
