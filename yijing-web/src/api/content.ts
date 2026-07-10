import type {
  AiExplainView,
  AiPromptTemplate,
  CaseStudy,
  CourseLesson,
  DivinationRecord,
  DivinationResult,
  Hexagram,
  HexagramDetailView,
  HexagramLine,
  HexagramSummary,
  Trigram
} from '../types';

const loadJson = async <T>(name: string): Promise<T> => {
  const base = (import.meta.env.BASE_URL || '/').replace(/\/$/, '');
  const response = await fetch(`${base}/data/${name}.json`);
  if (!response.ok) {
    throw new Error(`Failed to load ${name}.json: ${response.status}`);
  }
  return response.json();
};

let trigramsCache: Trigram[] | null = null;
let hexagramsCache: Hexagram[] | null = null;
let linesCache: HexagramLine[] | null = null;
let casesCache: CaseStudy[] | null = null;
let coursesCache: CourseLesson[] | null = null;
let promptsCache: AiPromptTemplate[] | null = null;

const loadTrigrams = async () => {
  if (!trigramsCache) trigramsCache = await loadJson<Trigram[]>('trigrams');
  return trigramsCache;
};

const loadHexagrams = async () => {
  if (!hexagramsCache) hexagramsCache = await loadJson<Hexagram[]>('hexagrams');
  return hexagramsCache;
};

const loadLines = async () => {
  if (!linesCache) linesCache = await loadJson<HexagramLine[]>('hexagram-lines');
  return linesCache;
};

const loadCases = async () => {
  if (!casesCache) casesCache = await loadJson<CaseStudy[]>('cases');
  return casesCache;
};

const loadCourses = async () => {
  if (!coursesCache) coursesCache = await loadJson<CourseLesson[]>('courses');
  return coursesCache;
};

const loadPrompts = async () => {
  if (!promptsCache) promptsCache = await loadJson<AiPromptTemplate[]>('prompts');
  return promptsCache;
};

const toHexagramSummary = (item: Hexagram): HexagramSummary => ({
  id: item.id,
  sequenceNo: item.sequenceNo,
  name: item.name,
  hexagramSymbol: item.hexagramSymbol,
  hexagramCardTitle: item.hexagramCardTitle,
  structure: item.structure,
  keywords: item.keywords,
  shortDescription: item.shortDescription
});

export const fetchTrigrams = async () => loadTrigrams();

export const fetchTrigram = async (id: string | number) => {
  const list = await loadTrigrams();
  return list.find((item) => String(item.id) === String(id)) || null;
};

export const fetchHexagrams = async (q = '') => {
  const list = await loadHexagrams();
  const query = q.trim().toLowerCase();
  const filtered = query
    ? list.filter((item) => {
        const source = [
          item.name,
          item.hexagramCardTitle,
          item.structure,
          item.shortDescription,
          ...item.keywords
        ]
          .join(' ')
          .toLowerCase();
        return source.includes(query);
      })
    : list;
  return filtered.map(toHexagramSummary);
};

export const fetchHexagram = async (id: string | number): Promise<HexagramDetailView | null> => {
  const [hexagrams, trigrams, lines] = await Promise.all([
    loadHexagrams(),
    loadTrigrams(),
    loadLines()
  ]);
  const hexagram = hexagrams.find((item) => String(item.id) === String(id));
  if (!hexagram) return null;

  const upperTrigram = trigrams.find((item) => item.id === hexagram.upperTrigramId);
  const lowerTrigram = trigrams.find((item) => item.id === hexagram.lowerTrigramId);
  const hexagramLines = lines
    .filter((item) => item.hexagramId === hexagram.id)
    .sort((a, b) => a.lineNo - b.lineNo);
  const relatedHexagrams = (hexagram.relatedHexagramIds || [])
    .map((rid) => hexagrams.find((item) => item.id === rid))
    .filter((item): item is Hexagram => Boolean(item))
    .map(toHexagramSummary);

  return {
    hexagram,
    upperTrigram: upperTrigram || ({} as Trigram),
    lowerTrigram: lowerTrigram || ({} as Trigram),
    lines: hexagramLines,
    relatedHexagrams
  };
};

export const fetchCases = async (category = '') => {
  const list = await loadCases();
  if (!category) return list;
  return list.filter((item) => item.category === category);
};

