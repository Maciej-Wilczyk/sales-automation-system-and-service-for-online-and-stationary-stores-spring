package server.adore_server.request.helpObj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
    private long stock_id;
    private long product_id;
    private long ean;
    private int active;
    private int stock;
    private double price;
    private OptionsStocks optionsStocks;
    private Options options;

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    private long getEan() {
        return ean;
    }

    private void setEan(long ean) {
        this.ean = ean;
    }

    public long getStock_id() {
        return stock_id;
    }

    public void setStock_id(long stock_id) {
        this.stock_id = stock_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OptionsStocks getOptionsStocks() {
        return optionsStocks;
    }

    public void setOptionsStocks(OptionsStocks optionsStocks) {
        this.optionsStocks = optionsStocks;
    }
}