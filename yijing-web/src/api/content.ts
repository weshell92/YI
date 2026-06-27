import client from './client';
import type {
  AiExplainView,
  AiPromptTemplate,
  ApiResponse,
  CaseStudy,
  CourseLesson,
  DivinationRecord,
  DivinationResult,
  Hexagram,
  HexagramDetailView,
  HexagramSummary,
  Trigram
} from '../types';

export const fetchTrigrams = async () => (await client.get<ApiResponse<Trigram[]>>('/api/trigrams')).data.data;
export const fetchTrigram = async (id: string | number) => (await client.get<ApiResponse<Trigram>>(`/api/trigrams/${id}`)).data.data;
export const fetchHexagrams = async (q = '') =>
  (await client.get<ApiResponse<HexagramSummary[]>>('/api/hexagrams', { params: { q } })).data.data;
export const fetchHexagram = async (id: string | number) =>
  (await client.get<ApiResponse<HexagramDetailView>>(`/api/hexagrams/${id}`)).data.data;
export const fetchCases = async (category = '') =>
  (await client.get<ApiResponse<CaseStudy[]>>('/api/cases', { params: { category } })).data.data;
export const fetchCase = async (id: string | number) =>
  (await client.get<ApiResponse<CaseStudy>>(`/api/cases/${id}`)).data.data;
export const fetchCourses = async () =>
  (await client.get<ApiResponse<CourseLesson[]>>('/api/learn/courses')).data.data;
export const fetchCourse = async (id: string | number) =>
  (await client.get<ApiResponse<CourseLesson>>(`/api/learn/courses/${id}`)).data.data;
export const createDivination = async (payload: Record<string, unknown>) =>
  (await client.post<ApiResponse<DivinationResult>>('/api/divination', payload)).data.data;
export const fetchDivinationRecords = async () =>
  (await client.get<ApiResponse<DivinationRecord[]>>('/api/divination/records')).data.data;
export const submitDivinationReview = async (id: number, reviewResult: string) =>
  (await client.post<ApiResponse<DivinationRecord>>(`/api/divination/${id}/review`, { reviewResult })).data.data;
export const explainByAi = async (payload: Record<string, unknown>) =>
  (await client.post<ApiResponse<AiExplainView>>('/api/ai/explain', payload)).data.data;

export const adminFetchHexagrams = async () =>
  (await client.get<ApiResponse<HexagramSummary[]>>('/admin/hexagrams')).data.data;
export const adminCreateHexagram = async (payload: Partial<Hexagram>) =>
  (await client.post<ApiResponse<Hexagram>>('/admin/hexagrams', payload)).data.data;
export const adminUpdateHexagram = async (id: number, payload: Partial<Hexagram>) =>
  (await client.put<ApiResponse<Hexagram>>(`/admin/hexagrams/${id}`, payload)).data.data;
export const adminDeleteHexagram = async (id: number) =>
  (await client.delete<ApiResponse<null>>(`/admin/hexagrams/${id}`)).data.data;

export const adminFetchCases = async () =>
  (await client.get<ApiResponse<CaseStudy[]>>('/admin/cases')).data.data;
export const adminCreateCase = async (payload: Partial<CaseStudy>) =>
  (await client.post<ApiResponse<CaseStudy>>('/admin/cases', payload)).data.data;
export const adminUpdateCase = async (id: number, payload: Partial<CaseStudy>) =>
  (await client.put<ApiResponse<CaseStudy>>(`/admin/cases/${id}`, payload)).data.data;
export const adminDeleteCase = async (id: number) =>
  (await client.delete<ApiResponse<null>>(`/admin/cases/${id}`)).data.data;

export const adminFetchCourses = async () =>
  (await client.get<ApiResponse<CourseLesson[]>>('/admin/course')).data.data;
export const adminCreateCourse = async (payload: Partial<CourseLesson>) =>
  (await client.post<ApiResponse<CourseLesson>>('/admin/course', payload)).data.data;

export const adminFetchPrompts = async () =>
  (await client.get<ApiResponse<AiPromptTemplate[]>>('/admin/ai/prompts')).data.data;
export const adminCreatePrompt = async (payload: Partial<AiPromptTemplate>) =>
  (await client.post<ApiResponse<AiPromptTemplate>>('/admin/ai/prompts', payload)).data.data;
