<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <h1 class="page-title">六十四卦资料库</h1>
      <p class="page-subtitle">
        每一卦都被拆成古文、白话、处境和行动建议四层，帮助用户把“卦象”转成现实判断。
      </p>
      <el-input v-model="keyword" placeholder="搜索卦名、结构或关键词" class="search-box" @input="loadHexagrams" />
    </header>

    <div class="section-grid two-col hexagram-grid">
      <RouterLink
        v-for="item in hexagrams"
        :key="item.id"
        :to="`/hexagrams/${item.id}`"
        class="glass-panel hexagram-card"
      >
        <div class="hexagram-head">
          <span>第 {{ item.sequenceNo }} 卦</span>
          <strong>{{ item.name }}</strong>
        </div>
        <p>{{ item.structure }}</p>
        <div class="keyword-row">
          <span v-for="keywordItem in item.keywords" :key="keywordItem">{{ keywordItem }}</span>
        </div>
        <p class="summary">{{ item.shortDescription }}</p>
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import type { HexagramSummary } from '../types';
import { fetchHexagrams } from '../api/content';

const keyword = ref('');
const hexagrams = ref<HexagramSummary[]>([]);

const loadHexagrams = async () => {
  hexagrams.value = await fetchHexagrams(keyword.value);
};

onMounted(loadHexagrams);
</script>

<style scoped>
.page-header,
.hexagram-card {
  padding: 28px;
}

.search-box {
  margin-top: 20px;
}

.hexagram-grid {
  margin-top: 24px;
}

.hexagram-head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.hexagram-head span {
  color: var(--accent);
}

.keyword-row {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.keyword-row span {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
  font-size: 0.88rem;
}

.summary {
  color: var(--text-soft);
}
</style>

