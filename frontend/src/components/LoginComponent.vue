<template>
    <div class="card mx-auto" style="max-width: 400px;">
    <div class="card-body">
    <h3 class="card-title text-center text-primary">Login</h3>
    <form @submit.prevent="login">
    <div class="mb-3">
    <label for="username" class="form-label">Username</label>
    <input
               type="text"
               id="username"
               class="form-control"
               v-model="username"
               placeholder="Enter your username"
               required
             />
    </div>
    <div class="mb-3">
    <label for="password" class="form-label">Password</label>
    <input
               type="password"
               id="password"
               class="form-control"
               v-model="password"
               placeholder="Enter your password"
               required
             />
    </div>
    <button type="submit" class="btn btn-primary w-100">Login</button>
    </form>
    <p class="text-center mt-3">
           Don't have an account?
    <router-link to="/register" class="text-decoration-none">Register here</router-link>
    </p>
    </div>
    </div>
</template>
<script>
    // import axios from "axios";
    export default {
     data() {
       return {
         username: '',
         password: '',
       };
     },
     methods: {
        async login() {
        try {
        // const response = await axios.post("http://localhost:8080/login", {
        //     username: this.username,
        //     password: this.password,
        // });
        alert("Login Successful");
        this.$router.push({ name: 'ListExpenses' })
        } catch (error) {
        alert("Login failed");
        }
    },
     },
    };
</script>
<style>
</style>