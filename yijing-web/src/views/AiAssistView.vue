<template>
  <div class="page-container">
    <section class="glass-panel detail-card">
      <h1 class="page-title">AI 解卦助手</h1>
      <p class="page-subtitle">AI 只做解释、比较、追问和复盘，不做神谕式断语。</p>
      <el-form label-position="top">
        <el-form-item label="你的问题">
          <el-input v-model="question" type="textarea" :rows="4" placeholder="例如：雷火丰卦是不是代表好事？" />
        </el-form-item>
        <div class="section-grid two-col">
          <el-form-item label="本卦">
            <el-select v-model="primaryHexagramId">
              <el-option v-for="item in hexagrams" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="变卦（可选）">
            <el-select v-model="changedHexagramId" clearable>
              <el-option v-for="item in hexagrams" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
        </div>
        <el-button type="primary" @click="submit">生成解释</el-button>
      </el-form>
    </section>

    <section class="glass-panel detail-card" v-if="result">
      <h2>结构化解释</h2>
      <div class="detail-list">
        <span><strong>问题复述</strong>{{ result.restatedQuestion }}</span>
        <span><strong>本卦解释</strong>{{ result.primaryExplanation }}</span>
        <span><strong>动爻解释</strong>{{ result.movingLineExplanation }}</span>
        <span><strong>变卦解释</strong>{{ result.changedHexagramExplanation }}</span>
        <span><strong>处境分析</strong>{{ result.situationAnalysis }}</span>
        <span><strong>继续观察</strong>{{ result.observationPoints }}</span>
        <span><strong>复盘建议</strong>{{ result.reviewSuggestion }}</span>
      </div>
      <div class="section-grid two-col">
        <div class="glass-panel inner-card">
          <h3>风险提醒</h3>
          <ul>
            <li v-for="item in result.riskWarnings" :key="item">{{ item }}</li>
          </ul>
        </div>
        <div class="glass-panel inner-card">
          <h3>行动建议</h3>
          <ul>
            <li v-for="item in result.actionSuggestions" :key="item">{{ item }}</li>
          </ul>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { explainByAi, fetchHexagrams } from '../api/content';
import type { AiExplainView, HexagramSummary } from '../types';

const question = ref('');
const primaryHexagramId = ref<number | undefined>(5);
const changedHexagramId = ref<number | undefined>(8);
const hexagrams = ref<HexagramSummary[]>([]);
const result = ref<AiExplainView | null>(null);

onMounted(async () => {
  hexagrams.value = await fetchHexagrams();
});

const submit = async () => {
  if (!question.value.trim()) {
    ElMessage.warning('请先输入一个问题');
    return;
  }
  result.value = await explainByAi({
    question: question.value,
    primaryHexagramId: primaryHexagramId.value,
    changedHexagramId: changedHexagramId.value
  });
};
</script>

<style scoped>
.detail-card,
.inner-card {
  padding: 28px;
}

.inner-card ul {
  margin: 0;
  padding-left: 20px;
}
</style>

