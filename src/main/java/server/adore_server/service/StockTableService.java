package server.adore_server.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.adore_auth.AdoreToken;

import server.adore_server.model.clcker.Deleted;
import server.adore_server.model.StockTable;
import server.adore_server.repository.*;
import server.adore_server.repository.rarelyUsed.OptionValuesRepository;
import server.adore_server.repository.rarelyUsed.OptionsRepository;
import server.adore_server.repository.rarelyUsed.ProducersRepository;
import server.adore_server.repository.rarelyUsed.TagsRepository;
import server.adore_server.request.OrderPaidRequest;
import server.adore_server.request.ProductEditRequest;
import server.adore_server.request.helpObj.OptionsStocks;
import server.adore_server.request.helpObj.Products;
import server.adore_server.request.helpObj.Stock;
import server.adore_server.response.StockTableResponse;
import server.adore_server.response.StockTableResponse2;
import server.adore_server.response.StockTableResponse3;
import server.adore_server.service.rarelyUsed.DeletedService;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.lang.Object;

import java.util.Collections;
import java.util.List;
import java.lang.*;
import java.util.NoSuchElementException;

@Service
public class StockTableService {

    @Autowired
    StockTableRepository stockTableRepository;

    @Autowired
    ProducersRepository producersRepository;

    @Autowired
    OptionsRepository optionsRepository;

    @Autowired
    OptionValuesRepository optionValuesRepository;

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    SaleRecordService saleRecordService;

    @Autowired
    DeletedService deletedService;

    public StockTableResponse getSaleRecordForClientByEan(long ean) {
        StockTable stockTable = stockTableRepository.findByEan(ean).orElseThrow();
        StockTableResponse stockTableResponse = new StockTableResponse(stockTable.getName(), stockTable.getPrice(), stockTable.getPromo_price(), stockTable.getOptions(), stockTable.getProductId(), stockTable.getStock_id(), stockTable.getIsDiscounted());

        return stockTableResponse;
    }

    public StockTableResponse2 getSaleRecordForClientByName(String name) {
        StockTable stockTable = stockTableRepository.findByName(name).orElseThrow();
        StockTableResponse2 stockTableResponse2 = new StockTableResponse2(stockTable.getEan(), stockTable.getPrice(), stockTable.getPromo_price(), stockTable.getOptions(), stockTable.getProductId(), stockTable.getStock_id(), stockTable.getIsDiscounted());

        return stockTableResponse2;

    }

    public StockTableResponse3 getSaleRecordForClientByNameAndOptions(String name, String options) {
        StockTable stockTable = stockTableRepository.findByNameAndOptions(name, options).orElseThrow();
        StockTableResponse3 stockTableResponse3 = new StockTableResponse3(stockTable.getEan(), stockTable.getPrice(), stockTable.getPromo_price(), stockTable.getProductId(), stockTable.getStock_id(), stockTable.getIsDiscounted());

        return stockTableResponse3;

    }

    public ProductEditRequest getIdEditingProduct(ProductEditRequest productEditRequest) { // getting id to next request to shop API
        int j = 0;

        ProductEditRequest productEditRequest1 = new ProductEditRequest();

        productEditRequest1.setProduct_id(productEditRequest.getProduct_id());

        Stock stock = new Stock();
        stock.setStock_id(productEditRequest.getStock().getStock_id());
        stock.setProduct_id(productEditRequest.getStock().getProduct_id());
        productEditRequest1.setStock(stock);

        OptionsStocks[] optionsStocks = new OptionsStocks[productEditRequest.getOptionsStocks().length];

        for (OptionsStocks i : productEditRequest.getOptionsStocks()) {
            optionsStocks[j] = new OptionsStocks();
            optionsStocks[j].setStock_id(i.getStock_id());
            j++;
        }

        productEditRequest1.setOptionsStocks(optionsStocks);

        return productEditRequest1;
    }

