import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LearnView from '../views/LearnView.vue';
import TrigramListView from '../views/TrigramListView.vue';
import TrigramDetailView from '../views/TrigramDetailView.vue';
import HexagramListView from '../views/HexagramListView.vue';
import HexagramDetailView from '../views/HexagramDetailView.vue';
import CaseListView from '../views/CaseListView.vue';
import CaseDetailView from '../views/CaseDetailView.vue';
import DivinationView from '../views/DivinationView.vue';
import AiAssistView from '../views/AiAssistView.vue';
import UserCenterView from '../views/UserCenterView.vue';
import AdminView from '../views/AdminView.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: HomeView },
    { path: '/learn', component: LearnView },
    { path: '/trigrams', component: TrigramListView },
    { path: '/trigrams/:id', component: TrigramDetailView },
    { path: '/hexagrams', component: HexagramListView },
    { path: '/hexagrams/:id', component: HexagramDetailView },
    { path: '/cases', component: CaseListView },
    { path: '/cases/:id', component: CaseDetailView },
    { path: '/divination', component: DivinationView },
    { path: '/ai', component: AiAssistView },
    { path: '/user', component: UserCenterView },
    { path: '/admin', component: AdminView }
  ]
});

export default router;

