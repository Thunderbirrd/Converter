package com.converter.task.services;

import com.converter.task.models.Currency;
import com.converter.task.repos.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepo currencyRepo;

    @Transactional(readOnly = true)
    public List<Currency> getAllCurrencies(){
        return currencyRepo.getAllCurrencies();
    }

    @Transactional
    public Currency updateCurrencyRate(String name, double rate, Date date){
        Currency currency = currencyRepo.findCurrencyByName(name);
        if(currency != null){
            currency.setRate(rate);
            currency.setUpdate_date(date);
            return currencyRepo.save(currency);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Currency findCurrencyByName(String name){
        return currencyRepo.findCurrencyByName(name);
    }
    @Transactional
    public void saveCurrency(Currency currency){
        currencyRepo.save(currency);
    }
}
