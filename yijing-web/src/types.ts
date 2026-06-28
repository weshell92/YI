export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message: string;
}

export interface Trigram {
  id: number;
  name: string;
  symbol: string;
  nature: string;
  element: string;
  attribute: string;
  humanMeaning: string;
  modernScenario: string;
  plainExplanation: string;
  suitableProblems: string;
  commonPitfall: string;
  relatedHexagramIds: number[];
  sortOrder: number;
}

export interface HexagramSummary {
  id: number;
  sequenceNo: number;
  name: string;
  hexagramSymbol: string;
  hexagramCardTitle: string;
  structure: string;
  keywords: string[];
  shortDescription: string;
}

export interface Hexagram {
  id: number;
  sequenceNo: number;
  name: string;
  upperTrigramId: number;
  lowerTrigramId: number;
  hexagramSymbol: string;
  hexagramCardTitle: string;
  structure: string;
  keywords: string[];
  shortDescription: string;
  guaCi: string;
  tuanCi: string;
  xiangCi: string;
  daXiang: string;
  wenYan: string;
  xuGua: string;
  zaGua: string;
  shuoGua: string;
  xiCiShang: string;
  xiCiXia: string;
  plainExplanation: string;
  situationAnalysis: string;
  advice: string;
  careerExplanation: string;
  loveExplanation: string;
  relationExplanation: string;
  businessExplanation: string;
  commonMistakes: string;
  relatedHexagramIds: number[];
}

export interface HexagramLine {
  id: number;
  hexagramId: number;
  lineNo: number;
  lineName: string;
  originalText: string;
  xiangOriginalText: string;
  plainExplanation: string;
  modernMeaning: string;
  advice: string;
}

export interface HexagramDetailView {
  hexagram: Hexagram;
  upperTrigram: Trigram;
  lowerTrigram: Trigram;
  lines: HexagramLine[];
  relatedHexagrams: HexagramSummary[];
}

export interface CaseStudy {
  id: number;
  title: string;
  category: string;
  background: string;
  hexagramId: number;
  analysis: string;
  modernAnalysis: string;
  advice: string;
  sortOrder: number;
  published: boolean;
}

export interface CourseLesson {
  id: number;
  orderNo: number;
  stage: string;
  title: string;
  overview: string;
  coreConcept: string;
  plainExplanation: string;
  termNotes: string[];
  bodyParagraphs: string[];
  diagram: string;
  example: string;
  realWorldAnalogy: string;
  commonMistakes: string[];
  followUpLinks: string[];
  relatedTrigramIds: number[];
  relatedHexagramIds: number[];
  quizQuestions: string[];
}

export interface DivinationResult {
  recordId: number;
  question: string;
  primaryHexagram: HexagramSummary;
  movingLines: number[];
  changedHexagram: HexagramSummary;
  hexagramAnalysis: string;
  realityExplanation: string;
  riskWarnings: string[];
  actionSuggestions: string[];
  observationPoints: string;
  reviewPrompt: string;
}

export interface DivinationRecord {
  id: number;
  userId: number;
  question: string;
  method: string;
  originalHexagramId: number;
  changedHexagramId: number;
  movingLines: number[];
  analysis: string;
  realityExplanation: string;
  riskWarnings: string[];
  actionSuggestions: string[];
  observationPoints: string;
  reviewResult: string;
  createdAt: string;
}

export interface AiExplainView {
  restatedQuestion: string;
  primaryExplanation: string;
  movingLineExplanation: string;
  changedHexagramExplanation: string;
  situationAnalysis: string;
  riskWarnings: string[];
  actionSuggestions: string[];
  observationPoints: string;
  reviewSuggestion: string;
}

export interface AiPromptTemplate {
  id: number;
  name: string;
  purpose: string;
  content: string;
  enabled: boolean;
}
