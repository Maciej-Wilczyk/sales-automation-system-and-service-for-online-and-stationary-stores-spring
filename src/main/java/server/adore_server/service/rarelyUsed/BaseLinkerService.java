package server.adore_server.service.rarelyUsed;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.model.SaleRecord;
import server.adore_server.model.auth.Status;
import server.adore_server.model.StockTable;
import server.adore_server.repository.SaleRecordRepository;
import server.adore_server.repository.StockTableRepository;
import server.adore_server.service.tokens.BaseLinkerTokenService;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaseLinkerService {

    @Autowired
    StockTableRepository stockTableRepository;

    @Autowired
    SaleRecordRepository saleRecordRepository;

    @Autowired
    BaseLinkerTokenService baseLinkerTokenService;


    public JSONObject getStatusListConnector(String method, String parameters) {
        String url = "https://api.baselinker.com/connector.php";

        String urlParameters = "token=" + baseLinkerTokenService.getData() + "&method=" + method + parameters;

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
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
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setDoOutput(true);

        try (
                DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        return new JSONObject(response.toString());
    }

    public List<Status> getStatusList(JSONObject jsonObject) {
        List<Status> list = new ArrayList<>();

        JSONArray jsonObjectNew = null;
        try {
            jsonObjectNew = jsonObject.getJSONArray("statuses");
        } catch (Exception e) {
        }
        long id;
        String name;
        for (int i = 0; i < jsonObjectNew.length(); i++) {
            id = jsonObjectNew.getJSONObject(i).getLong("id");
            name = jsonObjectNew.getJSONObject(i).getString("name");
            list.add(new Status(id, name, null));
        }
        return list;
    }

    public List<Status> getOrders(JSONObject jsonObject) {
        List<Status> list = new ArrayList<>();

        JSONArray jsonObjectNew = null;
        try {
            jsonObjectNew = jsonObject.getJSONArray("orders");
        } catch (Exception e) {
        }

        long order_id;
        String allegroOrShop;
        String delivery_fullname;
        for (int i = 0; i < jsonObjectNew.length(); i++) {
            order_id = jsonObjectNew.getJSONObject(i).getLong("order_id");
            delivery_fullname = jsonObjectNew.getJSONObject(i).getString("delivery_fullname");
            if (jsonObjectNew.getJSONObject(i).getString("user_login").isEmpty()) {
                try {
                    allegroOrShop = Integer.toString(jsonObjectNew.getJSONObject(i).getInt("shop_order_id"));
                } catch (Exception e) {
                    allegroOrShop = "inne";
                }
            } else
                allegroOrShop = jsonObjectNew.getJSONObject(i).getString("user_login");

            list.add(new Status(order_id, delivery_fullname, allegroOrShop));
        }
        return list;
    }

    public List<SaleRecord> getOrderToSaleRecord(JSONObject jsonObject, long order_id) {
        List<SaleRecord> list = new ArrayList<>();
        JSONArray jsonObjectOrders = null;
        JSONArray jsonObjectProducts = null;

        SaleRecord saleRecord;
        try {
            jsonObjectOrders = jsonObject.getJSONArray("orders");
            jsonObjectProducts = jsonObjectOrders.getJSONObject(0).getJSONArray("products");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("nie dziala");
        }

        double price = 0, bank = 0;
        long ean;

        int phoneNr;
        String where_, delivery_fullname;

        double delivery_price, price_brutto;
        int product_id;
        long variant_id; // stock_id

        StockTable stockTable = null;

        int quantity = 1;
        for (int i = 0; i < jsonObjectProducts.length(); i++) {
            quantity = jsonObjectProducts.getJSONObject(i).getInt("quantity");
            for (int q = 0; q < quantity; q++) {
                try {
                    phoneNr = Integer.parseInt(jsonObjectOrders.getJSONObject(0).getString("phone").substring(3));
                } catch (Exception e) {
                    phoneNr = -1;
                }
                where_ = "baselinker " + jsonObjectOrders.getJSONObject(0).getString("order_source");
                try {
                    price = jsonObjectProducts.getJSONObject(i).getDouble("price_brutto");
                } catch (Exception e) {
                    price = -1;
                }
                if (jsonObjectProducts.getJSONObject(i).getString("ean").equals(""))
                    ean = -1;
                else
                    ean = jsonObjectProducts.getJSONObject(i).getLong("ean");

                try {

                    delivery_price = jsonObjectOrders.getJSONObject(0).getDouble("delivery_price");
                } catch (Exception e) {
                    delivery_price = -1;
                }
                try {
                    price_brutto = jsonObjectProducts.getJSONObject(i).getDouble("price_brutto");
                } catch (Exception e) {
                    price_brutto = -1;
                }
                try {
                    variant_id = jsonObjectProducts.getJSONObject(i).getLong("variant_id");
                } catch (Exception e) {
                    variant_id = -1;
                }
                try {
                    product_id = jsonObjectProducts.getJSONObject(i).getInt("product_id");
                } catch (Exception e) {
                    product_id = -1;
                }

                try {
                    delivery_fullname = jsonObjectOrders.getJSONObject(0).getString("delivery_fullname");
                } catch (Exception e) {
                    delivery_fullname = "brak";
                }
                System.out.println("to jest stock_id:" + variant_id);
                saleRecord = new SaleRecord();
                saleRecord.setEan(ean);
                saleRecord.setWhere_(where_);
                saleRecord.setPhoneNr(phoneNr);
                saleRecord.setPrice(price);
                saleRecord.setProductId(product_id);
                saleRecord.setStock_id(variant_id);
                saleRecord.setBaselinker_id(order_id);
                saleRecord.setDelivery_price(delivery_price);
                saleRecord.setPrice_brutto(price_brutto);
                saleRecord.setBaselinker_fullname(delivery_fullname);

                if(variant_id == 0)
                    variant_id = -1;

                if (variant_id != -1 )
                    stockTable = stockTableRepository.findById(variant_id).orElseThrow();

                if (variant_id == -1) {
                    saleRecord.setName("brak stock_id z baselnikera");
                    saleRecord.setOptions("brak stock_id z baselnikera");
                } else {
                    saleRecord.setName(stockTable.getName());
                    saleRecord.setOptions(stockTable.getOptions());
                    saleRecord.setProducer(stockTable.getProducer());
                }
                list.add(saleRecord);
                bank += price_brutto;
            }
        }
        for (SaleRecord l : list) {
            l.setBank(bank);
        }
        return list;
    }

    @Transactional
    public List<SaleRecord> addSaleRecordBaseLinker(List<SaleRecord> saleRecord1) throws Exception {
        if (saleRecord1.get(0).getDelivery_price() != 0) {
            SaleRecord saleRecord = new SaleRecord();
            saleRecord.setDate(saleRecord1.get(0).getDate());
            saleRecord.setName("Dostawa");
            saleRecord.setPrice(saleRecord1.get(0).getDelivery_price());
            saleRecord.setDelivery_price(saleRecord1.get(0).getDelivery_price());
            saleRecord.setTransaction_id(saleRecord1.get(0).getTransaction_id());
            saleRecord.setEmployee(saleRecord1.get(0).getEmployee());
            saleRecord.setWhere_(saleRecord1.get(0).getWhere_());
            saleRecordRepository.save(saleRecord);
        }
        return saleRecordRepository.saveAll(saleRecord1);
    }

}



