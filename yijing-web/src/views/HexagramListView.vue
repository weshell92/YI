<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <p class="eyebrow">Hexagram Index</p>
      <h1 class="page-title">六十四卦列表</h1>
      <p class="page-subtitle">
        这是完整的六十四卦索引页。你可以按卦名、结构、关键词搜索，也可以按阶段区间和常见主题快速浏览。
      </p>

      <div class="toolbar">
        <el-input
          v-model="keyword"
          placeholder="搜索卦名、结构、关键词或一句话解释"
          class="search-box"
        />
      </div>

      <div class="filter-row">
        <span class="filter-label">阶段浏览</span>
        <button
          v-for="item in rangeFilters"
          :key="item.value"
          class="filter-chip"
          :class="{ 'filter-chip--active': activeRange === item.value }"
          @click="activeRange = item.value"
        >
          {{ item.label }}
        </button>
      </div>

      <div class="filter-row">
        <span class="filter-label">常见主题</span>
        <button
          v-for="item in themeFilters"
          :key="item.value"
          class="filter-chip"
          :class="{ 'filter-chip--active': activeTheme === item.value }"
          @click="activeTheme = item.value"
        >
          {{ item.label }}
        </button>
      </div>
    </header>

    <div class="summary-bar glass-panel">
      <strong>当前结果：</strong>
      <span>共 {{ filteredHexagrams.length }} 条</span>
    </div>

    <div class="section-grid two-col hexagram-grid">
      <RouterLink
        v-for="item in filteredHexagrams"
        :key="item.id"
        :to="`/hexagrams/${item.id}`"
        class="glass-panel hexagram-card"
      >
        <div class="hexagram-head">
          <span>第 {{ item.sequenceNo }} 卦</span>
          <strong>{{ item.name }}</strong>
        </div>
        <p class="structure">{{ item.structure }}</p>
        <div class="keyword-row">
          <span v-for="keywordItem in item.keywords" :key="keywordItem">{{ keywordItem }}</span>
        </div>
        <p class="summary">{{ item.shortDescription }}</p>
      </RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import type { HexagramSummary } from '../types';
import { fetchHexagrams } from '../api/content';

const keyword = ref('');
const activeRange = ref('all');
const activeTheme = ref('all');
const hexagrams = ref<HexagramSummary[]>([]);

const rangeFilters = [
  { label: '全部', value: 'all' },
  { label: '1-8 开局识势', value: '1-8' },
  { label: '9-16 组织与秩序', value: '9-16' },
  { label: '17-24 关系与修正', value: '17-24' },
  { label: '25-32 风险与承压', value: '25-32' },
  { label: '33-40 进退与解困', value: '33-40' },
  { label: '41-48 增减与经营', value: '41-48' },
  { label: '49-56 变革与高光', value: '49-56' },
  { label: '57-64 收束与完成', value: '57-64' }
];

const themeFilters = [
  { label: '全部', value: 'all' },
  { label: '等待准备', value: '等待' },
  { label: '开局成长', value: '开创' },
  { label: '关系协作', value: '关系' },
  { label: '风险承压', value: '风险' },
  { label: '变革转折', value: '变革' },
  { label: '减法止损', value: '减法' }
];

const filteredHexagrams = computed(() => {
  const query = keyword.value.trim();
  return hexagrams.value.filter((item) => {
    const inRange = matchRange(item.sequenceNo, activeRange.value);
    const inTheme = matchTheme(item, activeTheme.value);
    const inSearch =
      !query ||
      item.name.includes(query) ||
      item.structure.includes(query) ||
      item.shortDescription.includes(query) ||
      item.keywords.some((entry) => entry.includes(query));
    return inRange && inTheme && inSearch;
  });
});

function matchRange(sequenceNo: number, range: string) {
  if (range === 'all') {
    return true;
  }
  const [start, end] = range.split('-').map((value) => Number.parseInt(value, 10));
  return sequenceNo >= start && sequenceNo <= end;
}

function matchTheme(item: HexagramSummary, theme: string) {
  if (theme === 'all') {
    return true;
  }
  const source = [item.name, item.shortDescription, ...item.keywords].join('');
  return source.includes(theme);
}

onMounted(async () => {
  hexagrams.value = await fetchHexagrams();
});
</script>

<style scoped>
.page-header,
.hexagram-card,
.summary-bar {
  padding: 28px;
}

.eyebrow {
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
  margin: 0 0 12px;
}

.toolbar {
  margin-top: 20px;
}

.filter-row {
  margin-top: 18px;
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  align-items: center;
}

.filter-label {
  color: var(--brand-soft);
  font-size: 0.92rem;
}

.filter-chip {
  border: 1px solid var(--border);
  background: rgba(255, 252, 246, 0.8);
  color: var(--text-soft);
  padding: 8px 12px;
  border-radius: 999px;
  cursor: pointer;
}

.filter-chip--active {
  background: rgba(35, 75, 64, 0.12);
  color: var(--brand);
}

.summary-bar {
  margin-top: 24px;
  display: flex;
  gap: 12px;
  align-items: center;
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

.structure {
  color: var(--brand-soft);
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
  line-height: 1.8;
}
</style>
