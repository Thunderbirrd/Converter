package com.converter.task.repos;

import com.converter.task.models.Exchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ExchangeRepo extends CrudRepository<Exchange, Integer> {
    @Query("SELECT exchange FROM Exchange exchange WHERE exchange.date>=:date AND exchange.user_id=:userId ORDER BY id")
    List<Exchange> getAllByDate(@Param("date") Date date, @Param("userId") Integer userId);

    @Query("SELECT exchange FROM Exchange exchange WHERE exchange.currency1=:currency1 AND" +
            " exchange.currency2=:currency2 AND exchange.user_id=:userId ORDER BY id")
    List<Exchange> getAllByCurrency1AndCurrency2(@Param("currency1")String currency1,
                                                 @Param("currency2") String currency2, @Param("userId") Integer userId);
}
