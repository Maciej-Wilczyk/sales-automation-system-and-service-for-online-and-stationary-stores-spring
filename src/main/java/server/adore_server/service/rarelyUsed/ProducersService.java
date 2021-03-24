package server.adore_server.service.rarelyUsed;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.adore_auth.AdoreToken;
import server.adore_server.model.clcker.Producers;
import server.adore_server.repository.rarelyUsed.ProducersRepository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class ProducersService {

    @Autowired
    ProducersRepository producersRepository;

    public void updateProducers() throws Exception {
        try {
            int pages = 1, page = 1;
            JSONObject jsonObj;
            JSONArray list;
            int producer_id;
            String name;
            do {
                try {
                    String url = "https://example/webapi/rest/producers?limit=50&&page=" + page;
                    String tokenS = "Bearer " + AdoreToken.access_token;
                    URL obj = new URL(url);
                    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                    con.setRequestMethod("GET");
                    con.setRequestProperty("Authorization", tokenS);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    jsonObj = new JSONObject(response.toString());

                    pages = jsonObj.getInt("pages");
                    page++;
                    list = jsonObj.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        producer_id = list.getJSONObject(i).getInt("producer_id");
                        name = list.getJSONObject(i).getString("name");
                        producersRepository.save(new Producers(producer_id, name));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    page--;
                }
            }
            while (page <= pages);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
