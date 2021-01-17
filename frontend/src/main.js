import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Navbar from '@/components/Navbar.vue'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'

createApp(App).use(router).mount('#app');
createApp(Navbar).use(router).mount('header');
