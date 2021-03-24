package server.adore_server.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.adore_server.request.helpObj.Products;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPaidRequest {

    private Products[] products;

    public Products[] getProducts() {
        return products;
    }

    public void setProducts(Products[] products) {
        this.products = products;
    }
           }
