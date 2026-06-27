<template>
  <div class="page-container">
    <section class="glass-panel detail-card">
      <h1 class="page-title">在线起卦</h1>
      <p class="page-subtitle">这里的起卦结果不会给你绝对结论，而是输出处境、风险、建议和复盘入口。</p>

      <el-form label-position="top" class="divination-form">
        <el-form-item label="你的问题">
          <el-input v-model="form.question" type="textarea" :rows="4" placeholder="例如：我最近想换工作，但担心新公司不稳定，该怎么判断？" />
        </el-form-item>
        <div class="section-grid three-col">
          <el-form-item label="起卦方式">
            <el-select v-model="form.method">
              <el-option label="时间起卦" value="time" />
              <el-option label="数字起卦" value="number" />
              <el-option label="手动选卦" value="manual" />
            </el-select>
          </el-form-item>
          <el-form-item label="数字（可选）" v-if="form.method === 'number'">
            <el-input-number v-model="form.luckyNumber" :min="1" :max="999" />
          </el-form-item>
          <el-form-item label="手动选卦" v-if="form.method === 'manual'">
            <el-select v-model="form.selectedHexagramId">
              <el-option v-for="item in hexagrams" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
        </div>
        <el-button type="primary" @click="submit">生成分析</el-button>
      </el-form>
    </section>

    <section class="glass-panel detail-card" v-if="result">
      <h2>结果</h2>
      <div class="detail-list">
        <span><strong>本卦</strong>{{ result.primaryHexagram.name }}</span>
        <span><strong>变爻</strong>{{ result.movingLines.join('、') }}</span>
        <span><strong>变卦</strong>{{ result.changedHexagram.name }}</span>
      </div>
      <p><strong>卦象分析：</strong>{{ result.hexagramAnalysis }}</p>
      <p><strong>现实解释：</strong>{{ result.realityExplanation }}</p>
      <p><strong>继续观察：</strong>{{ result.observationPoints }}</p>
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
      <p class="review-note"><strong>复盘提醒：</strong>{{ result.reviewPrompt }}</p>
    </section>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { createDivination, fetchHexagrams } from '../api/content';
import type { DivinationResult, HexagramSummary } from '../types';

const hexagrams = ref<HexagramSummary[]>([]);
const result = ref<DivinationResult | null>(null);

const form = reactive({
  question: '',
  method: 'time',
  luckyNumber: 8,
  selectedHexagramId: undefined as number | undefined
});

onMounted(async () => {
  hexagrams.value = await fetchHexagrams();
});

const submit = async () => {
  if (!form.question.trim()) {
    ElMessage.warning('请先输入你的问题');
    return;
  }
  result.value = await createDivination(form);
};
</script>

<style scoped>
.detail-card,
.inner-card {
  padding: 28px;
}

.divination-form {
  margin-top: 24px;
}

.inner-card ul {
  margin: 0;
  padding-left: 20px;
}

.review-note {
  margin-top: 20px;
}
</style>

