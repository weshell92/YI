package com.example.yijing.service.impl;

import com.example.yijing.dto.AiExplainRequest;
import com.example.yijing.model.Hexagram;
import com.example.yijing.repository.InMemoryContentRepository;
import com.example.yijing.service.AiExplainService;
import com.example.yijing.vo.AiExplainView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiExplainServiceImpl implements AiExplainService {

    private final InMemoryContentRepository repository;

    public AiExplainServiceImpl(InMemoryContentRepository repository) {
        this.repository = repository;
    }

    @Override
    public AiExplainView explain(AiExplainRequest request) {
        Hexagram primary = request.primaryHexagramId() == null ? repository.findHexagram(5L) : repository.findHexagram(request.primaryHexagramId());
        Hexagram changed = request.changedHexagramId() == null ? repository.findHexagram(8L) : repository.findHexagram(request.changedHexagramId());
        String movingLineExplanation = "当前重点不在“会不会发生”，而在“哪一个变化点最值得观察”。如果没有明确动爻，就先把问题拆成风险、时机和准备度三部分。";
        return new AiExplainView(
                "你当前的问题是：" + request.question(),
                primary == null ? "请先选择一个卦象来辅助解释。" : primary.plainExplanation(),
                movingLineExplanation,
                changed == null ? "暂未指定变卦。" : changed.plainExplanation(),
                primary == null ? "先明确自己的处境，再进入解释。" : primary.situationAnalysis(),
                List.of(
                        "不要把卦象理解成绝对结果，而要理解成处境提醒",
                        "重点识别你是否忽略了成本、边界和退路",
                        "避免在情绪高点或低点做单点决策"
                ),
                List.of(
                        "把问题拆成 3 个可验证条件",
                        "为每个条件准备一条观察证据",
                        "设定下一次复盘时间，避免凭感觉反复摇摆"
                ),
                "继续观察信息是否充分、关键角色是否可靠、当前阶段是该推进还是该等待。",
                "后续回看时，重点记录：你当时依据了哪些事实，又被哪些情绪放大了。"
        );
    }
}

