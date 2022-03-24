package com.zooplus.nbp_api;

import com.google.gson.Gson;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/")
    String mainPage() {
        return "init";
    }

    @GetMapping("/api/exchange-rates")
    public String forwarder(@RequestParam String currency) {
        return "redirect:/api/exchange-rates/" + currency;
    }

    @GetMapping(value = "/api/exchange-rates/{currencyCode}", produces = MediaType.TEXT_HTML_VALUE)
    String currencyToPLNPageHtml(@PathVariable String currencyCode, Model model) {
        model.addAttribute("map", GetExchangeRateHandler.handleRequest(currencyCode));
        return "currencytopln";
    }

    @GetMapping(value = "/api/gold-price/average", produces = MediaType.TEXT_HTML_VALUE)
    String averageGoldPricePageHtml(Model model) {
        model.addAttribute("map", GetAverageGoldPriceHandler.handleRequest());
        return "averagegoldprice";
    }

    @GetMapping(value = "/api/exchange-rates/{currencyCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String currencyToPLNPageJson(@PathVariable String currencyCode, Model model) {
        return new Gson().toJson(GetExchangeRateHandler.handleRequest(currencyCode));
    }

    @GetMapping(value = "/api/gold-price/average", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String averageGoldPricePageJson(Model model) {
        return new Gson().toJson(GetAverageGoldPriceHandler.handleRequest());
    }

}