package com.converter.task.controllers;

import com.converter.task.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.converter.task.models.Currency;



@RestController
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @RequestMapping(value = "/getAllCurrencies", method = RequestMethod.GET)
    public ArrayList<Currency> getAllCurrencies(){
        return (ArrayList<Currency>)currencyService.getAllCurrencies();
    }
    @RequestMapping(value = "/updateRate", method = RequestMethod.GET)
    public String updateCurrencyRate() throws IOException {
        return "";
    }

    private String parseAllCurrencies() throws IOException {
        URL obj = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }
}
