package com.zooplus.nbp_api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetAverageGoldPriceHandler {
    private static final String URL_STRING = "https://api.nbp.pl/api/cenyzlota/last/14/?format=json";
    private static final Gson gson = new Gson();

    public static Map<String, Double> handleRequest() {
        WebClient client = WebClient.create();
        Map<String, Double> prices = new LinkedHashMap<>();

        String response = client.get().uri(URL_STRING).retrieve()
                .bodyToMono(String.class)
                .block();

        assert response != null;
        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

        if(jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0; i<len; i++) {
                JsonObject tmp = jsonArray.get(i).getAsJsonObject();
                String date = tmp.get("data").getAsString();
                Double mid = tmp.get("cena").getAsDouble();
                prices.put(date, mid);
            }
        }
        return prices;
    }

}
