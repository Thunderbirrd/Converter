package com.converter.task.services;

import com.converter.task.models.Exchange;
import com.converter.task.repos.ExchangeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    ExchangeRepo exchangeRepo;

    @Transactional
    public Exchange saveExchange(Exchange exchange){
        return exchangeRepo.save(exchange);
    }

    @Transactional(readOnly = true)
    public List<Exchange> getAllExchangesByDate(Date date1, Integer userId){

        return exchangeRepo.getAllByDate(date1, userId);
    }

    @Transactional(readOnly = true)
    public List<Exchange> getAllByDateAndCurrencies(Date date, String currency1, String currency2, Integer userId){
        List<Exchange> listDate = exchangeRepo.getAllByDate(date, userId);
        List<Exchange> listCurrencies = exchangeRepo.getAllByCurrency1AndCurrency2(currency1, currency2, userId);
        listDate.retainAll(listCurrencies);
        return listDate;
    }
}
