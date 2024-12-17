package com.cd.expensetracker;

import com.cd.expensetracker.model.Expense;
import com.cd.expensetracker.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
@SpringBootTest
class ExpensetrackerApplicationTests {

        private ExpenseService tracker;
        @BeforeEach
        public void setUp() {
            tracker = new ExpenseService();
        }
        @Test
        public void testAddExpense() {
            Expense expense = new Expense();
            expense.setTitle("Expense Title");
            expense.setAmount(100.0);
            expense.setCategory("Food");
            expense.setDate(LocalDate.ofEpochDay(01/01/2024));
            tracker.addExpense(expense);
            List<Expense> expenses = tracker.getAllExpenses(1L);
            assertEquals(1, expenses.size());
            assertEquals("Groceries", expenses.get(0).getTitle());
            assertEquals(50.0, expenses.get(0).getAmount());
        }
        @Test
        public void testEditExpense() {
            Expense expense1 = new Expense("Groceries", 50.0, "Food", "2024-06-10");
            tracker.addExpense(expense1);
            Expense updatedExpense = new Expense("Supermarket", 60.0, "Food", "2024-06-11");
            tracker.updateExpense(0L, updatedExpense);
            Expense editedExpense = tracker.getAllExpenses(1L).get(0);
            assertEquals("Supermarket", editedExpense.getTitle());
            assertEquals(60.0, editedExpense.getAmount());
            assertEquals("2024-06-11", editedExpense.getDate());
        }
        @Test
        public void testDeleteExpense() {
            Expense expense1 = new Expense();
            tracker.addExpense(expense1);
            tracker.deleteExpense(0L);
            List<Expense> expenses = tracker.getAllExpenses(1L);
            assertEquals(0, expenses.size());
        }
        @Test
        public void testDeleteExpenseInvalidIndex() {
            Expense expense1 = new Expense("Groceries", 50.0, "Food", "2024-06-10");
            tracker.addExpense(expense1);
            assertThrows(IndexOutOfBoundsException.class, () -> tracker.deleteExpense(1L));
        }
        @Test
        public void testFilterByCategory() {
            Expense expense1 = new Expense("Groceries", 50.0, "Food", "2024-06-10");
            Expense expense2 = new Expense("Bus Ticket", 15.0, "Transport", "2024-06-11");
            Expense expense3 = new Expense("Dinner", 30.0, "Food", "2024-06-12");
            tracker.addExpense(expense1);
            tracker.addExpense(expense2);
            tracker.addExpense(expense3);
            List<Object[]> foodExpenses = tracker.getExpensesByCategory(1L);
            assertEquals(2, foodExpenses.size());
            assertTrue(foodExpenses.stream().anyMatch(exp -> exp.getClass().equals("Groceries")));
            assertTrue(foodExpenses.stream().anyMatch(exp -> exp.getClass().equals("Dinner")));
        }
        @Test
        public void testFilterByCategoryNoMatch() {
            Expense expense1 = new Expense("Groceries", 50.0, "Food", "2024-06-10");
            tracker.addExpense(expense1);
            List<Object[]> transportExpenses = tracker.getExpensesByCategory(1L);
            assertEquals(0, transportExpenses.size());
        }
        @Test
        public void testEditExpenseInvalidIndex() {
            Expense expense1 = new Expense("Groceries", 50.0, "Food", "2024-06-10");
            tracker.addExpense(expense1);
            Expense updatedExpense = new Expense("Supermarket", 60.0, "Food", "2024-06-11");
            assertThrows(IndexOutOfBoundsException.class, () -> tracker.updateExpense(1L, updatedExpense));
        }

}
