<template>
  <div class="page-container">
    <header class="glass-panel hero">
      <div class="hero-copy">
        <p class="eyebrow">Learning Path</p>
        <h1 class="page-title">从零开始学《易经》</h1>
        <p class="page-subtitle">
          这一页不再只是 8 张课程卡，而是按“入门导读、基础结构、八卦认知、六十四卦阅读方法”四层往下展开。
          你可以连续阅读，也可以直接跳到相关八卦与六十四卦详情。
        </p>
      </div>

      <div class="path-panel">
        <div v-for="stage in stageSummaries" :key="stage.name" class="path-item">
          <strong>{{ stage.name }}</strong>
          <p>{{ stage.description }}</p>
        </div>
      </div>
    </header>

    <section class="section-grid two-col support-grid">
      <div class="glass-panel support-card">
        <h2>术语索引</h2>
        <div class="glossary-grid">
          <article v-for="item in glossary" :key="item.term" class="glossary-item">
            <strong>{{ item.term }}</strong>
            <p>{{ item.explanation }}</p>
          </article>
        </div>
      </div>

      <div class="glass-panel support-card">
        <h2>阅读顺序建议</h2>
        <ol class="reading-steps">
          <li>先读“入门导读”，确认《易经》是结构化思考工具，而不是替你拍板的神谕。</li>
          <li>再读“基础结构”，把阴阳、爻和动爻理解成分析问题的最小单位。</li>
          <li>进入“八卦认知”，把八卦当成六十四卦的词汇表。</li>
          <li>最后再进入六十四卦详情，结合卦辞、十翼和六爻做完整阅读。</li>
        </ol>
      </div>
    </section>

    <section
      v-for="stage in groupedStages"
      :key="stage.name"
      class="glass-panel stage-section"
    >
      <div class="stage-head">
        <div>
          <p class="eyebrow">{{ stage.name }}</p>
          <h2>{{ stage.title }}</h2>
        </div>
        <p>{{ stage.description }}</p>
      </div>

      <div class="lesson-stack">
        <details
          v-for="lesson in stage.lessons"
          :key="lesson.id"
          class="lesson-block"
          open
        >
          <summary>
            <span class="lesson-order">第 {{ lesson.orderNo }} 课</span>
            <div>
              <strong>{{ lesson.title }}</strong>
              <p>{{ lesson.overview }}</p>
            </div>
          </summary>

          <div class="lesson-body">
            <div class="section-grid two-col lesson-grid">
              <article class="lesson-card">
                <h3>这一章学什么</h3>
                <p>{{ lesson.overview }}</p>
              </article>

              <article class="lesson-card">
                <h3>核心概念</h3>
                <p>{{ lesson.coreConcept }}</p>
              </article>
            </div>

            <div class="section-grid two-col lesson-grid">
              <article class="lesson-card">
                <h3>术语解释</h3>
                <ul class="bullet-list">
                  <li v-for="item in lesson.termNotes" :key="item">{{ item }}</li>
                </ul>
              </article>

              <article class="lesson-card">
                <h3>结构图示</h3>
                <div class="diagram-card">{{ lesson.diagram }}</div>
                <p class="example-text">{{ lesson.example }}</p>
              </article>
            </div>

            <article class="lesson-card lesson-card--full">
              <h3>详细解释</h3>
              <p v-for="paragraph in lesson.bodyParagraphs" :key="paragraph">{{ paragraph }}</p>
            </article>

            <div class="section-grid two-col lesson-grid">
              <article class="lesson-card">
                <h3>现实类比</h3>
                <p>{{ lesson.realWorldAnalogy }}</p>
              </article>

              <article class="lesson-card">
                <h3>常见误区</h3>
                <ul class="bullet-list">
                  <li v-for="item in lesson.commonMistakes" :key="item">{{ item }}</li>
                </ul>
              </article>
            </div>

            <div class="section-grid two-col lesson-grid">
              <article class="lesson-card">
                <h3>继续阅读入口</h3>
                <ul class="bullet-list">
                  <li v-for="item in lesson.followUpLinks" :key="item">{{ item }}</li>
                </ul>
              </article>

              <article class="lesson-card">
                <h3>课后小测</h3>
                <ul class="bullet-list">
                  <li v-for="question in lesson.quizQuestions" :key="question">{{ question }}</li>
                </ul>
              </article>
            </div>

            <div class="section-grid two-col lesson-grid">
              <article class="lesson-card">
                <h3>跳转到八卦详情</h3>
                <div class="link-row">
                  <RouterLink
                    v-for="item in relatedTrigrams(lesson)"
                    :key="item.id"
                    :to="`/trigrams/${item.id}`"
                    class="jump-link"
                  >
                    {{ item.name }}卦
                  </RouterLink>
                  <span v-if="!relatedTrigrams(lesson).length" class="empty-text">本章暂不直接关联单个八卦。</span>
                </div>
              </article>

              <article class="lesson-card">
                <h3>跳转到六十四卦详情</h3>
                <div class="link-row">
                  <RouterLink
                    v-for="item in relatedHexagrams(lesson)"
                    :key="item.id"
                    :to="`/hexagrams/${item.id}`"
                    class="jump-link"
                  >
                    {{ item.hexagramCardTitle }}
                  </RouterLink>
                  <span v-if="!relatedHexagrams(lesson).length" class="empty-text">本章以结构学习为主，还未指定案例卦。</span>
                </div>
              </article>
            </div>
          </div>
        </details>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import { fetchCourses, fetchHexagrams, fetchTrigrams } from '../api/content';
