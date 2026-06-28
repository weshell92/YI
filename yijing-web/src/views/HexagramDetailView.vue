<template>
  <div class="page-container" v-if="detail">
    <section class="glass-panel hero-card">
      <div class="hero-main">
        <div>
          <p class="eyebrow">Hexagram Detail</p>
          <h1 class="page-title">{{ detail.hexagram.name }}</h1>
          <p class="hero-subtitle">{{ detail.hexagram.hexagramCardTitle }}</p>
          <p class="page-subtitle">{{ detail.hexagram.shortDescription }}</p>
        </div>

        <HexagramGlyph :pattern="detail.hexagram.hexagramSymbol" :aria-label="`${detail.hexagram.name}卦卦符`" />
      </div>

      <div class="hero-meta">
        <div class="meta-item">
          <strong>序号</strong>
          <span>第 {{ detail.hexagram.sequenceNo }} 卦</span>
        </div>
        <div class="meta-item">
          <strong>上卦</strong>
          <span>{{ detail.upperTrigram.name }} · {{ detail.upperTrigram.nature }}</span>
        </div>
        <div class="meta-item">
          <strong>下卦</strong>
          <span>{{ detail.lowerTrigram.name }} · {{ detail.lowerTrigram.nature }}</span>
        </div>
        <div class="meta-item">
          <strong>结构</strong>
          <span>{{ detail.hexagram.structure }}</span>
        </div>
      </div>

      <div class="keyword-row">
        <span v-for="item in detail.hexagram.keywords" :key="item">{{ item }}</span>
      </div>
    </section>

    <section class="section-grid two-col detail-grid">
      <article class="glass-panel section-card">
        <h2>卦象结构</h2>
        <p>{{ detail.hexagram.hexagramCardTitle }} 由 {{ detail.upperTrigram.name }}上 {{ detail.lowerTrigram.name }}下 组成。</p>
        <div class="detail-list">
          <span><strong>上卦意象</strong>{{ detail.upperTrigram.plainExplanation }}</span>
          <span><strong>下卦意象</strong>{{ detail.lowerTrigram.plainExplanation }}</span>
          <span><strong>现代提示</strong>{{ detail.hexagram.shortDescription }}</span>
        </div>
      </article>

      <article class="glass-panel section-card">
        <h2>卦辞原文</h2>
        <p class="classical-text">{{ detail.hexagram.guaCi }}</p>
      </article>
    </section>

    <section class="glass-panel section-card ten-wings">
      <h2>十翼原文</h2>
      <div class="section-grid two-col">
        <article v-for="item in wingSections" :key="item.title" class="wing-card">
          <h3>{{ item.title }}</h3>
          <p class="classical-text">{{ item.content }}</p>
        </article>
      </div>
    </section>

    <section class="section-grid two-col detail-grid">
      <article class="glass-panel section-card">
        <h2>白话解释</h2>
        <p>{{ detail.hexagram.plainExplanation }}</p>
      </article>

      <article class="glass-panel section-card">
        <h2>处境分析</h2>
        <p>{{ detail.hexagram.situationAnalysis }}</p>
      </article>
    </section>

    <section class="glass-panel section-card">
      <h2>行动建议</h2>
      <p>{{ detail.hexagram.advice }}</p>
      <p class="mistake-note"><strong>常见误解：</strong>{{ detail.hexagram.commonMistakes }}</p>
    </section>

    <section class="section-grid two-col detail-grid">
      <article class="glass-panel section-card">
        <h2>事业 / 创业</h2>
        <div class="detail-list">
          <span><strong>事业</strong>{{ detail.hexagram.careerExplanation }}</span>
          <span><strong>创业</strong>{{ detail.hexagram.businessExplanation }}</span>
        </div>
      </article>

      <article class="glass-panel section-card">
        <h2>感情 / 人际</h2>
        <div class="detail-list">
          <span><strong>感情</strong>{{ detail.hexagram.loveExplanation }}</span>
          <span><strong>人际</strong>{{ detail.hexagram.relationExplanation }}</span>
        </div>
      </article>
    </section>

    <section class="glass-panel section-card">
      <h2>六爻展开</h2>
      <div class="line-grid">
        <article v-for="line in detail.lines" :key="line.id" class="line-card">
          <div class="line-head">
            <YaoStroke :is-yang="isYangLine(line.lineNo)" compact />
            <strong>{{ line.lineName }}</strong>
          </div>
          <p class="classical-text">{{ line.originalText }}</p>
          <p><strong>小象原文：</strong>{{ line.xiangOriginalText }}</p>
          <p><strong>白话解释：</strong>{{ line.plainExplanation }}</p>
          <p><strong>现代含义：</strong>{{ line.modernMeaning }}</p>
          <p><strong>行动建议：</strong>{{ line.advice }}</p>
        </article>
      </div>
    </section>

    <section class="glass-panel section-card" v-if="detail.relatedHexagrams.length">
      <h2>相关卦</h2>
      <div class="related-grid">
        <RouterLink
          v-for="item in detail.relatedHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="related-card"
        >
          <HexagramGlyph :pattern="item.hexagramSymbol" compact />
          <div>
            <strong>{{ item.name }}</strong>
            <p>{{ item.hexagramCardTitle }}</p>
            <p>{{ item.shortDescription }}</p>
          </div>
        </RouterLink>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import HexagramGlyph from '../components/HexagramGlyph.vue';
