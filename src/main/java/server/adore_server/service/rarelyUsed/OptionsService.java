package server.adore_server.service.rarelyUsed;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.adore_auth.AdoreToken;
import server.adore_server.model.clcker.Options;
import server.adore_server.repository.rarelyUsed.OptionsRepository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class OptionsService {

    @Autowired
    OptionsRepository optionsRepository;

    public void updateOptions() throws Exception {
        try {
            int pages, page = 1;
            JSONObject jsonObj;
            JSONArray list;
            int option_id;
            String name;
            do {
                String url = "https://example/webapi/rest/options?limit=50&&page="+ page;
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

                System.out.println(response.toString());

                jsonObj = new JSONObject(response.toString());

                pages = jsonObj.getInt("pages");
                page++;
                list = jsonObj.getJSONArray("list");

                for(int i = 0; i < list.length(); i++){
                    option_id = list.getJSONObject(i).getJSONObject("translations").getJSONObject("pl_PL").getInt("option_id");
                    name = list.getJSONObject(i).getJSONObject("translations").getJSONObject("pl_PL").getString("name");
                    optionsRepository.save(new Options(option_id,name));
                }


            }
            while (page <= pages);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
