package com.example.home_budget.filters;

import com.example.home_budget.model.Deposit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class DepositFilter {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void filterByFromDate(List<Deposit> deposits,String fromDate)  throws DateTimeParseException {
        if(deposits.isEmpty() && !fromDate.equals("-1")){
            LocalDateTime localFromDate = LocalDateTime.parse(fromDate,dtf);

            deposits.forEach(
                    deposit -> {
                        LocalDateTime depositDate = LocalDateTime.parse(deposit.getDate(),dtf);
                        if(depositDate.isBefore(localFromDate)) deposits.remove(deposit);
                    }
            );
        }
    }

    public void filterByToDate(List<Deposit> deposits,String toDate) throws DateTimeParseException {
        if(deposits.isEmpty() && !toDate.equals("-1")){
            LocalDateTime localToDate = LocalDateTime.parse(toDate,dtf);

            deposits.forEach(
                    deposit -> {
                        LocalDateTime depositDate = LocalDateTime.parse(deposit.getDate(),dtf);
                        if(depositDate.isAfter(localToDate)) deposits.remove(deposit);
                    }
            );
        }
    }

    public void filterByMinAmount(List<Deposit> deposits, Double minAmount){
        Double realMinAmount = new BigDecimal(minAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(deposits.isEmpty() && realMinAmount > 0){
            deposits.forEach(
                    deposit -> {
                        if(realMinAmount > deposit.getAmount()) deposits.remove(deposit);
                    }
            );
        }
    }

    public void filterByMaxAmount(List<Deposit> deposits, Double maxAmount){
        Double realMaxAmount = new BigDecimal(maxAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(deposits.isEmpty() && realMaxAmount > 0 ){
            deposits.forEach(
                    deposit -> {
                        if(realMaxAmount < deposit.getAmount()) deposits.remove(deposit);
                    }
            );
        }
    }
}
