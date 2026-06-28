<template>
  <div class="page-container">
    <section class="hero glass-panel">
      <div class="hero-copy">
        <p class="hero-tag">学习 · 解释 · 判断 · 复盘</p>
        <h1 class="page-title">用现代语言学习《易经》，理解变化，辅助决策。</h1>
        <p class="page-subtitle">
          易经研习系统不是一个替你“算结果”的网站，而是一套帮你理解处境、识别变化、整理风险和做出行动判断的学习平台。
        </p>
        <div class="hero-actions">
          <RouterLink class="hero-link hero-link--primary" to="/learn">从零学习易经</RouterLink>
          <RouterLink class="hero-link" to="/trigrams">先看八卦基础</RouterLink>
          <RouterLink class="hero-link" to="/hexagrams">浏览六十四卦</RouterLink>
          <RouterLink class="hero-link" to="/cases">查看现实案例</RouterLink>
        </div>
        <div class="hero-statement">
          <strong>克制声明</strong>
          <p>本网站不提供绝对预测，只提供基于《易经》象理的思考辅助。</p>
        </div>
      </div>

      <div class="hero-panel">
        <div class="metric-card">
          <strong>8</strong>
          <span>基础八卦认知</span>
          <p>先理解象、性、场景，再进入更复杂的卦象判断。</p>
        </div>
        <div class="metric-card">
          <strong>64</strong>
          <span>全量六十四卦详情</span>
          <p>每一卦都拆成白话解释、处境分析、行动建议和六爻说明。</p>
        </div>
        <div class="metric-card">
          <strong>3 步</strong>
          <span>网站使用路径</span>
          <p>先学基础，再查结构，最后把卦象转成现实问题清单。</p>
        </div>
      </div>
    </section>

    <div class="section-grid three-col home-grid">
      <SectionBlock
        eyebrow="定位"
        title="先解释，不先神化"
        description="每个页面都用现代中文解释卦象，避免把《易经》变成只有结论、没有推理的神秘黑盒。"
      />
      <SectionBlock
        eyebrow="方法"
        title="先学习，再起卦"
        description="网站先帮用户建立八卦和六十四卦的结构感，再进入具体问题分析。"
      />
      <SectionBlock
        eyebrow="目标"
        title="先反思，再行动"
        description="我们更关心你该观察什么、准备什么、避免什么，而不是一句简单吉凶。"
      />
    </div>

    <SectionBlock
      eyebrow="八卦认知"
      title="先学会八种基础力量，再去看复杂局面"
      description="八卦是六十四卦的前置词汇。先理解天、地、雷、风、水、火、山、泽分别代表什么，再看上下卦的组合。"
    >
      <div class="section-grid four-col trigram-grid">
        <RouterLink v-for="item in trigrams" :key="item.id" :to="`/trigrams/${item.id}`" class="glass-panel trigram-card">
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
      eyebrow="经典样例"
      title="这些卦最适合作为第一次进入资料库的入口"
      description="如果你是第一次用这个网站，可以先从几种高频场景卦入手，快速理解“局势 + 变化 + 行动”的阅读方式。"
    >
      <div class="section-grid two-col sample-grid">
        <RouterLink
          v-for="item in featuredHexagrams"
          :key="item.id"
          :to="`/hexagrams/${item.id}`"
          class="glass-panel sample-card"
        >
          <div class="sample-card__head">
            <span>第 {{ item.sequenceNo }} 卦</span>
            <strong>{{ item.name }}</strong>
          </div>
          <p>{{ item.structure }}</p>
          <div class="keyword-row">
            <span v-for="keyword in item.keywords" :key="keyword">{{ keyword }}</span>
          </div>
          <p class="sample-card__desc">{{ item.shortDescription }}</p>
        </RouterLink>
      </div>
    </SectionBlock>

    <SectionBlock
      eyebrow="使用方式"
      title="把卦象当作处境地图，而不是当作命运判词"
      description="真正有价值的用法，不是输入一个问题就等一个答案，而是把卦象转成结构化思考。"
    >
      <div class="section-grid three-col method-grid">
        <div class="glass-panel method-card" v-for="item in methodCards" :key="item.title">
          <h3>{{ item.title }}</h3>
          <p>{{ item.description }}</p>
        </div>
      </div>
    </SectionBlock>

    <SectionBlock
      eyebrow="产品原则"
      title="这四句话，是整个网站的底层设计原则"
    >
      <div class="section-grid two-col principle-grid">
        <div class="glass-panel principle-card" v-for="item in principles" :key="item.title">
          <h3>{{ item.title }}</h3>
          <p>{{ item.description }}</p>
        </div>
      </div>
    </SectionBlock>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import SectionBlock from '../components/SectionBlock.vue';
