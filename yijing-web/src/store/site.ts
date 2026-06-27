import { defineStore } from 'pinia';

export const useSiteStore = defineStore('site', {
  state: () => ({
    title: '易经研习系统',
    tagline: '用现代语言学习《易经》，理解变化，辅助决策。'
  })
});

