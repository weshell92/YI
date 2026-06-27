<template>
  <div class="page-container" v-if="caseStudy">
    <section class="glass-panel detail-card">
      <span class="case-tag">{{ caseStudy.category }}</span>
      <h1 class="page-title">{{ caseStudy.title }}</h1>
      <p class="page-subtitle">{{ caseStudy.background }}</p>
    </section>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>卦象分析</h2>
        <p>{{ caseStudy.analysis }}</p>
        <p>{{ caseStudy.modernAnalysis }}</p>
      </section>
      <section class="glass-panel detail-card">
        <h2>行动建议</h2>
        <p>{{ caseStudy.advice }}</p>
        <RouterLink :to="`/hexagrams/${caseStudy.hexagramId}`">查看关联卦象</RouterLink>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import type { CaseStudy } from '../types';
import { fetchCase } from '../api/content';

const route = useRoute();
const caseStudy = ref<CaseStudy | null>(null);

onMounted(async () => {
  caseStudy.value = await fetchCase(route.params.id as string);
});
</script>

<style scoped>
.detail-card {
  padding: 28px;
}

.detail-grid {
  margin-top: 24px;
}

.case-tag {
  color: var(--accent);
}
</style>

