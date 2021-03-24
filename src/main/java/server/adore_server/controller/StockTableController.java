package server.adore_server.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.clcker.Deleted;
import server.adore_server.model.StockTable;
import server.adore_server.repository.StockTableRepository;
import server.adore_server.request.*;
import server.adore_server.request.helpObj.OptionsStocks;
import server.adore_server.request.helpObj.Products;
import server.adore_server.response.StockTableResponse;
import server.adore_server.response.StockTableResponse2;
import server.adore_server.response.StockTableResponse3;
import server.adore_server.service.rarelyUsed.DeletedService;
import server.adore_server.service.StockTableService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockTableController {
    @Autowired
    StockTableService stockTableService;

    @Autowired
    StockTableRepository stockTableRepository;

    @Autowired
    DeletedService deletedService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/ean")
    public ResponseEntity<?> getEanGiveInfoProduct(@Valid @RequestBody EanRequest eanRequest) {
        StockTableResponse stockTableResponse = stockTableService.getSaleRecordForClientByEan(eanRequest.getEan());
        return ResponseEntity.ok(stockTableResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/name")
    public ResponseEntity<?> getNameGiveInfoProduct(@Valid @RequestBody NameRequest nameRequest) {
        StockTableResponse2 stockTableResponse2 = stockTableService.getSaleRecordForClientByName(nameRequest.getName());
        return ResponseEntity.ok(stockTableResponse2);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/optionsList")
    public List<Object> getNameGiveOptionsList(@Valid @RequestBody NameRequest nameRequest) {

        return stockTableService.getOptionsList(nameRequest.getName());
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/nameAndOptions")
    public ResponseEntity<?> getNameAndOptionsGiveInfoProduct(@Valid @RequestBody NameAndOptionsRequest nameAndOptionsRequestRequest) {
        StockTableResponse3 stockTableResponse3 = stockTableService.getSaleRecordForClientByNameAndOptions(nameAndOptionsRequestRequest.getName(), nameAndOptionsRequestRequest.getOptions());
        return ResponseEntity.ok(stockTableResponse3);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/filterByName")
    public List<List> filterByName(@RequestBody OneStringRequest s) {
        return stockTableService.filterByName(s.getS());
    }


    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/updateEditProduct")
    public void updateEditProduct(@RequestBody ProductEditRequest productEditRequest) {
        JSONObject jsonObject;
        JSONObject jsonObject1;
        boolean isExist = false;
        ProductEditRequest productEditRequest1 = stockTableService.getIdEditingProduct(productEditRequest);
        try {
            //first stock id
            jsonObject = stockTableService.connectionToAdore1("https://example/webapi/rest/product-stocks/", productEditRequest1.getStock().getStock_id());
            jsonObject1 = stockTableService.connectionToAdore1("https://example/webapi/rest/products/", productEditRequest1.getStock().getProduct_id());
            stockTableService.updateEditingProduct(jsonObject, jsonObject1, jsonObject.getLong("stock_id"), jsonObject.getLong("product_id"));

            for (OptionsStocks i : productEditRequest1.getOptionsStocks()) {
                jsonObject = stockTableService.connectionToAdore1("https://example/webapi/rest/product-stocks/", i.getStock_id());
                jsonObject1 = stockTableService.connectionToAdore1("https://example/webapi/rest/products/", productEditRequest1.getStock().getProduct_id());
                stockTableService.updateEditingProduct(jsonObject, jsonObject1, jsonObject.getLong("stock_id"), jsonObject.getLong("product_id"));
            }
            var listOptions = stockTableRepository.getAllExtended1ProductId((int) productEditRequest.getProduct_id());
            for (StockTable i : listOptions) {
                for (OptionsStocks j : productEditRequest1.getOptionsStocks()) {
                    if (i.getStock_id() == j.getStock_id()) {
                        isExist = true;
                        continue;
                    }
                }
                if (isExist == true)
                    isExist = false;
                else {
                    Deleted deleted = new Deleted();
                    deleted.setStock_id(i.getStock_id());
                    deleted.setExtended(i.getExtended());
                    deleted.setStock(i.getStock());
                    deleted.setActive(i.getActive());
                    deleted.setEan(i.getEan());
                    deleted.setProductId(i.getProductId());
                    deleted.setOptions(i.getOptions());
                    deleted.setName(i.getName());
                    deleted.setPrice(i.getPrice());
                    deleted.setPromo_price(i.getPromo_price());
                    deleted.setProducer(i.getProducer());
                    deletedService.addToDeleted(deleted);
                    stockTableService.deleteOptions(i.getStock_id());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/updateEditOrder")
    public boolean updateEditOrder(@RequestBody OrderPaidRequest orderEditRequest) throws IOException {
        OrderPaidRequest orderPaidRequest1 = stockTableService.getIdOrderingProduct(orderEditRequest);
        JSONObject jsonObject;
        JSONObject jsonObject1;
        try {

            for (Products i : orderPaidRequest1.getProducts()) {
                jsonObject = stockTableService.connectionToAdore1("https://example/webapi/rest/product-stocks/", i.getStock_id());
                jsonObject1 = stockTableService.connectionToAdore1("https://example/webapi/rest/products/", i.getProduct_id());
                stockTableService.updateEditingProduct(jsonObject, jsonObject1, i.getStock_id(), i.getProduct_id());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/deleteProduct")
    public int deleteProduct(@RequestBody ProductEditRequest productEditRequest) {
        ProductEditRequest productEditRequest1 = stockTableService.getIdEditingProduct(productEditRequest);
        System.out.println(productEditRequest1.getProduct_id());
        int a = stockTableService.deleteProduct(productEditRequest1.getProduct_id());
        return a;
    }


}


