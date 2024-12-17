<template>
        
    <div class="container mt-5">
        <!-- Logout Button -->
        <div class="d-flex justify-content-end mb-4">
            <!-- <router-link to="/login" class="text-right">Logout Test2 User</router-link> -->
    </div>    
    <!-- <h4 class="text-center mb-4">Welcome to Test2 expenses</h4> -->
    
    <!-- Toggle Buttons -->
    <div class="d-flex justify-content-between mb-4">
    <!-- Button to toggle Add Expense Form -->
    <button class="btn btn-primary" @click="toggleAddExpenseTab">
           {{ showAddExpense ? "Hide Add Expense" : "Add New Expense" }}
    </button>
    <!-- Button to toggle Filters Tab -->
    <button class="btn btn-secondary" @click="toggleFiltersTab">
           {{ showFilters ? "Hide Filters" : "Show Filters" }}
    </button>
    </div>
    <!-- Add Expense Form -->
    <div v-if="showAddExpense" class="card mb-4">
    <div class="card-header">{{ isEditing ? "Edit Expense" : "Add Expense" }}</div>
    <div class="card-body">
    <form @submit.prevent="saveExpense">
    <div class="mb-3">
    <label for="title" class="form-label">Title</label>
    <input
                 v-model="form.title"
                 type="text"
                 id="title"
                 class="form-control"
                 placeholder="Enter expense title"
                 required
               />
    </div>
    <div class="mb-3">
    <label for="amount" class="form-label">Amount</label>
    <input
                 v-model.number="form.amount"
                 type="number"
                 id="amount"
                 class="form-control"
                 placeholder="Enter amount"
                 required
               />
    </div>
    <div class="mb-3">
    <label for="category" class="form-label">Category</label>
    <select v-model="form.category" id="category" class="form-select" required>
    <option value="" disabled>Select a category</option>
    <option v-for="category in categories" :key="category" :value="category">
                   {{ category }}
    </option>
    </select>
    </div>
    <div class="mb-3">
    <label for="date" class="form-label">Date</label>
    <input v-model="form.date" type="date" id="date" class="form-control" required />
    </div>
    <div class="d-flex justify-content-end">
    <button type="submit" class="btn btn-primary me-2">{{ isEditing ? "Update" : "Add" }}</button>
    <button
                 type="button"
                 v-if="isEditing"
                 class="btn btn-secondary"
                 @click="cancelEdit"
    >
                 Cancel
    </button>
    </div>
    </form>
    </div>
    </div>
    <!-- Filters Tab -->
    <div v-if="showFilters" class="card mb-4">
    <div class="card-header">Filters</div>
    <div class="card-body">
    <div class="row g-3">
    <div class="col-md-4">
    <label for="filterCategory" class="form-label">Category</label>
    <select v-model="filters.category" id="filterCategory" class="form-select">
    <option value="">All Categories</option>
    <option v-for="category in categories" :key="category" :value="category">
                   {{ category }}
    </option>
    </select>
    </div>
    <div class="col-md-4">
    <label for="filterDateFrom" class="form-label">Date From</label>
    <input v-model="filters.dateFrom" type="date" id="filterDateFrom" class="form-control" />
    </div>
    <div class="col-md-4">
    <label for="filterDateTo" class="form-label">Date To</label>
    <input v-model="filters.dateTo" type="date" id="filterDateTo" class="form-control" />
    </div>
    </div>
    <div class="row g-3 mt-3">
    <div class="col-md-6">
    <label for="filterKeyword" class="form-label">Keyword</label>
    <input
                 v-model="filters.keyword"
                 type="text"
                 id="filterKeyword"
                 class="form-control"
                 placeholder="Search by title"
               />
    </div>
    <div class="col-md-6 d-flex align-items-end">
    <button class="btn btn-secondary w-100" @click="clearFilters">Clear Filters</button>
    </div>
    </div>
    </div>
    </div>
    <!-- Expense List -->
    <h2 class="text-center mb-3">Expense List</h2>
    <table class="table table-striped">
    <thead class="table-dark">
    <tr>
    <th>Title</th>
    <th>Amount</th>
    <th>Category</th>
    <th>Date</th>
    <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(expense, index) in filteredExpenses" :key="index">
    <td>{{ expense.title }}</td>
    <td>{{ expense.amount }}</td>
    <td>{{ expense.category }}</td>
    <td>{{ expense.date }}</td>
    <td>
    <button class="btn btn-sm btn-warning me-2" @click="editExpense(index)">Edit</button>
    <button class="btn btn-sm btn-danger" @click="deleteExpense(index)">Delete</button>
    </td>
    </tr>
    <tr v-if="filteredExpenses.length === 0">
    <td colspan="5" class="text-center">No expenses found</td>
    </tr>
    </tbody>
    </table>
    <!-- Export to CSV -->
    <div class="d-flex justify-content-end mt-3">
    <button class="btn btn-success" @click="exportToCSV">Export to CSV</button>
    </div>
    </div>
    </template>
    <script>
    import { ref, computed } from "vue";
    export default {
     name: "ExpenseTracker",
     setup() {
       const expenses = ref([]);
       const categories = ["Food", "Transport", "Utilities", "Entertainment", "Miscellaneous"];
       const form = ref({
         title: "",
         amount: null,
         category: "",
         date: "",
       });
       const isEditing = ref(false);
       const editingIndex = ref(null);
       // Toggle states for tabs
       const showAddExpense = ref(false);  // Initially closed
       const showFilters = ref(false);     // Initially closed
       // Filters
       const filters = ref({
         category: "",
         dateFrom: "",
         dateTo: "",
         keyword: "",
       });
       const filteredExpenses = computed(() => {
         return expenses.value.filter((expense) => {
           const matchesCategory = !filters.value.category || expense.category === filters.value.category;
           const matchesDateFrom = !filters.value.dateFrom || new Date(expense.date) >= new Date(filters.value.dateFrom);
           const matchesDateTo = !filters.value.dateTo || new Date(expense.date) <= new Date(filters.value.dateTo);
           const matchesKeyword =
             !filters.value.keyword || expense.title.toLowerCase().includes(filters.value.keyword.toLowerCase());
           return matchesCategory && matchesDateFrom && matchesDateTo && matchesKeyword;
         });
       });
       const saveExpense = () => {
         if (isEditing.value) {
           expenses.value[editingIndex.value] = { ...form.value };
           isEditing.value = false;
           editingIndex.value = null;
         } else {
           expenses.value.push({ ...form.value });
         }
         resetForm();
       };
       const editExpense = (index) => {
         isEditing.value = true;
         editingIndex.value = index;
         form.value = { ...expenses.value[index] };
       };
       const deleteExpense = (index) => {
         expenses.value.splice(index, 1);
       };
       const cancelEdit = () => {
         isEditing.value = false;
         editingIndex.value = null;
         resetForm();
       };
       const resetForm = () => {
         form.value = {
           title: "",
           amount: null,
           category: "",
           date: "",
         };
       };
       // Toggle visibility of Add Expense form
       const toggleAddExpenseTab = () => {
         showAddExpense.value = !showAddExpense.value;
       };
       // Toggle visibility of Filters tab
       const toggleFiltersTab = () => {
         showFilters.value = !showFilters.value;
       };
       // Clear filters
       const clearFilters = () => {
         filters.value = {
           category: "",
           dateFrom: "",
           dateTo: "",
           keyword: "",
         };
       };
       // Export to CSV
       const exportToCSV = () => {
         const csvRows = [
           ["Title", "Amount", "Category", "Date"],
           ...expenses.value.map((expense) => [expense.title, expense.amount, expense.category, expense.date]),
         ];
         const csvContent = csvRows.map((row) => row.join(",")).join("\n");
         const blob = new Blob([csvContent], { type: "text/csv" });
         const url = URL.createObjectURL(blob);
         const a = document.createElement("a");
         a.href = url;
         a.download = `expenses__Test2_User_${new Date().toISOString().slice(0, 10)}.csv`;
         a.click();
         URL.revokeObjectURL(url);
       };
       return {
         expenses,
         categories,
         form,
         isEditing,
         saveExpense,
         editExpense,
         deleteExpense,
         cancelEdit,
         filters,
         filteredExpenses,
         showAddExpense,
         showFilters,
         toggleAddExpenseTab,
         toggleFiltersTab,
         clearFilters,
         exportToCSV,
       };
     },
    };
    </script>
