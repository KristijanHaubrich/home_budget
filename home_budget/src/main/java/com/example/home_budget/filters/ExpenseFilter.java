package com.example.home_budget.filters;

import com.example.home_budget.model.Expense;
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
public class ExpenseFilter {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public void filterByCategory(List<Expense> expenses,String category){
        if(!expenses.isEmpty() && expenses != null  && !category.equals("-1")){
            List<Expense> toRemove = new ArrayList<>();
            expenses.forEach(
                    expense -> {
                        if(!expense.getCategory().getName().equals(category)) toRemove.add(expense);
                    }
            );
            expenses.removeAll(toRemove);
        }
    }

    public void filterByFromDate(List<Expense> expenses, String fromDate)  throws DateTimeParseException {
        if(!expenses.isEmpty() && expenses != null && !fromDate.equals("-1")){
            LocalDateTime localFromDate = LocalDateTime.parse(fromDate,dtf);
            List<Expense> toRemove = new ArrayList<>();
            expenses.forEach(
                    expense -> {
                        LocalDateTime expenseDate = LocalDateTime.parse(expense.getDate(),dtf);
                        if(expenseDate.isBefore(localFromDate)) toRemove.add(expense);
                    }
            );
            expenses.removeAll(toRemove);
        }
    }

    public void filterByToDate(List<Expense> expenses,String toDate) throws DateTimeParseException {
        if(!expenses.isEmpty() && expenses != null  && !toDate.equals("-1")){
            LocalDateTime localToDate = LocalDateTime.parse(toDate,dtf);
            List<Expense> toRemove = new ArrayList<>();
            expenses.forEach(
                    expense -> {
                        LocalDateTime expenseDate = LocalDateTime.parse(expense.getDate(),dtf);
                        if(expenseDate.isAfter(localToDate)) toRemove.add(expense);
                    }
            );
            expenses.removeAll(toRemove);
        }
    }

    public void filterByMinAmount(List<Expense> expenses, Double minAmount){
        Double realMinAmount = new BigDecimal(minAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(!expenses.isEmpty() && expenses != null  && realMinAmount > 0){
                List<Expense> toRemove = new ArrayList<>();
                expenses.forEach(
                        expense -> {
                            if(realMinAmount > expense.getAmount()) toRemove.add(expense);
                        }
                );
                expenses.removeAll(toRemove);
        }
    }

    public void filterByMaxAmount(List<Expense> expenses, Double maxAmount){
        Double realMaxAmount = new BigDecimal(maxAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(!expenses.isEmpty() && expenses != null && realMaxAmount > 0 ){
            List<Expense> toRemove = new ArrayList<>();
            expenses.forEach(
                    expense -> {
                        if(realMaxAmount < expense.getAmount()) toRemove.add(expense);
                    }
            );
            expenses.removeAll(toRemove);
        }
    }

}