import { fetchHexagrams, fetchTrigrams } from '../api/content';
import type { HexagramSummary, Trigram } from '../types';

const trigrams = ref<Trigram[]>([]);
const hexagrams = ref<HexagramSummary[]>([]);

const methodCards = [
  {
    title: '先看自己处在哪一类阶段',
    description: '是开局期、等待期、高压期、转折期，还是该停下来做减法的时期？'
  },
  {
    title: '再看局势里最重要的变化点',
    description: '本卦看当下，变卦看趋势，六爻看变化发生在什么层面。'
  },
  {
    title: '最后把它转成行动问题',
    description: '该进、该退、该守、该等、该补什么信息、该控制什么风险。'
  }
];

const principles = [
  {
    title: '先学习，后起卦',
    description: '先有结构感，后看具体问题，避免一上来只追求结论。'
  },
  {
    title: '先解释，后判断',
    description: '先看这个卦在讲什么处境，再谈这件事该怎么做。'
  },
  {
    title: '先反思，后行动',
    description: '每一个判断都要能落成风险清单、准备清单和复盘提醒。'
  },
  {
    title: '先克制，后扩展',
    description: '不把《易经》做成神秘化工具，而是把它做成能长期使用的判断训练系统。'
  }
];

const featuredHexagrams = computed(() => {
  const order = [1, 3, 5, 10, 29, 41];
  return order
    .map((id) => hexagrams.value.find((item) => item.id === id))
    .filter((item): item is HexagramSummary => Boolean(item));
});

onMounted(async () => {
  trigrams.value = await fetchTrigrams();
  hexagrams.value = await fetchHexagrams();
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

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 28px;
}

.hero-link {
  padding: 12px 16px;
  border-radius: 16px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
}

.hero-link--primary {
  background: var(--brand);
  color: #f6f0e5;
}

.hero-statement {
  margin-top: 28px;
  padding: 16px 18px;
  border-radius: 18px;
  background: rgba(185, 135, 62, 0.08);
  border: 1px solid rgba(185, 135, 62, 0.18);
}

.hero-statement strong {
  color: var(--brand);
}

.hero-statement p {
  margin: 8px 0 0;
  color: var(--text-soft);
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

.metric-card strong {
  display: block;
  font-size: 2rem;
  color: var(--brand);
}

.metric-card span {
  display: block;
  margin-top: 6px;
  color: var(--brand-soft);
  font-weight: 600;
}

.metric-card p {
  margin: 10px 0 0;
  color: var(--text-soft);
  line-height: 1.7;
}

.home-grid,
.sample-grid,
.method-grid,
.principle-grid {
  margin-top: 20px;
}

.trigram-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.trigram-card,
.sample-card,
.method-card,
.principle-card {
  padding: 20px;
  display: block;
}

.trigram-card__top {
  display: flex;
  align-items: center;
  gap: 14px;
}

.trigram-card__symbol {
  font-size: 2.6rem;
  color: var(--brand);
}

.trigram-card__top p {
  margin: 4px 0 0;
  color: var(--text-soft);
}

.trigram-card__desc {
  margin: 16px 0 0;
  color: var(--text-soft);
  line-height: 1.7;
}

.sample-card__head {
  display: flex;
  justify-content: space-between;
  gap: 16px;
}

.sample-card__head span {
  color: var(--accent);
}

.sample-card__desc {
  color: var(--text-soft);
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

.method-card h3,
.principle-card h3 {
  margin-top: 0;
}

.method-card p,
.principle-card p {
  margin-bottom: 0;
  color: var(--text-soft);
  line-height: 1.8;
}

@media (max-width: 1000px) {
  .hero {
    grid-template-columns: 1fr;
  }

  .trigram-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 700px) {
  .trigram-grid {
    grid-template-columns: 1fr;
  }
}
</style>
