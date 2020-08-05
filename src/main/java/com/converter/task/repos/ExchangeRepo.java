package com.converter.task.repos;

import com.converter.task.models.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepo extends CrudRepository<Exchange, Integer> {

}
