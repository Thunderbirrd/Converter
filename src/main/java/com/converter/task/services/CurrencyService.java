package com.converter.task.services;

import com.converter.task.models.Currency;
import com.converter.task.repos.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Currency updateCurrencyRate(String name, double rate){
        Currency currency = currencyRepo.findCurrencyByName(name);
        currency.setRate(rate);
        return currencyRepo.save(currency);
    }
}
