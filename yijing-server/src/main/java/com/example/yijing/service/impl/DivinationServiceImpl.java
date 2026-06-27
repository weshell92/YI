package com.example.yijing.service.impl;

import com.example.yijing.dto.DivinationRequest;
import com.example.yijing.model.DivinationRecord;
import com.example.yijing.model.Hexagram;
import com.example.yijing.repository.InMemoryContentRepository;
import com.example.yijing.service.DivinationService;
import com.example.yijing.vo.DivinationResultView;
import com.example.yijing.vo.HexagramSummary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DivinationServiceImpl implements DivinationService {

    private final InMemoryContentRepository repository;

    public DivinationServiceImpl(InMemoryContentRepository repository) {
        this.repository = repository;
    }

    @Override
    public DivinationResultView generate(DivinationRequest request) {
        List<Hexagram> hexagrams = repository.findAllHexagrams();
        int size = hexagrams.size();
        int base = Math.abs((request.question() + "|" + request.method()).hashCode());
        if (request.luckyNumber() != null) {
            base += request.luckyNumber() * 31;
        }
        Hexagram primary = resolvePrimary(request, hexagrams, base, size);
        List<Integer> movingLines = request.movingLines() == null || request.movingLines().isEmpty()
                ? List.of(base % 6 + 1)
                : request.movingLines().stream().distinct().sorted().toList();
        Hexagram changed = hexagrams.get((primary.sequenceNo() + movingLines.get(0)) % size);

        String analysis = primary.plainExplanation();
        String realityExplanation = "这个卦更像是在提醒你：" + primary.situationAnalysis();
        List<String> risks = List.of(
                "信息是否充分，是否只看到了机会而忽略成本",
                "风险承受能力是否与你当前阶段匹配",
                "是否存在退路、缓冲资金或替代方案"
        );
        List<String> suggestions = List.of(
                "先确认关键事实，再做下一步决定",
                "把当前判断拆成可验证的问题清单",
                "保留阶段性复盘点，避免一次性押注"
        );
        String observation = "重点继续观察：时机是否成熟、合作对象是否可靠、你是否已经准备好承担变化后的代价。";
        String reviewPrompt = "把这次判断保存下来，后续回看：哪些判断来自事实，哪些只是情绪。";

        DivinationRecord record = new DivinationRecord(
                null,
                1L,
                request.question(),
                StringUtils.hasText(request.method()) ? request.method() : "time",
                primary.id(),
                changed.id(),
                movingLines,
                analysis,
                realityExplanation,
                risks,
                suggestions,
                observation,
                "",
                LocalDateTime.now()
        );
        DivinationRecord saved = repository.saveRecord(record);
        return new DivinationResultView(
                saved.getId(),
                request.question(),
                toSummary(primary),
                movingLines,
                toSummary(changed),
                analysis,
                realityExplanation,
                risks,
                suggestions,
                observation,
                reviewPrompt
        );
    }

    @Override
    public List<DivinationRecord> listRecords() {
        return repository.findAllRecords();
    }

    @Override
    public DivinationRecord review(Long recordId, String reviewResult) {
        DivinationRecord record = repository.findRecord(recordId);
        if (record == null) {
            return null;
        }
        record.setReviewResult(reviewResult);
        repository.saveRecord(record);
        return record;
    }

    private Hexagram resolvePrimary(DivinationRequest request, List<Hexagram> hexagrams, int base, int size) {
        if ("manual".equalsIgnoreCase(request.method()) && request.selectedHexagramId() != null) {
            Hexagram picked = repository.findHexagram(request.selectedHexagramId());
            if (picked != null) {
                return picked;
            }
        }
        if ("number".equalsIgnoreCase(request.method()) && request.luckyNumber() != null) {
            return hexagrams.get(Math.abs(request.luckyNumber()) % size);
        }
        return hexagrams.get(base % size);
    }

    private HexagramSummary toSummary(Hexagram hexagram) {
        return new HexagramSummary(
                hexagram.id(),
                hexagram.sequenceNo(),
                hexagram.name(),
                hexagram.structure(),
                hexagram.keywords(),
                hexagram.shortDescription()
        );
    }
}

