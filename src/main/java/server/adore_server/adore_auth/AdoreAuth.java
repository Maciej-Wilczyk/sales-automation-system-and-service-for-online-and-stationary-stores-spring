package server.adore_server.adore_auth;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.adore_server.service.tokens.TokenRefreshDataService;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
@Service
public class AdoreAuth {

    @Autowired
    TokenRefreshDataService tokenRefreshDataService;
    public JSONObject getToken() throws IOException {

        String url = "https://example/webapi/rest/auth";

        String loginAndPassword =  "Basic " + tokenRefreshDataService.getData();

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", loginAndPassword);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObj = new JSONObject(response.toString());

        return jsonObj;

    }

    public boolean saveAccessToken() {
        try {
            JSONObject js = getToken();
            AdoreToken.access_token = js.get("access_token").toString();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}


