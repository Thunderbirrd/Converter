package com.converter.task.controllers;

import com.converter.task.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.converter.task.models.Currency;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


@RestController
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @RequestMapping(value = "/getAllCurrencies", method = RequestMethod.GET)
    public ArrayList<Currency> getAllCurrencies(){
        return (ArrayList<Currency>)currencyService.getAllCurrencies();
    }

    
     void parseCurrentRates(CurrencyService currencyService) throws IOException, ParserConfigurationException, SAXException, ParseException {
        URL obj = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "windows-1251"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(response.toString()));
        inputSource.setEncoding("UTF-8");
        Document document = documentBuilder.parse(inputSource);
        Node root = document.getDocumentElement();
        NodeList currencies = root.getChildNodes();
        for(int i = 0; i < currencies.getLength(); i++){
            Node currency = currencies.item(i);
            NodeList currencyData = currency. getChildNodes();
            Currency currencyForDB = new Currency();
            double coefficient = 1.0;
            for(int j = 0; j < 5; j++){
                Node property = currencyData.item(j);
                switch (property.getNodeName()) {
                    case "CharCode":
                        currencyForDB.setName(property.getChildNodes().item(0).getTextContent() + "(");
                        break;
                    case "Name":
                        currencyForDB.setName(currencyForDB.getName() + property.getChildNodes().item(0)
                                .getTextContent() + ")");
                        break;
                    case "Nominal":
                        coefficient = Double.parseDouble(property.getChildNodes()
                                .item(0).getTextContent());
                        break;
                    case "Value":
                        currencyForDB.setRate(Double.parseDouble(property.getChildNodes().item(0).getTextContent()
                                .replaceAll(",", ".")) / coefficient);
                        break;

                }
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            currencyForDB.setUpdate_date(formatter.parse(formatter.format(date)));
            currencyService.saveCurrency(currencyForDB);
        }

    }
}