    public OrderPaidRequest getIdOrderingProduct(OrderPaidRequest orderPaidRequest) { // getting id to next request to shop API
        int j = 0;

        OrderPaidRequest orderPaidRequest1 = new OrderPaidRequest();

        Products[] products = new Products[orderPaidRequest.getProducts().length];

        for (Products i : orderPaidRequest.getProducts()) {
            products[j] = new Products();
            products[j].setStock_id(i.getStock_id());
            products[j].setProduct_id(i.getProduct_id());
            j++;
        }

        orderPaidRequest1.setProducts(products);

        return orderPaidRequest1;
    }

    public JSONObject connectionToAdore1(String url1, long id) throws Exception {
        boolean flag;
        int responseCode;
        HttpsURLConnection con;
        URL obj;
        try {


            String url = url1 + id;
            String tokenS = "Bearer " + AdoreToken.access_token;
            do {
                flag = false;
                obj = new URL(url);
                con = (HttpsURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", tokenS);
                responseCode = con.getResponseCode();

                if (responseCode == 429)
                    flag = true;
            }
            while (flag == true);
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());

            JSONObject jsonObj = new JSONObject(response.toString());

            return jsonObj;

        } catch (Exception e) {
            return null;
        }

    }


    public boolean updateEditingProduct(JSONObject jsonObjectProductStock, JSONObject jsonObjectProducts, long a, long b) {
        try {
            long ean;
            double price;
            double promo_price;
            long producer_id;
            int optionsAmount = 200;
            int active;
            List<Integer> optionsList = new ArrayList<>();
            List<Integer> optionsListV = new ArrayList<>();

            long stock_id = a;

            long product_id = b;

            try {
                ean = jsonObjectProductStock.getLong("ean");
            } catch (Exception e) {
                ean = -1;
            }
            int extended = jsonObjectProductStock.getInt("extended");

            if (jsonObjectProductStock.getInt("price_type") == 0) {
                price = jsonObjectProducts.getJSONObject("stock").getDouble("comp_price");
                promo_price = price = jsonObjectProducts.getJSONObject("stock").getDouble("comp_promo_price");
            } else {

                try {
                    promo_price = jsonObjectProductStock.getDouble("comp_promo_price");
                } catch (Exception e) {
                    promo_price = 0;
                }

                if (jsonObjectProductStock.get("comp_price").equals(""))
                    price = -1;
                else
                    price = jsonObjectProductStock.getDouble("comp_price");
            }


            int stock = jsonObjectProductStock.getInt("stock");

            try {

                JSONObject options = jsonObjectProductStock.getJSONObject("options");

                for (int i = 0; i < optionsAmount; i++) { // i to options la to optionsValue

                    try {
                        options.get(Integer.toString(i));

                        int la = Integer.parseInt(options.get(Integer.toString(i)).toString());
                        optionsListV.add(la);
                        optionsList.add(i);
                    } catch (Exception e) {

                        continue;
                    }
                }
            } catch (Exception e) {
                System.out.println("optionsy puste wiec array");
                optionsList.add(-1);
                optionsListV.add(-1);
            }


            if (jsonObjectProducts.get("producer_id").toString() == "null")
                producer_id = -1;
            else {
                producer_id = jsonObjectProducts.getInt("producer_id");
            }


            if (extended == 0)
                active = jsonObjectProducts.getJSONObject("translations").getJSONObject("pl_PL").getInt("active");
            else
                active = jsonObjectProductStock.getInt("active");
            String name = jsonObjectProducts.getJSONObject("translations").getJSONObject("pl_PL").getString("name");
            List<Integer> tags = new ArrayList<>();
            JSONArray jsonArray = jsonObjectProducts.getJSONArray("tags");

            for (int j = 0; j < jsonArray.length(); j++)
                tags.add((int) jsonArray.get(j));
            StockTable stockTable;

            try {
                stockTable = stockTableRepository.findById(stock_id).orElseThrow();
                stockTable = stockTableRepository.getOne(stock_id);
            } catch (Exception e) {
                stockTable = new StockTable();
                stockTable.setStock_id(stock_id);

            }

            stockTable.setStock(stock);
            stockTable.setEan(ean);
            stockTable.setActive(active);
            stockTable.setExtended(extended);
            stockTable.setName(name);
            stockTable.setPromo_price(promo_price);
            stockTable.setPrice(price);
            stockTable.setProductId((int) product_id);

            String tagToPr = "";
            for (Integer i : tags) {
                tagToPr += Integer.toString(i);
                tagToPr += " ";
            }
            stockTable.setTags(tagToPr);

            String optionsToStockTable = "";
            if (optionsList.get(0) != -1) {
                for (int i = 0; i < optionsList.size(); i++) {
                    optionsToStockTable += optionsRepository.findById((long) optionsList.get(i)).get().getName();
                    optionsToStockTable += " ";
                    optionsToStockTable += optionValuesRepository.findById((long) optionsListV.get(i)).get().getValue();
                    optionsToStockTable += " ";
                }
                stockTable.setOptions(optionsToStockTable);
            } else {
                stockTable.setOptions("");
            }
            if (producer_id != -1) {
                String producer = producersRepository.findById(producer_id).get().getName();
                stockTable.setProducer(producer);
            } else
                stockTable.setProducer("");

            if (promo_price != price)
                stockTable.setIsDiscounted(1);
            else
                stockTable.setIsDiscounted(0);

            stockTableRepository.save(stockTable);

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Modifying
    public int deleteProduct(long product_id) {
        try {

            var list = stockTableRepository.getAllByProductId((int) product_id);
            for (StockTable i : list) {
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
                deleted.setTags(i.getTags());
                deletedService.addToDeleted(deleted);
            }
            return stockTableRepository.deleteProduct(product_id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Transactional
    @Modifying
    public int deleteOptions(long stock_id) {
        try {
            System.out.println(stock_id);
            return stockTableRepository.deleteOptions(stock_id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<List> filterByName(String s) {
        List<List> list = new ArrayList<>();
        List<List> list2 = new ArrayList<>();

        char[] name;
        String nameString;
        char[] s1 = s.toLowerCase().toCharArray();
        boolean pom = false, pom2 = false, hard = false;

        boolean isAnyOptions;
        for (StockTable i : stockTableRepository.getAllExtended0()) {
            name = i.getName().toLowerCase().toCharArray();
            nameString = i.getName().toLowerCase();
            for (int j = 0; j < (s1.length <= name.length ? s1.length : name.length); j++) {
                if (name[j] == s1[j]) {
                    pom = true;
                    if (j == (s1.length <= name.length ? s1.length : name.length) - 1) {
                        hard = true;
                    }
                } else if (nameString.contains(s) && hard == false) {
                    pom2 = true;

                } else {
                    pom2 = false;
                    pom = false;
                    break;
                }
            }
            if (list.size() == 10)
                break;
            if (list2.size() == 10)
                break;

            if (pom == true && hard == true) {

                List<Object> list1 = new ArrayList<>();
                try {

                    list1.add(nameString);
                    if (stockTableRepository.getAllExtended1(nameString).isEmpty()) {
                        isAnyOptions = false;
                    } else {
                        isAnyOptions = true;
                    }
                    list1.add(isAnyOptions);
                } catch (Exception e) {
                    continue;
                }
                list.add(list1);
            }
            if (pom2 == true && hard == false) {

                List<Object> list3 = new ArrayList<>();
                try {

                    list3.add(nameString);
                    if (stockTableRepository.getAllExtended1(nameString).isEmpty()) {
                        isAnyOptions = false;
                    } else {
                        isAnyOptions = true;
                    }
                    list3.add(isAnyOptions);
                } catch (Exception e) {
                    continue;
                }
                list2.add(list3);

            }
        }

        if (hard == true)
            return list;
        else
            return list2;
    }

    public List<Object> getOptionsList(String s) {

        List<Object> list = new ArrayList<>();
        for (StockTable i : stockTableRepository.getAllExtended1(s)) {
            list.add(i.getOptions());
        }
        return list;
    }

    public boolean changeEan(JSONObject jsonObject) {
        try {
            long ean = jsonObject.getLong("ean"), stock_id = jsonObject.getLong("stock_id");
            boolean isEanExist = true;
            StockTable stockTable;
            try {
                stockTable = stockTableRepository.findByEan(ean).orElseThrow();

            } catch (NoSuchElementException e) {
                System.out.println("jol");
                isEanExist = false;
            }
            stockTable = stockTableRepository.findById(stock_id).orElseThrow();
            stockTable.setEan(ean);

            if (isEanExist == true) {
                return false;
            }
            String url;
            url = "https://example/webapi/rest/product-stocks/" + stock_id;
            String tokenS = "Bearer " + AdoreToken.access_token;
            URL obj = null;
            try {
                obj = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpsURLConnection con = null;
            try {
                con = (HttpsURLConnection) obj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                con.setRequestMethod("PUT");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            con.setDoOutput(true);
            con.setRequestProperty("Authorization", tokenS);

            OutputStreamWriter osw = null;
            try {
                osw = new OutputStreamWriter(con.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                osw.write(String.format("{\"ean\":\"%s\"}", ean));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                osw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nSending 'PUT' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine = null;
            StringBuffer response = new StringBuffer();
            while (true) {
                try {
                    if (!((inputLine = in.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.append(inputLine);
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(response.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Long> increaseStock(JSONArray jsonArray) throws Exception {
        long stock_id, stock;
        int responseCode = 0;
        String name;
        List<Long> conflict = new ArrayList();
        int h = 0;
        boolean flag = false;
        List<Long> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            stock_id = jsonArray.getJSONObject(i).getLong("stock_id");
            list.add(stock_id);
        }

        int i = 0;
        while (list.size() > 0) {
            i = 0;
            h++;
            stock = stockTableRepository.findById(list.get(i)).get().getStock();

            String url = "https://example/webapi/rest/product-stocks/" + list.get(i);
            String tokenS = "Bearer " + AdoreToken.access_token;
            URL obj = null;
            try {
                obj = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpsURLConnection con = null;
            try {
                con = (HttpsURLConnection) obj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                con.setRequestMethod("PUT");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            con.setDoOutput(true);
            con.setRequestProperty("Authorization", tokenS);

            OutputStreamWriter osw = null;
            try {
                osw = new OutputStreamWriter(con.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                osw.write(String.format("{\"stock\":%d,\"active\":%d}", stock + Collections.frequency(list, list.get(i)), 1));
            } catch (IOException e) {
                e.printStackTrace();
            }

            var element = list.get(i);
            while (list.contains(element)) {
                list.remove(element);
            }

            try {
                osw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            responseCode = 0;
            try {
                responseCode = con.getResponseCode();
                if (responseCode == 409) {
                    System.out.println("wypisuje co wkladam:" + element);
                    conflict.add(element);
                    flag = true;
                }

                if (responseCode == 429) {

                    conflict.add(element);
                    flag = true;

                }

            } catch (IOException e) {

            }

            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
            } catch (IOException e) {
            }
            String inputLine = null;
            StringBuffer response = new StringBuffer();
            while (true) {

                try {
                    if (!((inputLine = in.readLine()) != null)) break;
                } catch (Exception e) {
                    break;
                }
                response.append(inputLine);
            }
            try {
                in.close();
            } catch (Exception e) {

            }

            if (h == 9) {
                h = 0;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (flag == true)
            return conflict;
        else
            return new ArrayList<>();


    }
}


