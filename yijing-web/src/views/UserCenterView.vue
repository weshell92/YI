<template>
  <div class="page-container">
    <section class="glass-panel detail-card">
      <h1 class="page-title">用户中心</h1>
      <p class="page-subtitle">第二阶段的用户系统将承载收藏、笔记、学习进度与复盘。当前先开放起卦记录与复盘入口。</p>
    </section>

    <section class="glass-panel detail-card">
      <h2>我的起卦记录</h2>
      <div v-if="records.length" class="record-grid">
        <article v-for="record in records" :key="record.id" class="record-card">
          <strong>{{ record.question }}</strong>
          <p>{{ record.analysis }}</p>
          <p class="record-meta">方法：{{ record.method }} · 时间：{{ record.createdAt }}</p>
          <el-input
            v-model="reviews[record.id]"
            type="textarea"
            :rows="3"
            placeholder="补一条复盘，记录后来发生了什么。"
          />
          <el-button size="small" @click="submitReview(record.id)">保存复盘</el-button>
          <p v-if="record.reviewResult"><strong>已保存复盘：</strong>{{ record.reviewResult }}</p>
        </article>
      </div>
      <el-empty v-else description="你还没有起卦记录，先去做一次问题分析吧。" />
    </section>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import type { DivinationRecord } from '../types';
import { fetchDivinationRecords, submitDivinationReview } from '../api/content';

const records = ref<DivinationRecord[]>([]);
const reviews = reactive<Record<number, string>>({});

const loadRecords = async () => {
  records.value = await fetchDivinationRecords();
};

const submitReview = async (id: number) => {
  if (!reviews[id]?.trim()) {
    ElMessage.warning('请先输入复盘内容');
    return;
  }
  await submitDivinationReview(id, reviews[id]);
  ElMessage.success('复盘已保存');
  await loadRecords();
};

onMounted(loadRecords);
</script>

<style scoped>
.detail-card {
  padding: 28px;
}

.record-grid {
  display: grid;
  gap: 16px;
}

.record-card {
  border: 1px solid var(--border);
  border-radius: 20px;
  padding: 18px;
  background: rgba(255, 252, 246, 0.72);
}

.record-meta {
  color: var(--text-soft);
}
</style>

