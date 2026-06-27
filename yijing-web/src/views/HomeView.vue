<template>
  <div class="page-container">
    <section class="hero glass-panel">
      <div class="hero-copy">
        <p class="hero-tag">学习 · 解释 · 判断 · 复盘</p>
        <h1 class="page-title">用现代语言学习《易经》，理解变化，辅助决策。</h1>
        <p class="page-subtitle">
          这里不是一个用来给你“下结论”的算命网站，而是一套帮助你看清处境、辨认风险、选择行动节奏的训练平台。
        </p>
        <div class="hero-actions">
          <RouterLink class="hero-link" to="/learn">从零学习易经</RouterLink>
          <RouterLink class="hero-link" to="/hexagrams">查询六十四卦</RouterLink>
          <RouterLink class="hero-link" to="/divination">在线起卦</RouterLink>
          <RouterLink class="hero-link" to="/cases">查看真实案例</RouterLink>
        </div>
      </div>
      <div class="hero-panel">
        <div class="metric-card">
          <strong>8</strong>
          <span>基础课程主题</span>
        </div>
        <div class="metric-card">
          <strong>{{ trigramCount }}</strong>
          <span>八卦样例资料</span>
        </div>
        <div class="metric-card">
          <strong>{{ hexagramCount }}</strong>
          <span>卦象内容样例</span>
        </div>
      </div>
    </section>

    <div class="section-grid three-col home-grid">
      <SectionBlock
        eyebrow="定位"
        title="先解释，不先判定"
        description="每个卦都被拆成结构、处境、风险和行动建议，让用户先理解，再决定。"
      />
      <SectionBlock
        eyebrow="方法"
        title="先学习，再起卦"
        description="课程、八卦、六十四卦和案例库共同构成基础认知，不让起卦脱离学习。"
      />
      <SectionBlock
        eyebrow="原则"
        title="先反思，再行动"
        description="所有输出都避免绝对预测，把卦象变成问题清单、观察点和复盘提醒。"
      />
    </div>

    <SectionBlock
      eyebrow="学习路径"
      title="网站的第一目标，是降低《易经》的理解门槛"
      description="很多人第一次接触《易经》会觉得难、玄、看不懂，所以首屏不堆古文，而是先把它翻译成现代处境语言。"
    >
      <div class="section-grid two-col">
        <div class="glass-panel feature-card" v-for="item in learnCards" :key="item.title">
          <h3>{{ item.title }}</h3>
          <p>{{ item.description }}</p>
        </div>
      </div>
    </SectionBlock>

    <SectionBlock
      eyebrow="克制声明"
      title="本网站不提供绝对预测，只提供基于《易经》象理的思考辅助"
      description="我们更关心你是否信息充分、风险可控、准备到位，而不是一句简单的吉凶判断。"
    />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import SectionBlock from '../components/SectionBlock.vue';
import { fetchHexagrams, fetchTrigrams } from '../api/content';

const trigramCount = ref(0);
const hexagramCount = ref(0);

const learnCards = [
  { title: '基础结构', description: '从阴阳、爻、八卦到六十四卦，先建立卦象思维。' },
  { title: '资料库查阅', description: '每一卦都按古文、白话、场景、建议四层展开。' },
  { title: '案例训练', description: '用职场、感情、创业等现实场景理解卦象。' },
  { title: '决策辅助', description: '把起卦结果转成观察点、风险清单和行动建议。' }
];

onMounted(async () => {
  trigramCount.value = (await fetchTrigrams()).length;
  hexagramCount.value = (await fetchHexagrams()).length;
});
</script>

<style scoped>
.hero {
  padding: 32px;
  display: grid;
  grid-template-columns: 1.45fr 0.8fr;
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
  color: var(--text-soft);
}

.home-grid {
  margin-bottom: 24px;
}

.feature-card {
  padding: 20px;
}

.feature-card h3 {
  margin-top: 0;
}

@media (max-width: 900px) {
  .hero {
    grid-template-columns: 1fr;
  }
}
</style>

