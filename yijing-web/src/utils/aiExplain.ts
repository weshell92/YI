import hexagramsData from '../../public/data/hexagrams.json';
import type { AiExplainView, Hexagram } from '../types';

const hexagrams: Hexagram[] = hexagramsData;

export const explainByAiLocal = (payload: Record<string, unknown>): AiExplainView => {
  const question = String(payload.question || '');
  const primaryId = payload.primaryHexagramId != null ? Number(payload.primaryHexagramId) : 5;
  const changedId = payload.changedHexagramId != null ? Number(payload.changedHexagramId) : 8;

  const primary = hexagrams.find((item) => item.id === primaryId);
  const changed = hexagrams.find((item) => item.id === changedId);

  const movingLineExplanation =
    '当前重点不在“会不会发生”，而在“哪一个变化点最值得观察”。如果没有明确动爻，就先把问题拆成风险、时机和准备度三部分。';

  return {
    restatedQuestion: `你当前的问题是：${question}`,
    primaryExplanation: primary?.plainExplanation || '请先选择一个卦象来辅助解释。',
    movingLineExplanation,
    changedHexagramExplanation: changed?.plainExplanation || '暂未指定变卦。',
    situationAnalysis: primary?.situationAnalysis || '先明确自己的处境，再进入解释。',
    riskWarnings: [
      '不要把卦象理解成绝对结果，而要理解成处境提醒',
      '重点识别你是否忽略了成本、边界和退路',
      '避免在情绪高点或低点做单点决策'
    ],
    actionSuggestions: [
      '把问题拆成 3 个可验证条件',
      '为每个条件准备一条观察证据',
      '设定下一次复盘时间，避免凭感觉反复摇摆'
    ],
    observationPoints: '继续观察信息是否充分、关键角色是否可靠、当前阶段是该推进还是该等待。',
    reviewSuggestion: '后续回看时，重点记录：你当时依据了哪些事实，又被哪些情绪放大了。'
  };
};
