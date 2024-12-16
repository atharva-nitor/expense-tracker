package com.cd.expensetracker.service;

import com.cd.expensetracker.model.Expense;
import com.cd.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    public List<Expense> getAllExpenses(Long userId) {
        System.out.println("Inside getAllExpenses");
        return expenseRepository.findByUserId(userId);
    }
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    public Expense updateExpense(Long expenseId, Expense updatedExpense) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow();
        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDate(updatedExpense.getDate());
        return expenseRepository.save(expense);
    }
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
    public List<Expense> filterExpensesByDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findExpensesByDateRange(userId, startDate, endDate);
    }
    public List<Object[]> getExpensesByCategory(Long userId) {
        return expenseRepository.getExpensesByCategory(userId);
    }
}
