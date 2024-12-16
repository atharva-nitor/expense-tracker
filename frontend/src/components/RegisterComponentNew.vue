<template>
    <div class="card mx-auto" style="max-width: 400px;">
    <div class="card-body">
    <h3 class="card-title text-center text-primary">Register</h3>
    <form @submit.prevent="register">
    <div class="mb-3">
    <label for="username" class="form-label">Username</label>
    <input
               type="text"
               id="username"
               class="form-control"
               v-model="username"
               placeholder="Choose a username"
               required
             />
    </div>
    <div class="mb-3">
    <label for="email" class="form-label">Email</label>
    <input
               type="email"
               id="email"
               class="form-control"
               v-model="email"
               placeholder="Enter your email"
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
               placeholder="Create a password"
               required
             />
    </div>
    <button type="submit" class="btn btn-primary w-100">Register</button>
    </form>
    <p class="text-center mt-3">
           Already have an account?
    <router-link to="/login" class="text-decoration-none">Login here</router-link>
    </p>
    </div>
    </div>
</template>
<script>
    import axios from "axios";
    axios.defaults.baseURL = 'http://localhost:8080'; // Spring Boot backend URL
    axios.defaults.headers.common['Content-Type'] = 'application/json';
    axios.defaults.headers.common['Access-Control-Allow-Origin'] = 'http://localhost:8081';
    axios.defaults.withCredentials = true; // If using cookies for session
    export default {
     data() {
       return {
         username: '',
         email: '',
         password: '',
       };
     },
     methods: {
        async register() {
        try {
        const response = await axios.post("http://localhost:8080/register", {
            username: this.username,
            email: this.email,
            password: this.password,
        });
        alert(response.data);
        } catch (error) {
        alert("Registration failed");
        }
    },
     },
    };
</script>
<style>
</style>