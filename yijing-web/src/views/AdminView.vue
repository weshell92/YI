<template>
  <div class="page-container">
    <section class="glass-panel detail-card">
      <h1 class="page-title">后台内容管理</h1>
      <p class="page-subtitle">首版后台聚焦内容维护，不提前引入复杂权限、审批或社区逻辑。</p>
    </section>

    <el-tabs class="glass-panel tabs-panel" v-model="activeTab">
      <el-tab-pane label="卦象管理" name="hexagrams">
        <div class="admin-toolbar">
          <el-input v-model="hexagramForm.name" placeholder="卦名" />
          <el-input-number v-model="hexagramForm.sequenceNo" :min="1" :max="64" />
          <el-input v-model="hexagramForm.structure" placeholder="结构，如坎上乾下" />
          <el-button type="primary" @click="createHexagram">新增卦象</el-button>
        </div>
        <el-table :data="adminHexagrams">
          <el-table-column prop="sequenceNo" label="序号" width="80" />
          <el-table-column prop="name" label="卦名" />
          <el-table-column prop="structure" label="结构" />
          <el-table-column label="操作" width="120">
            <template #default="{ row }">
              <el-button text type="danger" @click="removeHexagram(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="案例管理" name="cases">
        <div class="admin-toolbar">
          <el-input v-model="caseForm.title" placeholder="案例标题" />
          <el-input v-model="caseForm.category" placeholder="分类" />
          <el-button type="primary" @click="createCaseItem">新增案例</el-button>
        </div>
        <el-table :data="adminCases">
          <el-table-column prop="title" label="标题" />
          <el-table-column prop="category" label="分类" width="120" />
          <el-table-column label="操作" width="120">
            <template #default="{ row }">
              <el-button text type="danger" @click="removeCaseItem(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="课程管理" name="courses">
        <div class="admin-toolbar">
          <el-input v-model="courseForm.title" placeholder="课程标题" />
          <el-input-number v-model="courseForm.orderNo" :min="1" :max="99" />
          <el-button type="primary" @click="createCourseItem">新增课程</el-button>
        </div>
        <el-table :data="courses">
          <el-table-column prop="orderNo" label="排序" width="80" />
          <el-table-column prop="title" label="标题" />
          <el-table-column prop="coreConcept" label="核心概念" />
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="AI 提示词" name="prompts">
        <div class="admin-toolbar">
          <el-input v-model="promptForm.name" placeholder="提示词名称" />
          <el-input v-model="promptForm.purpose" placeholder="用途" />
          <el-button type="primary" @click="createPromptItem">新增提示词</el-button>
        </div>
        <el-table :data="prompts">
          <el-table-column prop="name" label="名称" />
          <el-table-column prop="purpose" label="用途" />
          <el-table-column prop="content" label="内容" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import {
  adminCreateCase,
  adminCreateCourse,
  adminCreateHexagram,
  adminCreatePrompt,
  adminDeleteCase,
  adminDeleteHexagram,
  adminFetchCases,
  adminFetchCourses,
  adminFetchHexagrams,
  adminFetchPrompts
} from '../api/content';
import type { AiPromptTemplate, CaseStudy, CourseLesson, HexagramSummary } from '../types';

const activeTab = ref('hexagrams');
const adminHexagrams = ref<HexagramSummary[]>([]);
const adminCases = ref<CaseStudy[]>([]);
const courses = ref<CourseLesson[]>([]);
const prompts = ref<AiPromptTemplate[]>([]);

const hexagramForm = reactive({
  sequenceNo: 12,
  name: '',
  structure: '',
  keywords: ['待补充'],
  shortDescription: '请在后台继续补充完整字段。'
});

const caseForm = reactive({
  title: '',
  category: '',
  background: '请补充案例背景。',
  hexagramId: 1,
  analysis: '请补充卦象分析。',
  modernAnalysis: '请补充现代解释。',
  advice: '请补充行动建议。',
  sortOrder: 99,
  published: true
});

const courseForm = reactive({
  orderNo: 9,
  title: '',
  coreConcept: '请补充核心概念。',
  plainExplanation: '请补充白话解释。',
  diagram: '请补充图示。',
  example: '请补充例子。',
  quizQuestions: ['这一课的关键判断是什么？']
});

const promptForm = reactive({
  name: '',
  purpose: '',
  content: '请补充 AI 提示词内容。',
  enabled: true
});

const loadAll = async () => {
  adminHexagrams.value = await adminFetchHexagrams();
  adminCases.value = await adminFetchCases();
  courses.value = await adminFetchCourses();
  prompts.value = await adminFetchPrompts();
};

const createHexagram = async () => {
  if (!hexagramForm.name.trim()) {
    ElMessage.warning('请输入卦名');
    return;
  }
  await adminCreateHexagram({
    sequenceNo: hexagramForm.sequenceNo,
    name: hexagramForm.name,
    upperTrigramId: 1,
    lowerTrigramId: 2,
    structure: hexagramForm.structure,
    keywords: hexagramForm.keywords,
    shortDescription: hexagramForm.shortDescription,
    guaCi: '',
    tuanCi: '',
    xiangCi: '',
    plainExplanation: '请在后台继续补充完整卦象解释。',
    situationAnalysis: '请补充当前处境分析。',
    advice: '请补充行动建议。',
    careerExplanation: '',
    loveExplanation: '',
    relationExplanation: '',
    businessExplanation: '',
    commonMistakes: '',
    relatedHexagramIds: []
  });
  hexagramForm.name = '';
  await loadAll();
};

const removeHexagram = async (id: number) => {
  await adminDeleteHexagram(id);
  ElMessage.success('已删除卦象');
  await loadAll();
};

const createCaseItem = async () => {
  if (!caseForm.title.trim()) {
    ElMessage.warning('请输入案例标题');
    return;
  }
  await adminCreateCase(caseForm);
  caseForm.title = '';
  caseForm.category = '';
  await loadAll();
};

const removeCaseItem = async (id: number) => {
  await adminDeleteCase(id);
  ElMessage.success('已删除案例');
  await loadAll();
};

const createCourseItem = async () => {
  if (!courseForm.title.trim()) {
    ElMessage.warning('请输入课程标题');
    return;
  }
  await adminCreateCourse(courseForm);
  courseForm.title = '';
  await loadAll();
};

const createPromptItem = async () => {
  if (!promptForm.name.trim()) {
    ElMessage.warning('请输入提示词名称');
    return;
  }
  await adminCreatePrompt(promptForm);
  promptForm.name = '';
  promptForm.purpose = '';
  await loadAll();
};

onMounted(loadAll);
</script>

<style scoped>
.detail-card,
.tabs-panel {
  padding: 28px;
}

.admin-toolbar {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 16px;
}

.admin-toolbar > * {
  max-width: 240px;
}
</style>
