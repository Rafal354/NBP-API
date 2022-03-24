package com.zooplus.nbp_api;

import com.google.gson.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

public class GetExchangeRateHandler {
    private static final String URL_STRING = "https://api.nbp.pl/api/exchangerates/rates/a/{currency}/last/5/?format=json";
    private static final Gson gson = new Gson();

    public static Map<String, Double> handleRequest(String currencyCode) {
        Map<String, Double> rates = new LinkedHashMap<>();
        WebClient client = WebClient.create();

        String response = client.get().uri(URL_STRING.replace("{currency}", currencyCode)).retrieve()
                .bodyToMono(String.class)
                .block();

        assert response != null;
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("rates");

        if(jsonArray != null) {
            int len = jsonArray.size();
            for (int i=0; i<len; i++) {
                JsonObject tmp = jsonArray.get(i).getAsJsonObject();
                String date = tmp.get("effectiveDate").getAsString();
                Double mid = tmp.get("mid").getAsDouble();
                rates.put(date, mid);
            }
        }
        return rates;
    }
}
