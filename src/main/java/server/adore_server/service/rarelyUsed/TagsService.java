package server.adore_server.service.rarelyUsed;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.adore_auth.AdoreToken;
import server.adore_server.model.clcker.Tags;
import server.adore_server.repository.rarelyUsed.TagsRepository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class TagsService {

    @Autowired
    TagsRepository tagsRepository;

    public void updateTags() throws Exception {
        try {
            int pages, page = 1;
            JSONObject jsonObj;
            JSONArray list;
            int tag_id;
            int lang_id;
            String name;
            do {
                String url = "https://example/webapi/rest/product-tags?limit=50&&page="+ page;
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
                    tag_id = list.getJSONObject(i).getInt("tag_id");
                    name = list.getJSONObject(i).getString("name");
                    lang_id = list.getJSONObject(i).getInt("lang_id");
                    tagsRepository.save(new Tags(tag_id,name,lang_id));
                }


            }
            while (page <= pages);
        }
        catch (Exception e){

            e.printStackTrace();
        }
    }
}
