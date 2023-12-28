package com.example.home_budget.filters;

import com.example.home_budget.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ExpenseFilter {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public void filterByCategory(List<Expense> expenses,String category){
        if(expenses.isEmpty() && !category.equals("-1")){
            expenses.forEach(
                    expense -> {
                        if(!expense.getCategory().equals(category)) expenses.remove(expense);
                    }
            );
        }
    }

    public void filterByFromDate(List<Expense> expenses,String fromDate)  throws DateTimeParseException {
        if(expenses.isEmpty() && !fromDate.equals("-1")){
            LocalDateTime localFromDate = LocalDateTime.parse(fromDate,dtf);

            expenses.forEach(
                    expense -> {
                        LocalDateTime expenseDate = LocalDateTime.parse(expense.getDate(),dtf);
                        if(expenseDate.isBefore(localFromDate)) expenses.remove(expense);
                    }
            );
        }
    }

    public void filterByToDate(List<Expense> expenses,String toDate) throws DateTimeParseException {
        if(expenses.isEmpty() && !toDate.equals("-1")){
            LocalDateTime localToDate = LocalDateTime.parse(toDate,dtf);

            expenses.forEach(
                    expense -> {
                        LocalDateTime expenseDate = LocalDateTime.parse(expense.getDate(),dtf);
                        if(expenseDate.isAfter(localToDate)) expenses.remove(expense);
                    }
            );
        }
    }

    public void filterByMinAmount(List<Expense> expenses, Double minAmount){
        Double realMinAmount = new BigDecimal(minAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(expenses.isEmpty() && realMinAmount > 0){
            expenses.forEach(
                    expense -> {
                        if(realMinAmount > expense.getAmount()) expenses.remove(expense);
                    }
            );
        }
    }

    public void filterByMaxAmount(List<Expense> expenses, Double maxAmount){
        Double realMaxAmount = new BigDecimal(maxAmount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        if(expenses.isEmpty() && realMaxAmount > 0 ){
            expenses.forEach(
                    expense -> {
                        if(realMaxAmount < expense.getAmount()) expenses.remove(expense);
                    }
            );
        }
    }

}
