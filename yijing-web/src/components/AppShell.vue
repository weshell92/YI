<template>
  <div>
    <header class="site-header">
      <div class="site-header__inner">
        <RouterLink class="brand" to="/">
          <span class="brand__seal">易</span>
          <div>
            <strong>{{ site.title }}</strong>
            <p>{{ site.tagline }}</p>
          </div>
        </RouterLink>
        <nav class="nav-links">
          <RouterLink v-for="item in navItems" :key="item.to" :to="item.to">{{ item.label }}</RouterLink>
        </nav>
      </div>
    </header>

    <main>
      <RouterView />
    </main>

    <footer class="site-footer">
      <div class="page-container site-footer__inner">
        <div>
          <strong>易经研习系统</strong>
          <p>先学习，再起卦；先解释，再判断；先反思，再行动。</p>
        </div>
        <div class="footer-links">
          <RouterLink to="/learn">学习路径</RouterLink>
          <RouterLink to="/hexagrams">六十四卦</RouterLink>
          <RouterLink to="/divination">在线起卦</RouterLink>
          <RouterLink to="/admin">后台管理</RouterLink>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import { useSiteStore } from '../store/site';

const site = useSiteStore();

const navItems = [
  { label: '学习易经', to: '/learn' },
  { label: '八卦', to: '/trigrams' },
  { label: '六十四卦', to: '/hexagrams' },
  { label: '在线起卦', to: '/divination' },
  { label: '案例库', to: '/cases' },
  { label: 'AI 解卦', to: '/ai' },
  { label: '用户中心', to: '/user' },
  { label: '后台', to: '/admin' }
];
</script>

<style scoped>
.site-header {
  position: sticky;
  top: 0;
  z-index: 20;
  background: rgba(246, 240, 229, 0.88);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid var(--border);
}

.site-header__inner {
  max-width: 1180px;
  margin: 0 auto;
  padding: 14px 20px;
  display: flex;
  gap: 24px;
  align-items: center;
  justify-content: space-between;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand strong {
  display: block;
  font-size: 1rem;
}

.brand p {
  margin: 4px 0 0;
  color: var(--text-soft);
  font-size: 0.88rem;
}

.brand__seal {
  width: 48px;
  height: 48px;
  display: grid;
  place-items: center;
  border-radius: 14px;
  background: linear-gradient(135deg, var(--brand), #15362d);
  color: #f6f0e5;
  font-family: "Noto Serif SC", "Songti SC", serif;
  font-size: 1.4rem;
}

.nav-links {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  justify-content: flex-end;
}

.nav-links a {
  padding: 8px 12px;
  border-radius: 999px;
  color: var(--text-soft);
}

.nav-links a.router-link-active {
  background: rgba(35, 75, 64, 0.09);
  color: var(--brand);
}

.site-footer {
  border-top: 1px solid var(--border);
}

.site-footer__inner {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  color: var(--text-soft);
}

.footer-links {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}

@media (max-width: 900px) {
  .site-header__inner,
  .site-footer__inner {
    flex-direction: column;
    align-items: flex-start;
  }

  .nav-links {
    justify-content: flex-start;
  }
}
</style>