import YaoStroke from '../components/YaoStroke.vue';
import { fetchHexagram } from '../api/content';
import type { HexagramDetailView as HexagramDetailType } from '../types';

const route = useRoute();
const detail = ref<HexagramDetailType | null>(null);

const wingSections = computed(() => {
  if (!detail.value) {
    return [];
  }
  return [
    { title: '彖传', content: detail.value.hexagram.tuanCi },
    { title: '大象传', content: detail.value.hexagram.daXiang },
    {
      title: '小象传',
      content: detail.value.lines.map((line) => `${line.lineName}：${line.xiangOriginalText}`).join('\n')
    },
    { title: '文言传', content: detail.value.hexagram.wenYan },
    { title: '序卦传', content: detail.value.hexagram.xuGua },
    { title: '杂卦传', content: detail.value.hexagram.zaGua || '本卦在《杂卦传》中暂无独立对句。' },
    { title: '说卦传', content: detail.value.hexagram.shuoGua },
    { title: '系辞上传', content: detail.value.hexagram.xiCiShang },
    { title: '系辞下传', content: detail.value.hexagram.xiCiXia }
  ];
});

const isYangLine = (lineNo: number) => detail.value?.hexagram.hexagramSymbol[lineNo - 1] === '1';

const load = async () => {
  detail.value = await fetchHexagram(route.params.id as string);
};

watch(() => route.params.id, load, { immediate: true });
</script>

<style scoped>
.hero-card,
.section-card {
  padding: 28px;
}

.eyebrow {
  margin: 0 0 12px;
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.hero-main {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  align-items: center;
}

.hero-subtitle {
  margin: 0 0 10px;
  color: var(--brand);
  font-size: 1.1rem;
}

.hero-meta {
  margin-top: 22px;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 14px;
}

.meta-item {
  border-radius: 18px;
  padding: 16px;
  background: var(--surface-strong);
  border: 1px solid var(--border);
}

.meta-item strong,
.section-card h2,
.wing-card h3,
.line-card strong {
  color: var(--brand);
}

.meta-item span,
.mistake-note,
.detail-list,
.related-card p {
  color: var(--text-soft);
}

.keyword-row {
  margin-top: 18px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.keyword-row span {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
}

.detail-grid,
.ten-wings,
.section-card + .section-card {
  margin-top: 24px;
}

.classical-text {
  white-space: pre-line;
  line-height: 1.9;
  color: #27302c;
}

.wing-card {
  border-radius: 18px;
  padding: 18px;
  background: rgba(255, 252, 246, 0.76);
  border: 1px solid rgba(35, 75, 64, 0.08);
}

.line-grid,
.related-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 16px;
}

.line-card {
  border-radius: 18px;
  padding: 18px;
  background: var(--surface-strong);
  border: 1px solid rgba(35, 75, 64, 0.08);
}

.line-head {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.related-card {
  display: flex;
  gap: 14px;
  align-items: center;
  padding: 18px;
  border-radius: 18px;
  background: rgba(255, 252, 246, 0.76);
  border: 1px solid rgba(35, 75, 64, 0.08);
}

@media (max-width: 960px) {
  .hero-main,
  .hero-meta,
  .line-grid,
  .related-grid {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
