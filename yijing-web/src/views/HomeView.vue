<template>
  <div class="page-container">
    <section class="hero glass-panel">
      <div class="hero-copy">
        <p class="hero-tag">学习 · 解释 · 判断 · 复盘</p>
        <h1 class="page-title">用现代语言学习《易经》，理解变化，辅助决策</h1>
        <p class="page-subtitle">
          易经研习系统不是“替你算结果”的网站，而是帮助你用卦象理解处境、识别变化、整理风险并形成行动建议的学习平台。
        </p>

        <div class="hero-actions">
          <RouterLink class="hero-link hero-link--primary" to="/learn">从零学习易经</RouterLink>
          <RouterLink class="hero-link" to="/hexagrams">查询六十四卦</RouterLink>
          <RouterLink class="hero-link" to="/divination">在线起卦</RouterLink>
          <RouterLink class="hero-link" to="/cases">查看真实案例</RouterLink>
        </div>

        <div class="hero-statement">
          <strong>非迷信说明</strong>
          <p>本网站只提供结构化解释与复盘辅助，不提供“必然成功/失败”的绝对预测。</p>
        </div>
      </div>

      <div class="hero-panel">
        <div class="metric-card">
          <strong>8</strong>
          <span>八卦基础词汇</span>
          <p>先认识八种基础力量，再读更复杂的六十四卦。</p>
        </div>
        <div class="metric-card">
          <strong>64</strong>
          <span>完整六十四卦库</span>
          <p>每一卦都配卦牌、卦辞、十翼、小象和现代分析。</p>
        </div>
        <div class="metric-card">
          <strong>1 条路径</strong>
          <span>先学再用</span>
          <p>先学结构，再看原文，最后回到现实问题与复盘。</p>
        </div>
      </div>
    </section>

    <div class="section-grid three-col intro-grid">
      <SectionBlock
        eyebrow="定位"
        title="先解释，不先神化"
        description="每个页面都优先给出现代语言的结构解释，避免把《易经》变成只有结论、没有推理过程的黑盒。"
      />
      <SectionBlock
        eyebrow="方法"
        title="先学习，再进入问题"
        description="先认识八卦、卦符、六爻和变卦，再进入起卦、案例和复盘，学习路径更稳。"
      />
      <SectionBlock
        eyebrow="目标"
        title="先反思，再行动"
        description="系统更关心你该观察什么、准备什么、避免什么，而不是一句简单吉凶。"
      />
    </div>

    <SectionBlock
      eyebrow="快速入口"
      title="如果你是第一次进入，可以从这四条路径开始"
      description="首页只做三件事：说明网站定位、给出四个入口、建立“非迷信”的使用认知。"
    >
      <div class="quick-grid">
        <RouterLink v-for="item in quickLinks" :key="item.title" :to="item.to" class="quick-card">
          <strong>{{ item.title }}</strong>
          <p>{{ item.description }}</p>
        </RouterLink>
      </div>
    </SectionBlock>

    <SectionBlock
      eyebrow="八卦认知"
      title="先学会八种基础力量，再去读复杂局势"
      description="八卦是六十四卦的词汇表。先理解自然象与现实含义，再进入完整卦牌。"
    >
      <div class="trigram-grid">
        <RouterLink v-for="item in trigrams" :key="item.id" :to="`/trigrams/${item.id}`" class="trigram-card">
          <div class="trigram-card__top">
            <span class="trigram-card__symbol">{{ item.symbol }}</span>
            <div>
              <strong>{{ item.name }}卦</strong>
              <p>{{ item.nature }} · {{ item.element }}</p>
            </div>
          </div>
          <p class="trigram-card__desc">{{ item.attribute }}：{{ item.humanMeaning }}</p>
        </RouterLink>
      </div>
    </SectionBlock>

    <SectionBlock
      eyebrow="经典卦"
      title="第一次进入六十四卦库，建议先看这六卦"
      description="它们分别覆盖开局、起步困难、等待时机、谨慎行事、风险承压和减法止损。"
    >
      <div class="sample-grid">
        <RouterLink
          v-for="item in featuredHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="sample-card"
        >
          <HexagramGlyph :pattern="item.hexagramSymbol" compact />
          <div>
            <span>第 {{ item.sequenceNo }} 卦</span>
            <strong>{{ item.name }}</strong>
            <p>{{ item.shortDescription }}</p>
          </div>
        </RouterLink>
      </div>
    </SectionBlock>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import HexagramGlyph from '../components/HexagramGlyph.vue';
import SectionBlock from '../components/SectionBlock.vue';
import { fetchHexagrams, fetchTrigrams } from '../api/content';
import type { HexagramSummary, Trigram } from '../types';

const trigrams = ref<Trigram[]>([]);
const hexagrams = ref<HexagramSummary[]>([]);

const quickLinks = [
  { title: '从零学习易经', description: '按四层课程结构把基础一口气补齐。', to: '/learn' },
  { title: '查询六十四卦', description: '直接进入卦牌列表，按结构找卦。', to: '/hexagrams' },
  { title: '在线起卦', description: '把起卦结果当成决策辅助与自我反思工具。', to: '/divination' },
  { title: '查看真实案例', description: '用现实场景理解卦象如何落地。', to: '/cases' }
];

const featuredHexagrams = computed(() =>
  [1, 3, 5, 10, 29, 41]
    .map((id) => hexagrams.value.find((item) => item.id === id))
    .filter((item): item is HexagramSummary => Boolean(item))
);

onMounted(async () => {
  const [trigramData, hexagramData] = await Promise.all([fetchTrigrams(), fetchHexagrams()]);
  trigrams.value = trigramData;
  hexagrams.value = hexagramData;
});
</script>

<style scoped>
.hero {
  padding: 36px;
  display: grid;
  grid-template-columns: minmax(0, 1.25fr) minmax(300px, 0.85fr);
  gap: 28px;
  margin-bottom: 24px;
}

.hero-tag {
  margin: 0 0 14px;
  letter-spacing: 0.18em;
  color: var(--accent);
}

.hero-actions,
.sample-grid,
.quick-grid {
  display: grid;
  gap: 12px;
}

.hero-actions {
  grid-template-columns: repeat(2, minmax(0, 1fr));
  margin-top: 28px;
}

.hero-link,
.quick-card,
.trigram-card,
.sample-card {
  padding: 16px;
  border-radius: 18px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
  border: 1px solid rgba(35, 75, 64, 0.08);
}

.hero-link--primary {
  background: var(--brand);
  color: #f6f0e5;
}

.hero-statement {
  margin-top: 24px;
  padding: 16px 18px;
  border-radius: 18px;
  background: rgba(185, 135, 62, 0.08);
  border: 1px solid rgba(185, 135, 62, 0.18);
}

.hero-panel {
  display: grid;
  gap: 16px;
}

.metric-card {
  border-radius: 20px;
  background: var(--surface-strong);
  border: 1px solid var(--border);
  padding: 20px;
}

.metric-card strong,
.sample-card strong,
.quick-card strong {
  color: var(--brand);
}

.quick-grid,
.sample-grid {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.trigram-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.trigram-card__top,
.sample-card {
  display: flex;
  gap: 14px;
  align-items: center;
}

.trigram-card__symbol {
  font-size: 2.5rem;
}

.intro-grid,
.trigram-grid,
.sample-grid {
  margin-top: 20px;
}

@media (max-width: 1000px) {
  .hero,
  .trigram-grid,
  .quick-grid,
  .sample-grid {
    grid-template-columns: 1fr;
  }

  .hero-actions {
    grid-template-columns: 1fr;
  }
}
</style>
