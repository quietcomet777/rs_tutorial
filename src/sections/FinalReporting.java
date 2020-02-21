package sections;

import org.osbot.rs07.script.MethodProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

public class FinalReporting {
    private MethodProvider api;

    FinalReporting(MethodProvider api){
        this.api = api;
    }


    public void run() throws Exception{


            URL obj = new URL("http://localhost:5000/report_state_change");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

        JsonObject params = new JsonObject();

        params.addProperty("rs_username", api.bot.getUsername());
        params.addProperty("in_game_name", api.myPlayer().getName());



            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();


            byte[] input = params.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();


            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                api.log(response.toString());
            }

    }
}
