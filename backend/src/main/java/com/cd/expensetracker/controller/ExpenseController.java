package com.cd.expensetracker.controller;

import com.cd.expensetracker.model.Expense;
import com.cd.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @GetMapping
    public List<Expense> getAllExpenses(@RequestParam Long userId) {

        return expenseService.getAllExpenses(userId);
    }
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
    @GetMapping("/filter")
    public List<Expense> filterExpenses(
            @RequestParam Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return expenseService.filterExpensesByDate(userId, startDate, endDate);
    }
    @GetMapping("/summary")
    public List<Object[]> getExpensesByCategory(@RequestParam Long userId) {
        return expenseService.getExpensesByCategory(userId);
    }
}
