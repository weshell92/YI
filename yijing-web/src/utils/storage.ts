import initialHexagrams from '../../public/data/hexagrams.json';
import initialCases from '../../public/data/cases.json';
import initialCourses from '../../public/data/courses.json';
import initialPrompts from '../../public/data/prompts.json';
import type {
  CaseStudy,
  CourseLesson,
  DivinationRecord,
  Hexagram,
  AiPromptTemplate
} from '../types';

const RECORDS_KEY = 'yijing-records';
const ADMIN_HEXAGRAMS_KEY = 'yijing-admin-hexagrams';
const ADMIN_CASES_KEY = 'yijing-admin-cases';
const ADMIN_COURSES_KEY = 'yijing-admin-courses';
const ADMIN_PROMPTS_KEY = 'yijing-admin-prompts';

const read = <T>(key: string, fallback: T): T => {
  try {
    const raw = localStorage.getItem(key);
    return raw ? (JSON.parse(raw) as T) : fallback;
  } catch {
    return fallback;
  }
};

const write = <T>(key: string, value: T) => {
  localStorage.setItem(key, JSON.stringify(value));
};

// ========== 起卦记录 ==========

export const getRecords = (): DivinationRecord[] => {
  return read<DivinationRecord[]>(RECORDS_KEY, []).sort(
    (a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
  );
};

export const saveRecord = (record: Omit<DivinationRecord, 'id' | 'createdAt'>): DivinationRecord => {
  const records = getRecords();
  const newRecord: DivinationRecord = {
    ...record,
    id: Date.now(),
    createdAt: new Date().toISOString()
  };
  records.unshift(newRecord);
  write(RECORDS_KEY, records);
  return newRecord;
};

export const saveReview = (id: number, reviewResult: string): DivinationRecord | null => {
  const records = getRecords();
  const record = records.find((item) => item.id === id);
  if (!record) return null;
  record.reviewResult = reviewResult;
  write(RECORDS_KEY, records);
  return record;
};

// ========== 后台管理：卦象 ==========

export const adminGetHexagrams = (): Hexagram[] =>
  read<Hexagram[]>(ADMIN_HEXAGRAMS_KEY, initialHexagrams as Hexagram[]);

export const adminCreateHexagram = (payload: Partial<Hexagram>): Hexagram => {
  const list = adminGetHexagrams();
  const nextId = Math.max(0, ...list.map((item) => item.id)) + 1;
  const created: Hexagram = {
    ...(initialHexagrams[0] as Hexagram),
    ...payload,
    id: nextId
  } as Hexagram;
  list.push(created);
  write(ADMIN_HEXAGRAMS_KEY, list);
  return created;
};

export const adminUpdateHexagram = (id: number, payload: Partial<Hexagram>): Hexagram | null => {
  const list = adminGetHexagrams();
  const index = list.findIndex((item) => item.id === id);
  if (index === -1) return null;
  list[index] = { ...list[index], ...payload };
  write(ADMIN_HEXAGRAMS_KEY, list);
  return list[index];
};

export const adminDeleteHexagram = (id: number): boolean => {
  const list = adminGetHexagrams();
  const filtered = list.filter((item) => item.id !== id);
  if (filtered.length === list.length) return false;
  write(ADMIN_HEXAGRAMS_KEY, filtered);
  return true;
};

// ========== 后台管理：案例 ==========

export const adminGetCases = (): CaseStudy[] =>
  read<CaseStudy[]>(ADMIN_CASES_KEY, initialCases as CaseStudy[]);

export const adminCreateCase = (payload: Partial<CaseStudy>): CaseStudy => {
  const list = adminGetCases();
  const nextId = Math.max(0, ...list.map((item) => item.id)) + 1;
  const created: CaseStudy = {
    ...(initialCases[0] as CaseStudy),
    ...payload,
    id: nextId
  } as CaseStudy;
  list.push(created);
  write(ADMIN_CASES_KEY, list);
  return created;
};

export const adminUpdateCase = (id: number, payload: Partial<CaseStudy>): CaseStudy | null => {
  const list = adminGetCases();
  const index = list.findIndex((item) => item.id === id);
  if (index === -1) return null;
  list[index] = { ...list[index], ...payload };
  write(ADMIN_CASES_KEY, list);
  return list[index];
};

export const adminDeleteCase = (id: number): boolean => {
  const list = adminGetCases();
  const filtered = list.filter((item) => item.id !== id);
  if (filtered.length === list.length) return false;
  write(ADMIN_CASES_KEY, filtered);
  return true;
};

// ========== 后台管理：课程 ==========

export const adminGetCourses = (): CourseLesson[] =>
  read<CourseLesson[]>(ADMIN_COURSES_KEY, initialCourses as CourseLesson[]);

export const adminCreateCourse = (payload: Partial<CourseLesson>): CourseLesson => {
  const list = adminGetCourses();
  const nextId = Math.max(0, ...list.map((item) => item.id)) + 1;
  const created: CourseLesson = {
    ...(initialCourses[0] as CourseLesson),
    ...payload,
    id: nextId
  } as CourseLesson;
  list.push(created);
  write(ADMIN_COURSES_KEY, list);
  return created;
};

export const adminUpdateCourse = (id: number, payload: Partial<CourseLesson>): CourseLesson | null => {
  const list = adminGetCourses();
  const index = list.findIndex((item) => item.id === id);
  if (index === -1) return null;
  list[index] = { ...list[index], ...payload };
  write(ADMIN_COURSES_KEY, list);
  return list[index];
};

export const adminDeleteCourse = (id: number): boolean => {
  const list = adminGetCourses();
  const filtered = list.filter((item) => item.id !== id);
  if (filtered.length === list.length) return false;
  write(ADMIN_COURSES_KEY, filtered);
  return true;
};

// ========== 后台管理：提示词 ==========

export const adminGetPrompts = (): AiPromptTemplate[] =>
  read<AiPromptTemplate[]>(ADMIN_PROMPTS_KEY, initialPrompts as AiPromptTemplate[]);

export const adminCreatePrompt = (payload: Partial<AiPromptTemplate>): AiPromptTemplate => {
  const list = adminGetPrompts();
  const nextId = Math.max(0, ...list.map((item) => item.id)) + 1;
  const created: AiPromptTemplate = {
    ...(initialPrompts[0] as AiPromptTemplate),
    ...payload,
    id: nextId
  } as AiPromptTemplate;
  list.push(created);
  write(ADMIN_PROMPTS_KEY, list);
  return created;
};

export const adminUpdatePrompt = (id: number, payload: Partial<AiPromptTemplate>): AiPromptTemplate | null => {
  const list = adminGetPrompts();
  const index = list.findIndex((item) => item.id === id);
  if (index === -1) return null;
  list[index] = { ...list[index], ...payload };
  write(ADMIN_PROMPTS_KEY, list);
  return list[index];
};

export const adminDeletePrompt = (id: number): boolean => {
  const list = adminGetPrompts();
  const filtered = list.filter((item) => item.id !== id);
  if (filtered.length === list.length) return false;
  write(ADMIN_PROMPTS_KEY, filtered);
  return true;
};
