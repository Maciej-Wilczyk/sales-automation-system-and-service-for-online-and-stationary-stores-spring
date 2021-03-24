package server.adore_server.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.adore_server.adore_auth.AdoreToken;
import server.adore_server.model.SaleRecord;
import server.adore_server.model.StockTable;
import server.adore_server.model.client.ClientCard;
import server.adore_server.model.client.ClientShopping;
import server.adore_server.model.client.MinimumAmount;
import server.adore_server.repository.SaleRecordRepository;
import server.adore_server.repository.StockTableRepository;
import server.adore_server.repository.client.ClientCardRepository;
import server.adore_server.repository.client.ClientShoppingRepository;
import server.adore_server.repository.client.MinimumAmountRepository;
import server.adore_server.response.ReturnedResponse2;
import server.adore_server.response.ReturnResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaleRecordService {

    @Autowired
    SaleRecordRepository saleRecordRepository;

    @Autowired
    StockTableRepository stockTableRepository;

    @Autowired
    SaleRecordDeletedService saleRecordDeletedService;

    @Autowired
    MinimumAmountRepository minimumAmountRepository;

    @Autowired
    ClientCardRepository clientCardRepository;

    @Autowired
    ClientShoppingRepository clientShoppingRepository;

    public List<SaleRecord> getSaleRecords() {
        return saleRecordRepository.findAll();
    }

    public List<SaleRecord> addSaleRecord(List<SaleRecord> saleRecord1, long card_id) {

        StockTable stockTable;
        long stock_id;
        int active, extended, productId, stock;
        int pom = 1;
        double together;
        int change_ = saleRecord1.get(0).getChange_();
        boolean check = true, helper = true;
        String producer;
        ClientCard card = null;
        ClientShopping clientShopping = null;


        if(card_id != -1 ){
            card = clientCardRepository.findById(card_id).orElseThrow();

            together = saleRecord1.get(0).getBank() + saleRecord1.get(0).getCard() + saleRecord1.get(0).getCash();
            int conuntPoints = (int)(together/minimumAmountRepository.getA());
            clientShopping = new ClientShopping();

            clientShopping.setDate(saleRecord1.get(0).getDate());
            clientShopping.setTransaction_id(saleRecord1.get(0).getTransaction_id());
            clientShopping.setPoints(conuntPoints);
            card.addClientShopping(clientShopping);

        }

        for (SaleRecord i : saleRecord1) {
            if (i.getEan() != 0) {
                if (i.getEan() != -1)
                    stockTable = stockTableRepository.findByEan(i.getEan()).orElseThrow();
                else
                    stockTable = stockTableRepository.findByNameAndOptions(i.getName(), i.getOptions()).orElseThrow();

                stock_id = stockTable.getStock_id();
                active = stockTable.getActive();
                stock = stockTable.getStock();
                productId = stockTable.getProductId();
                extended = stockTable.getExtended();
                producer = stockTable.getProducer();
                i.setProducer(producer);

                if (stock == 1) {
                    active = 0;
                }
                if (stock > 0)
                    stock -= 1;

                if (change_ == 1)
                    helper = decStockAdoreApi(stock, stock_id, active, productId, extended);

                if (helper == false)
                    i.setChange_(-1);//if conflict


                if (active == 0)
                    check = checkExtendLive(stockTable.getName(), stockTable.getOptions());

                if (check == false && change_ == 1)
                    setProductActive0(stockTable.getProductId());

                try {
                    card.addCSaleRecord(i);
                }catch (Exception e){
                    card = null;
                }
            }


        }


        return saleRecordRepository.saveAll(saleRecord1);
    }

    public boolean checkExtendLive(String s, String options) {
        if (stockTableRepository.getAllExtended1(s).size() == 0)
            return true;
        for (StockTable i : stockTableRepository.getAllExtended1(s)) {
            if (options.equals(i.getOptions()))
                continue;
            if (i.getActive() == 1)
                return true;
        }
        return false;
    }


    public boolean decStockAdoreApi(int stock, long stock_id, int active, int productId, int extended) {
        boolean flag_429;
        do {
            flag_429 = false;
            String url;
            if (extended == 1) {
                url = "https://example/webapi/rest/product-stocks/" + stock_id;
            } else {
                url = "https://example/webapi/rest/products/" + productId;
            }
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
            if (extended == 1) {
                try {
                    if (active == 1)
                        osw.write(String.format("{\"stock\":%d}", stock));
                    else if (active == 0) {

                        osw.write(String.format("{\"stock\":%d, \"active\":%d}", stock, active));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    if (active == 1) {
                        osw.write(String.format("{\"stock\":{\"stock\":%d}}", stock));
                    } else {
                        osw.write(String.format(
                                "{" +
                                        "\"stock\":{" +
                                        "\"stock\":%d" +
                                        "}," +
                                        "\"translations\":{" +
                                        "\"pl_PL\":{" +
                                        "\"active\":%d" +
                                        "}" +
                                        "}" +
                                        "}", stock, active));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                if (responseCode == 409) {
                    // flag_409 = true;
                    return false;
                }
                if (responseCode == 429) {
                    flag_429 = true;
                }
            } catch (Exception e) {
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
        } while (flag_429 == true);
        return true;
    }

    public void setProductActive0(long product_id) {
        String url = "https://adore.pl/webapi/rest/products/" + product_id;
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
            osw.write(String.format("{\"translations\":{\"pl_PL\":{\"active\":%d}}}", 0));

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
        System.out.println(product_id);
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
        //System.out.println(response.toString());
    }

    public long getMaxTrId() {
        return saleRecordRepository.getMaxTr();
    }

    public List<SaleRecord> getLastSaleRecord() {
        return saleRecordRepository.getLastSaleRecord("stacjonarna");
    }

    @Transactional
    @Modifying
    public int deleteByTransaction(String user) {
        int a = saleRecordRepository.getMaxTrStacjonarna("stacjonarna");

        saleRecordDeletedService.addSD(saleRecordRepository.getLastSaleRecord("stacjonarna"), user);
        return saleRecordRepository.deleteTr(a);
    }

    public void editComment(String s) {
        int a = saleRecordRepository.getMaxTrStacjonarna("stacjonarna");
        List<SaleRecord> list = saleRecordRepository.getLastSaleRecord("stacjonarna");
        for (SaleRecord i : list) {
            i.setComment(s);
        }
        saleRecordRepository.saveAll(list);
    }


    public List<SaleRecord> baselinkerIdExist(long baselinker_id) {
        return saleRecordRepository.existsByBaselinker_id(baselinker_id);
    }

    public List<ReturnResponse> getAllByEan(long ean) {
        List<SaleRecord> listR = saleRecordRepository.getAllByEan(ean);
        List<ReturnResponse> listN = new ArrayList();
        for (SaleRecord i : listR) {
            if (i.getBaselinker_fullname().equals(""))
                listN.add(new ReturnResponse(i.getTransaction_id(), i.getDate(), Integer.toString(i.getPhoneNr()), i.getName(), i.getOptions()));
            else
                listN.add(new ReturnResponse(i.getTransaction_id(), i.getDate(), i.getBaselinker_fullname(), i.getName(), i.getOptions()));
        }
        return listN;
    }

    public List<ReturnedResponse2> getAllByNameAndOptions(String name, String options) {
        List<SaleRecord> listR = saleRecordRepository.findAllByNameAndOptions(name, options).orElseThrow();
        List<ReturnedResponse2> listN = new ArrayList();
        for (SaleRecord i : listR) {
            if (i.getBaselinker_fullname().equals(""))
                listN.add(new ReturnedResponse2(i.getEan(), i.getTransaction_id(), i.getDate(), Integer.toString(i.getPhoneNr())));
            else
                listN.add(new ReturnedResponse2(i.getEan(), i.getTransaction_id(), i.getDate(), i.getBaselinker_fullname()));
        }
        return listN;
    }

    public List<ReturnedResponse2> getAllByName(String name) {
        List<SaleRecord> listR = saleRecordRepository.findAllByName(name).orElseThrow();
        List<ReturnedResponse2> listN = new ArrayList();
        for (SaleRecord i : listR) {
            if (i.getBaselinker_fullname().equals(""))
                listN.add(new ReturnedResponse2(i.getEan(), i.getTransaction_id(), i.getDate(), Integer.toString(i.getPhoneNr())));
            else
                listN.add(new ReturnedResponse2(i.getEan(), i.getTransaction_id(), i.getDate(), i.getBaselinker_fullname()));
        }
        return listN;
    }


    public List<SaleRecord> getAllByTransaction_id(int transaction_id) {
        return saleRecordRepository.getAllByTransaction_id(transaction_id);
    }

    public boolean changeSaleRecord(JSONObject jsonObject) {
        try {
            SaleRecord saleRecord;
            long stock_id;
            System.out.println(jsonObject.toString());
            int toReturn, stock, change = jsonObject.getInt("change"); // 1 -> yes, 0 -> no
            String comment = jsonObject.getString("comment");
            String employee = jsonObject.getString("employee");
            String date = jsonObject.getString("date");
            for (int i = 0; i < jsonObject.getJSONArray("toReturn").length(); i++) {
                toReturn = (int) jsonObject.getJSONArray("toReturn").get(i);
                System.out.println("to returned" + toReturn);
                saleRecord = saleRecordRepository.findById((long) toReturn).orElseThrow();
                saleRecord.setReturned(1);
                saleRecord.setReturned_employee(employee);
                saleRecord.setReturned_date(date);
                saleRecord.setReturned_comment(comment);
                saleRecord.setChange_(change);
                stock_id = saleRecord.getStock_id();
                stock = stockTableRepository.findById(saleRecord.getStock_id()).get().getStock();
                saleRecordRepository.save(saleRecord);
                if (change == 1) {
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
                        osw.write(String.format("{\"stock\":%d,\"active\":%d}", stock + 1, 1));

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
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
