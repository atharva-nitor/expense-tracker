<template>
  <div id="app" class="bg-light vh-100">
  <div class="container py-5">
  <header class="text-center mb-4">
  <h1 class="text-primary">Expense Tracker</h1>
  </header>
  <router-view />
  </div>
  </div>
  </template>
  <script>
  export default {
   name: 'App',
  };
  </script>
  <style>
  
  </style>