package com.converter.task.controllers;

import com.converter.task.models.Currency;
import com.converter.task.models.Exchange;
import com.converter.task.services.CurrencyService;
import com.converter.task.services.ExchangeService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class ExchangeController {

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    CurrencyService currencyService;

    @RequestMapping(value = "/converter.html/exchange", method = RequestMethod.POST)
    public ArrayList<Double> exchange(@RequestBody String data) throws JSONException, ParseException, ParserConfigurationException, SAXException, IOException {
        JSONObject d = new JSONObject(data);
        String currencyFrom = d.getString("currencyFrom");
        String currencyTo = d.getString("currencyTo");
        Integer id = d.getInt("userId");
        Double value = d.getDouble("input");
        Currency currency1 = currencyService.findCurrencyByName(currencyFrom);
        Currency currency2 = currencyService.findCurrencyByName(currencyTo);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if(!currency1.getUpdate_date().toString().substring(0, 10).equals(formatter.format(date))){
            CurrencyController controller = new CurrencyController();
            controller.updateRates(currencyService);
        }
        ArrayList<Double> answer = new ArrayList<>();
        answer.add(currency1.getRate()); answer.add(currency2.getRate());
        Exchange exchange = new Exchange();
        exchange.setCurrency1(currency1.getName());
        exchange.setCurrency2(currency2.getName());
        exchange.setUser_id(id);
        exchange.setValue1(value); exchange.setValue2(Math.round(value * currency1.getRate() /
                currency2.getRate() * 10000.0) / 10000.0);
        exchange.setDate(formatter.parse(formatter.format(date)));
        exchangeService.saveExchange(exchange);
        return answer;
    }
}
