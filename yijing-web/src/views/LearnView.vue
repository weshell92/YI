<template>
  <div class="page-container">
    <header class="glass-panel page-header">
      <p class="eyebrow">Learning Path</p>
      <h1 class="page-title">从零开始学《易经》</h1>
      <p class="page-subtitle">
        课程模块采用“结构优先”的方式设计：先理解阴阳、爻和八卦，再进入六十四卦、现实分析与判断练习。
      </p>
    </header>

    <div class="section-grid two-col course-grid">
      <section v-for="course in courses" :key="course.id" class="glass-panel course-card">
        <p class="course-order">第 {{ course.orderNo }} 课</p>
        <h2>{{ course.title }}</h2>
        <p class="course-core">{{ course.coreConcept }}</p>
        <p>{{ course.plainExplanation }}</p>
        <div class="course-diagram">{{ course.diagram }}</div>
        <p class="course-example">{{ course.example }}</p>
        <div class="quiz-list">
          <strong>课后自测</strong>
          <ul>
            <li v-for="question in course.quizQuestions" :key="question">{{ question }}</li>
          </ul>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { CourseLesson } from '../types';
import { fetchCourses } from '../api/content';

const courses = ref<CourseLesson[]>([]);

onMounted(async () => {
  courses.value = await fetchCourses();
});
</script>

<style scoped>
.page-header,
.course-card {
  padding: 28px;
}

.eyebrow,
.course-order {
  color: var(--accent);
  letter-spacing: 0.16em;
  text-transform: uppercase;
}

.course-grid {
  margin-top: 24px;
}

.course-core {
  color: var(--brand);
  font-weight: 600;
}

.course-diagram {
  margin: 18px 0;
  padding: 14px 16px;
  border-radius: 16px;
  background: rgba(35, 75, 64, 0.06);
  color: var(--brand);
}

.quiz-list ul {
  margin: 8px 0 0;
  padding-left: 20px;
  color: var(--text-soft);
}
</style>

