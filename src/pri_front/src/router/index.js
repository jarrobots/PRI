import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue' 
import ReviewPanel from '@/views/ReviewPanel.vue'
import GroupPreview from '@/views/GroupPreview.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/review-panel',
    name: 'ReviewPanel',
    component: ReviewPanel,
  },
  {
    path: '/group-preview',
    name: 'GroupPreview',
    component: GroupPreview,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