import type { CourseLesson, HexagramSummary, Trigram } from '../types';

const courses = ref<CourseLesson[]>([]);
const trigrams = ref<Trigram[]>([]);
const hexagrams = ref<HexagramSummary[]>([]);

const stageOrder = ['入门导读', '基础结构', '八卦认知', '六十四卦阅读方法'];
const stageMeta: Record<string, { title: string; description: string }> = {
  入门导读: {
    title: '先校准认知，再进入卦象',
    description: '先理解《易经》为什么值得学、应该怎样学，避免一上来就陷入“求结果”的惯性。'
  },
  基础结构: {
    title: '先学最小单位，再看复杂局势',
    description: '把阴阳、爻和动爻吃透，后面读卦就不会只剩背词。'
  },
  八卦认知: {
    title: '把八卦当成六十四卦的词汇表',
    description: '先懂八种基础力量，再看它们如何叠加成完整处境。'
  },
  六十四卦阅读方法: {
    title: '从卦牌头部一路读到六爻与十翼',
    description: '结构、原文、处境、建议、复盘一起读，才会真正形成判断。'
  }
};

const stageSummaries = stageOrder.map((name) => ({
  name,
  description: stageMeta[name].description
}));

const glossary = [
  { term: '卦符', explanation: '用六条阴阳线表示一个卦的结构，是进入详情页前最快的视觉入口。' },
  { term: '本卦', explanation: '代表当前处境的卦，是阅读起点。' },
  { term: '动爻', explanation: '说明变化正发生在哪一层，是从静态局势进入动态判断的关键。' },
  { term: '变卦', explanation: '由动爻变化后得到的新结构，用来看趋势与转向。' },
  { term: '彖传 / 大象 / 小象', explanation: '属于“十翼”系统，是从不同层级解释卦义的重要原文。' },
  { term: '复盘', explanation: '记录当时的理解、后来发生的结果和下一次提醒，帮助长期学习。' }
];

const groupedStages = computed(() =>
  stageOrder
    .map((name) => ({
      name,
      title: stageMeta[name].title,
      description: stageMeta[name].description,
      lessons: courses.value.filter((item) => item.stage === name)
    }))
    .filter((item) => item.lessons.length)
);

const trigramMap = computed(() => new Map(trigrams.value.map((item) => [item.id, item])));
const hexagramMap = computed(() => new Map(hexagrams.value.map((item) => [item.id, item])));

