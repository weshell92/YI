import hexagramsData from '../../public/data/hexagrams.json';
import type { DivinationResult, Hexagram, HexagramSummary } from '../types';

const hexagrams: Hexagram[] = hexagramsData;

const stringHash = (value: string) => {
  let hash = 0;
  for (let i = 0; i < value.length; i++) {
    const char = value.charCodeAt(i);
    hash = (hash << 5) - hash + char;
    hash |= 0;
  }
  return Math.abs(hash);
};

const toSummary = (hexagram: Hexagram): HexagramSummary => ({
  id: hexagram.id,
  sequenceNo: hexagram.sequenceNo,
  name: hexagram.name,
  hexagramSymbol: hexagram.hexagramSymbol,
  hexagramCardTitle: hexagram.hexagramCardTitle,
  structure: hexagram.structure,
  keywords: hexagram.keywords,
  shortDescription: hexagram.shortDescription
});

export const createDivinationResult = (payload: Record<string, unknown>): DivinationResult => {
  const size = hexagrams.length;
  if (size === 0) {
    throw new Error('卦象数据为空');
  }

  const question = String(payload.question || '');
  const method = String(payload.method || 'time');
  const luckyNumber = payload.luckyNumber != null ? Number(payload.luckyNumber) : null;
  const selectedHexagramId = payload.selectedHexagramId != null ? Number(payload.selectedHexagramId) : null;

  let primary: Hexagram;

  if (method === 'manual' && selectedHexagramId != null) {
    const picked = hexagrams.find((item) => item.id === selectedHexagramId);
    primary = picked || hexagrams[0];
  } else if (method === 'number' && luckyNumber != null) {
    primary = hexagrams[Math.abs(luckyNumber) % size];
  } else {
    const base = stringHash(`${question}|${method}`) + (luckyNumber != null ? luckyNumber * 31 : 0);
    primary = hexagrams[base % size];
  }

  const rawMovingLines = Array.isArray(payload.movingLines) ? payload.movingLines.map(Number) : [];
  const movingLines =
    rawMovingLines.length > 0
      ? [...new Set(rawMovingLines)].sort((a, b) => a - b)
      : [stringHash(`${question}|${method}|${primary.id}`) % 6 + 1];

  const changedIndex = (primary.sequenceNo + movingLines[0]) % size;
  const changed = hexagrams[changedIndex];

  const analysis = primary.plainExplanation;
  const realityExplanation = `这个卦更像是在提醒你：${primary.situationAnalysis}`;
  const riskWarnings = [
    '信息是否充分，是否只看到了机会而忽略成本',
    '风险承受能力是否与你当前阶段匹配',
    '是否存在退路、缓冲资金或替代方案'
  ];
  const actionSuggestions = [
    '先确认关键事实，再做下一步决定',
    '把当前判断拆成可验证的问题清单',
    '保留阶段性复盘点，避免一次性押注'
  ];
  const observationPoints =
    '重点继续观察：时机是否成熟、合作对象是否可靠、你是否已经准备好承担变化后的代价。';
  const reviewPrompt = '把这次判断保存下来，后续回看：哪些判断来自事实，哪些只是情绪。';

  return {
    recordId: Date.now(),
    question,
    primaryHexagram: toSummary(primary),
    movingLines,
    changedHexagram: toSummary(changed),
    hexagramAnalysis: analysis,
    realityExplanation,
    riskWarnings,
    actionSuggestions,
    observationPoints,
    reviewPrompt
  };
};