export const fetchCase = async (id: string | number) => {
  const list = await loadCases();
  return list.find((item) => String(item.id) === String(id)) || null;
};

export const fetchCourses = async () => loadCourses();

export const fetchCourse = async (id: string | number) => {
  const list = await loadCourses();
  return list.find((item) => String(item.id) === String(id)) || null;
};

// 起卦与 AI 解卦改走本地工具函数，以下接口仅保留签名以兼容旧调用处
export const createDivination = async (payload: Record<string, unknown>) => {
  const [{ createDivinationResult }, { saveRecord }] = await Promise.all([
    import('../utils/divination'),
    import('../utils/storage')
  ]);
  const result = createDivinationResult(payload) as DivinationResult;
  saveRecord({
    userId: 1,
    question: result.question,
    method: String(payload.method || 'time'),
    originalHexagramId: result.primaryHexagram.id,
    changedHexagramId: result.changedHexagram.id,
    movingLines: result.movingLines,
    analysis: result.hexagramAnalysis,
    realityExplanation: result.realityExplanation,
    riskWarnings: result.riskWarnings,
    actionSuggestions: result.actionSuggestions,
    observationPoints: result.observationPoints,
    reviewResult: ''
  });
  return result;
};

export const explainByAi = async (payload: Record<string, unknown>) => {
  const { explainByAiLocal } = await import('../utils/aiExplain');
  return explainByAiLocal(payload) as AiExplainView;
};

// 起卦记录与复盘改走 localStorage
export const fetchDivinationRecords = async () => {
  const { getRecords } = await import('../utils/storage');
  return getRecords();
};

export const submitDivinationReview = async (id: number, reviewResult: string) => {
  const { saveReview } = await import('../utils/storage');
  return saveReview(id, reviewResult);
};

// 后台管理本地演示：走 localStorage
export const adminFetchHexagrams = async () => fetchHexagrams();
export const adminCreateHexagram = async (payload: Partial<Hexagram>) => {
  const { adminCreateHexagram: create } = await import('../utils/storage');
  return create(payload);
};
export const adminUpdateHexagram = async (id: number, payload: Partial<Hexagram>) => {
  const { adminUpdateHexagram: update } = await import('../utils/storage');
  return update(id, payload);
};
export const adminDeleteHexagram = async (id: number) => {
  const { adminDeleteHexagram: remove } = await import('../utils/storage');
  return remove(id);
};

export const adminFetchCases = async () => loadCases();
export const adminCreateCase = async (payload: Partial<CaseStudy>) => {
  const { adminCreateCase: create } = await import('../utils/storage');
  return create(payload);
};
export const adminUpdateCase = async (id: number, payload: Partial<CaseStudy>) => {
  const { adminUpdateCase: update } = await import('../utils/storage');
  return update(id, payload);
};
export const adminDeleteCase = async (id: number) => {
  const { adminDeleteCase: remove } = await import('../utils/storage');
  return remove(id);
};

export const adminFetchCourses = async () => {
  const { adminGetCourses } = await import('../utils/storage');
  return adminGetCourses();
};
export const adminCreateCourse = async (payload: Partial<CourseLesson>) => {
  const { adminCreateCourse: create } = await import('../utils/storage');
  return create(payload);
};
export const adminUpdateCourse = async (id: number, payload: Partial<CourseLesson>) => {
  const { adminUpdateCourse: update } = await import('../utils/storage');
  return update(id, payload);
};
export const adminDeleteCourse = async (id: number) => {
  const { adminDeleteCourse: remove } = await import('../utils/storage');
  return remove(id);
};

export const adminFetchPrompts = async () => {
  const { adminGetPrompts } = await import('../utils/storage');
  return adminGetPrompts();
};
export const adminCreatePrompt = async (payload: Partial<AiPromptTemplate>) => {
  const { adminCreatePrompt: create } = await import('../utils/storage');
  return create(payload);
};
export const adminUpdatePrompt = async (id: number, payload: Partial<AiPromptTemplate>) => {
  const { adminUpdatePrompt: update } = await import('../utils/storage');
  return update(id, payload);
};
export const adminDeletePrompt = async (id: number) => {
  const { adminDeletePrompt: remove } = await import('../utils/storage');
  return remove(id);
};
