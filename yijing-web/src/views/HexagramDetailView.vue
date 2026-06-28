<template>
  <div class="page-container" v-if="detail">
    <section class="glass-panel page-header">
      <p class="eyebrow">Hexagram Detail</p>
      <h1 class="page-title">{{ detail.hexagram.name }}</h1>
      <p class="page-subtitle">{{ detail.hexagram.structure }} · {{ detail.hexagram.shortDescription }}</p>
      <div class="keyword-row">
        <span v-for="item in detail.hexagram.keywords" :key="item">{{ item }}</span>
      </div>
    </section>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>卦象图与上下卦</h2>
        <div class="detail-list">
          <span><strong>上卦</strong>{{ detail.upperTrigram.name }}卦 · {{ detail.upperTrigram.nature }}</span>
          <span><strong>下卦</strong>{{ detail.lowerTrigram.name }}卦 · {{ detail.lowerTrigram.nature }}</span>
          <span><strong>结构</strong>{{ detail.hexagram.structure }}</span>
          <span><strong>一句话解释</strong>{{ detail.hexagram.shortDescription }}</span>
        </div>
      </section>

      <section class="glass-panel detail-card">
        <h2>当前处境与行动建议</h2>
        <p><strong>当前处境：</strong>{{ detail.hexagram.situationAnalysis }}</p>
        <p><strong>行动建议：</strong>{{ detail.hexagram.advice }}</p>
      </section>
    </div>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>卦辞、彖辞、象辞</h2>
        <div class="detail-list">
          <span><strong>卦辞</strong>{{ detail.hexagram.guaCi }}</span>
          <span><strong>彖辞</strong>{{ detail.hexagram.tuanCi }}</span>
          <span><strong>象辞</strong>{{ detail.hexagram.xiangCi }}</span>
        </div>
      </section>

      <section class="glass-panel detail-card">
        <h2>白话解释与常见误解</h2>
        <p>{{ detail.hexagram.plainExplanation }}</p>
        <p><strong>常见误解：</strong>{{ detail.hexagram.commonMistakes }}</p>
      </section>
    </div>

    <div class="section-grid two-col detail-grid">
      <section class="glass-panel detail-card">
        <h2>现实场景判断</h2>
        <div class="detail-list">
          <span><strong>事业</strong>{{ detail.hexagram.careerExplanation }}</span>
          <span><strong>感情</strong>{{ detail.hexagram.loveExplanation }}</span>
          <span><strong>人际</strong>{{ detail.hexagram.relationExplanation }}</span>
          <span><strong>创业</strong>{{ detail.hexagram.businessExplanation }}</span>
        </div>
      </section>

      <section class="glass-panel detail-card">
        <h2>阅读这卦时最值得问自己的问题</h2>
        <ul class="question-list">
          <li>我现在处在哪个阶段，是起步、受阻、积累、转折还是收束？</li>
          <li>当前局势里最关键的风险、边界和变化点分别是什么？</li>
          <li>我该进、该退、该守、该等，还是该先做减法？</li>
        </ul>
      </section>
    </div>

    <section class="glass-panel detail-card">
      <h2>六爻展开</h2>
      <div class="line-grid">
        <article v-for="line in detail.lines" :key="line.id" class="line-card">
          <strong>{{ line.lineName }}</strong>
          <p class="line-original">{{ line.originalText }}</p>
          <p><strong>白话解释：</strong>{{ line.plainExplanation }}</p>
          <p><strong>现代含义：</strong>{{ line.modernMeaning }}</p>
          <p class="line-note"><strong>行动建议：</strong>{{ line.advice }}</p>
        </article>
      </div>
    </section>

    <section class="glass-panel detail-card" v-if="detail.relatedHexagrams.length">
      <h2>相关卦</h2>
      <div class="section-grid three-col">
        <RouterLink
          v-for="item in detail.relatedHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="glass-panel related-card"
        >
          <strong>{{ item.name }}</strong>
          <p>{{ item.structure }}</p>
          <p>{{ item.shortDescription }}</p>
        </RouterLink>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import type { HexagramDetailView as HexagramDetailType } from '../types';
import { fetchHexagram } from '../api/content';

const route = useRoute();
const detail = ref<HexagramDetailType | null>(null);

onMounted(async () => {
  detail.value = await fetchHexagram(route.params.id as string);
});
</script>

<style scoped>
.page-header,
.detail-card,
.related-card {
  padding: 28px;
}

.eyebrow {
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
  margin: 0 0 12px;
}

.keyword-row {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  margin-top: 16px;
}

.keyword-row span {
  padding: 6px 10px;
  border-radius: 999px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
}

.detail-grid {
  margin-top: 24px;
}

.question-list {
  margin: 0;
  padding-left: 20px;
  color: var(--text-soft);
  line-height: 1.8;
}

.line-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 16px;
}

.line-card {
  border-radius: 20px;
  border: 1px solid var(--border);
  padding: 18px;
  background: rgba(255, 252, 246, 0.72);
}

.line-card p {
  line-height: 1.75;
}

.line-original {
  color: var(--brand);
}

.line-note {
  color: var(--brand-soft);
}

.related-card {
  display: block;
}
</style>
