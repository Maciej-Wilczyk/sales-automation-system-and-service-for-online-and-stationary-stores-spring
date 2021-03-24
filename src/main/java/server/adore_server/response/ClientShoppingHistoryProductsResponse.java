package server.adore_server.response;

public interface ClientShoppingHistoryProductsResponse {
    String getDate();
    String getProduct_name();
    double getAfter_disc();
    int getReturned();
    String getReturned_date();
    double getPrice_brutto();
    String getOptions();


}
