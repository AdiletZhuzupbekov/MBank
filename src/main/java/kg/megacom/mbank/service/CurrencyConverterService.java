package kg.megacom.mbank.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyConverterService {


    public double kgs;
    public double eur;
    public double rub;
    public double cad;
    public double conversion_result;



    public void sendHttpGetRequest(String fromCode, String toCode, double amount) throws IOException, JSONException {
        String GET_URL = "https://v6.exchangerate-api.com/v6/47128bc040037fe82d6a4119/pair/"+fromCode+"/"+toCode+"/"+amount;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){ //success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }in.close();

            JSONObject object = new JSONObject(response.toString());
            this.conversion_result = object.getDouble("conversion_result");
        }
        else {
            System.out.println("Get request failed!");
        }
    }

    @Bean
    @Scheduled(cron = "**1***")
    private  void sendHttpGetRequest() throws IOException, JSONException {
        String GET_URL = "https://v6.exchangerate-api.com/v6/47128bc040037fe82d6a4119/latest/USD";
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){ //success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }in.close();

            JSONObject object = new JSONObject(response.toString());
            double kgs = object.getJSONObject("conversion_rates").getDouble("KGS");
            double eur = object.getJSONObject("conversion_rates").getDouble("EUR");
            double rub = object.getJSONObject("conversion_rates").getDouble("RUB");
            double cad = object.getJSONObject("conversion_rates").getDouble("CAD");

            this.kgs = kgs;
            this.eur = eur;
            this.rub = rub;
            this.cad = cad;


        }
        else {
            System.out.println("Get request failed!");
        }
    }

}
