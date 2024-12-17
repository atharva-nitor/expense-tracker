import { createRouter, createWebHistory } from 'vue-router';
import LoginComponent from '../components/LoginComponent.vue';
import RegisterComponent from '../components/RegisterComponent.vue';
import ListExpensesComponent from '../components/ListExpensesComponent.vue';
const routes = [
 {
   path: '/login',
   name: 'Login',
   component: LoginComponent,
 },
 {
   path: '/register',
   name: 'Register',
   component: RegisterComponent,
 },
 {
  path: '/expenses',
  name: 'ListExpenses',
  component: ListExpensesComponent,
},
];
const router = createRouter({
 history: createWebHistory(),
 routes,
});
export default router;
