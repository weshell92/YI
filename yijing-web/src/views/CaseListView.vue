<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <h1 class="page-title">现代案例库</h1>
      <p class="page-subtitle">让卦象落到现实场景里。首批案例覆盖职场、感情、创业与人际等高频决策问题。</p>
      <el-select v-model="category" clearable placeholder="按分类筛选" class="filter-select" @change="loadCases">
        <el-option v-for="item in categories" :key="item" :label="item" :value="item" />
      </el-select>
    </header>

    <div class="section-grid two-col case-grid">
      <RouterLink v-for="item in cases" :key="item.id" :to="`/cases/${item.id}`" class="glass-panel case-card">
        <span class="case-tag">{{ item.category }}</span>
        <h2>{{ item.title }}</h2>
        <p>{{ item.background }}</p>
        <p class="case-analysis">{{ item.modernAnalysis }}</p>
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import type { CaseStudy } from '../types';
import { fetchCases } from '../api/content';

const cases = ref<CaseStudy[]>([]);
const category = ref('');
const categories = ['职场', '感情', '创业', '投资', '合作', '人际', '家庭', '学习', '低谷期', '人生选择'];

const loadCases = async () => {
  cases.value = await fetchCases(category.value);
};

onMounted(loadCases);
</script>

<style scoped>
.page-header,
.case-card {
  padding: 28px;
}

.filter-select {
  margin-top: 20px;
  width: 240px;
}

.case-grid {
  margin-top: 24px;
}

.case-tag {
  color: var(--accent);
}

.case-analysis {
  color: var(--brand);
}
</style>

