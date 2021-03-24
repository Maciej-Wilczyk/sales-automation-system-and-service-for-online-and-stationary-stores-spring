package server.adore_server.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Component;
import server.adore_server.request.helpObj.OptionsStocks;
import server.adore_server.request.helpObj.Stock;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductEditRequest {

    private Stock stock;

    private long product_id;

    private OptionsStocks[] optionsStocks;

    public OptionsStocks[] getOptionsStocks() {
        return optionsStocks;
    }

    public OptionsStocks getOneOptionsStock(int a)
    {
        return optionsStocks[a];
    }

    public void setOptionsStocks(OptionsStocks[] optionsStocks) {
        this.optionsStocks = optionsStocks;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
