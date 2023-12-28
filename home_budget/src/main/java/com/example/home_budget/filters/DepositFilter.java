package com.example.home_budget.filters;

import com.example.home_budget.model.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositFilter {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public void filterByFromDate(List<Deposit> deposits,String fromDate)  throws DateTimeParseException {
        if(!deposits.isEmpty() && !fromDate.equals("-1")){
            LocalDateTime localFromDate = LocalDateTime.parse(fromDate,dtf);
            List<Deposit> toRemove = new ArrayList<>();
            deposits.forEach(
                    deposit -> {
                        LocalDateTime depositDate = LocalDateTime.parse(deposit.getDate(),dtf);
                        if(depositDate.isBefore(localFromDate)) toRemove.add(deposit);
                    }
            );
            deposits.removeAll(toRemove);
        }
    }

    public void filterByToDate(List<Deposit> deposits,String toDate) throws DateTimeParseException {
        if(!deposits.isEmpty() && !toDate.equals("-1")){
            LocalDateTime localToDate = LocalDateTime.parse(toDate,dtf);
            List<Deposit> toRemove = new ArrayList<>();
            deposits.forEach(
                    deposit -> {
                        LocalDateTime depositDate = LocalDateTime.parse(deposit.getDate(),dtf);
                        if(depositDate.isAfter(localToDate))toRemove.add(deposit);
                    }
            );
            deposits.removeAll(toRemove);
        }
    }
    public void filterByMinAmount(List<Deposit> deposits, Double minAmount){
        Double realMinAmount = new BigDecimal(minAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(!deposits.isEmpty() && realMinAmount > 0){
            List<Deposit> toRemove = new ArrayList<>();
            deposits.forEach(
                    deposit -> {
                        if(realMinAmount > deposit.getAmount()) toRemove.add(deposit);
                    }
            );
            deposits.removeAll(toRemove);
        }
    }

    public void filterByMaxAmount(List<Deposit> deposits, Double maxAmount){
        Double realMaxAmount = new BigDecimal(maxAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(!deposits.isEmpty() && realMaxAmount > 0 ){
            List<Deposit> toRemove = new ArrayList<>();
            deposits.forEach(
                    deposit -> {
                        if(realMaxAmount < deposit.getAmount()) toRemove.add(deposit);
                    }
            );
            deposits.removeAll(toRemove);
        }
    }
}
