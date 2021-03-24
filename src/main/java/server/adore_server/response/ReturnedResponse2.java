package server.adore_server.response;

public class ReturnedResponse2 {

    private long ean;
    private int transaction_id;
    private String date;
    private String fullName;

    public ReturnedResponse2(long ean, int transaction_id, String date, String fullName) {
        this.ean = ean;
        this.transaction_id = transaction_id;
        this.date = date;
        this.fullName = fullName;
    }

    public long getEan() {
        return ean;
    }

    public void setEan(long ean) {
        this.ean = ean;
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
}