const relatedTrigrams = (lesson: CourseLesson) =>
  lesson.relatedTrigramIds
    .map((id) => trigramMap.value.get(id))
    .filter((item): item is Trigram => Boolean(item));

const relatedHexagrams = (lesson: CourseLesson) =>
  lesson.relatedHexagramIds
    .map((id) => hexagramMap.value.get(id))
    .filter((item): item is HexagramSummary => Boolean(item));

onMounted(async () => {
  const [courseData, trigramData, hexagramData] = await Promise.all([
    fetchCourses(),
    fetchTrigrams(),
    fetchHexagrams()
  ]);
  courses.value = courseData;
  trigrams.value = trigramData;
  hexagrams.value = hexagramData;
});
</script>

<style scoped>
.hero,
.support-card,
.stage-section {
  padding: 30px;
}

.hero {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(280px, 0.8fr);
  gap: 24px;
}

.eyebrow {
  margin: 0 0 12px;
  color: var(--accent);
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.path-panel {
  display: grid;
  gap: 14px;
}

.path-item {
  border-radius: 20px;
  padding: 18px;
  background: var(--surface-strong);
  border: 1px solid var(--border);
}

.path-item strong,
.lesson-card h3,
.stage-head h2 {
  color: var(--brand);
}

.path-item p,
.lesson-card p,
.glossary-item p,
.reading-steps,
.stage-head > p,
.empty-text,
.example-text {
  color: var(--text-soft);
  line-height: 1.8;
}

.support-grid,
.stage-section {
  margin-top: 24px;
}

.glossary-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.glossary-item {
  border-radius: 18px;
  padding: 16px;
  background: rgba(35, 75, 64, 0.05);
}

.reading-steps {
  margin: 0;
  padding-left: 20px;
}

.stage-head {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  align-items: end;
}

.stage-head h2 {
  margin: 0;
  font-family: 'Noto Serif SC', 'Source Han Serif SC', serif;
}

.lesson-stack {
  margin-top: 20px;
  display: grid;
  gap: 16px;
}

.lesson-block {
  border-radius: 22px;
  border: 1px solid var(--border);
  background: rgba(255, 252, 246, 0.78);
  overflow: hidden;
}

.lesson-block summary {
  list-style: none;
  display: grid;
  grid-template-columns: 116px minmax(0, 1fr);
  gap: 16px;
  padding: 20px 22px;
  cursor: pointer;
}

.lesson-block summary::-webkit-details-marker {
  display: none;
}

.lesson-order {
  display: inline-flex;
  align-items: start;
  justify-content: center;
  padding: 10px 12px;
  border-radius: 999px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
  font-weight: 600;
}

.lesson-block summary strong {
  display: block;
  color: var(--brand);
  font-size: 1.15rem;
}

.lesson-block summary p {
  margin: 8px 0 0;
  color: var(--text-soft);
  line-height: 1.7;
}

.lesson-body {
  padding: 0 22px 22px;
}

.lesson-grid {
  margin-top: 16px;
}

.lesson-card {
  border-radius: 18px;
  padding: 18px;
  background: var(--surface-strong);
  border: 1px solid rgba(35, 75, 64, 0.08);
}

.lesson-card--full p + p {
  margin-top: 10px;
}

.diagram-card {
  margin-bottom: 10px;
  padding: 14px 16px;
  border-radius: 16px;
  background: rgba(35, 75, 64, 0.08);
  color: var(--brand);
  font-weight: 600;
}

.bullet-list {
  margin: 0;
  padding-left: 20px;
  color: var(--text-soft);
  line-height: 1.8;
}

.link-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.jump-link {
  display: inline-flex;
  align-items: center;
  padding: 10px 14px;
  border-radius: 999px;
  background: rgba(185, 135, 62, 0.12);
  color: var(--brand);
}

@media (max-width: 960px) {
  .hero {
    grid-template-columns: 1fr;
  }

  .glossary-grid {
    grid-template-columns: 1fr;
  }

  .stage-head,
  .lesson-block summary {
    grid-template-columns: 1fr;
    display: grid;
  }
}
</style>
