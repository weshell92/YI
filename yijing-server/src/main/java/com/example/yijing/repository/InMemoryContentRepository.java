package com.example.yijing.repository;

import com.example.yijing.model.AiPromptTemplate;
import com.example.yijing.model.CaseStudy;
import com.example.yijing.model.CourseLesson;
import com.example.yijing.model.DivinationRecord;
import com.example.yijing.model.Hexagram;
import com.example.yijing.model.HexagramLine;
import com.example.yijing.model.Trigram;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryContentRepository {

    private final Map<Long, Trigram> trigramStore = new ConcurrentHashMap<>();
    private final Map<Long, Hexagram> hexagramStore = new ConcurrentHashMap<>();
    private final Map<Long, List<HexagramLine>> lineStore = new ConcurrentHashMap<>();
    private final Map<Long, CaseStudy> caseStore = new ConcurrentHashMap<>();
    private final Map<Long, CourseLesson> courseStore = new ConcurrentHashMap<>();
    private final Map<Long, AiPromptTemplate> aiPromptStore = new ConcurrentHashMap<>();
    private final Map<Long, DivinationRecord> divinationStore = new ConcurrentHashMap<>();

    private final AtomicLong trigramId = new AtomicLong(9);
    private final AtomicLong hexagramId = new AtomicLong(9);
    private final AtomicLong caseId = new AtomicLong(5);
    private final AtomicLong courseId = new AtomicLong(9);
    private final AtomicLong promptId = new AtomicLong(2);
    private final AtomicLong recordId = new AtomicLong(1);

    @PostConstruct
    public void init() {
        seedTrigrams();
        seedHexagrams();
        seedCases();
        seedCourses();
        seedPrompts();
    }

    public List<Trigram> findAllTrigrams() {
        return trigramStore.values().stream()
                .sorted(Comparator.comparing(Trigram::sortOrder))
                .toList();
    }

    public Trigram findTrigram(Long id) {
        return trigramStore.get(id);
    }

    public Trigram saveTrigram(Trigram trigram) {
        Long id = trigram.id() == null ? trigramId.getAndIncrement() : trigram.id();
        Trigram saved = new Trigram(
                id,
                trigram.name(),
                trigram.symbol(),
                trigram.nature(),
                trigram.element(),
                trigram.attribute(),
                trigram.humanMeaning(),
                trigram.modernScenario(),
                trigram.plainExplanation(),
                trigram.sortOrder() == null ? 99 : trigram.sortOrder()
        );
        trigramStore.put(id, saved);
        return saved;
    }

    public void deleteTrigram(Long id) {
        trigramStore.remove(id);
    }

    public List<Hexagram> findAllHexagrams() {
        return hexagramStore.values().stream()
                .sorted(Comparator.comparing(Hexagram::sequenceNo))
                .toList();
    }

    public Hexagram findHexagram(Long id) {
        return hexagramStore.get(id);
    }

    public Hexagram saveHexagram(Hexagram hexagram) {
        Long id = hexagram.id() == null ? hexagramId.getAndIncrement() : hexagram.id();
        Hexagram saved = new Hexagram(
                id,
                hexagram.sequenceNo(),
                hexagram.name(),
                hexagram.upperTrigramId(),
                hexagram.lowerTrigramId(),
                hexagram.structure(),
                hexagram.keywords(),
                hexagram.shortDescription(),
                hexagram.guaCi(),
                hexagram.tuanCi(),
                hexagram.xiangCi(),
                hexagram.plainExplanation(),
                hexagram.situationAnalysis(),
                hexagram.advice(),
                hexagram.careerExplanation(),
                hexagram.loveExplanation(),
                hexagram.relationExplanation(),
                hexagram.businessExplanation(),
                hexagram.commonMistakes(),
                hexagram.relatedHexagramIds()
        );
        hexagramStore.put(id, saved);
        return saved;
    }

    public void deleteHexagram(Long id) {
        hexagramStore.remove(id);
        lineStore.remove(id);
    }

    public List<HexagramLine> findHexagramLines(Long hexagramIdValue) {
        return lineStore.getOrDefault(hexagramIdValue, List.of());
    }

    public List<CaseStudy> findAllCases() {
        return caseStore.values().stream()
                .sorted(Comparator.comparing(CaseStudy::sortOrder))
                .toList();
    }

    public CaseStudy findCase(Long id) {
        return caseStore.get(id);
    }

    public CaseStudy saveCase(CaseStudy caseStudy) {
        Long id = caseStudy.id() == null ? caseId.getAndIncrement() : caseStudy.id();
        CaseStudy saved = new CaseStudy(
                id,
                caseStudy.title(),
                caseStudy.category(),
                caseStudy.background(),
                caseStudy.hexagramId(),
                caseStudy.analysis(),
                caseStudy.modernAnalysis(),
                caseStudy.advice(),
                caseStudy.sortOrder() == null ? 99 : caseStudy.sortOrder(),
                caseStudy.published() == null || caseStudy.published()
        );
        caseStore.put(id, saved);
        return saved;
    }

    public void deleteCase(Long id) {
        caseStore.remove(id);
    }

    public List<CourseLesson> findAllCourses() {
        return courseStore.values().stream()
                .sorted(Comparator.comparing(CourseLesson::orderNo))
                .toList();
    }

    public CourseLesson findCourse(Long id) {
        return courseStore.get(id);
    }

    public CourseLesson saveCourse(CourseLesson courseLesson) {
        Long id = courseLesson.id() == null ? courseId.getAndIncrement() : courseLesson.id();
        CourseLesson saved = new CourseLesson(
                id,
                courseLesson.orderNo(),
                courseLesson.title(),
                courseLesson.coreConcept(),
                courseLesson.plainExplanation(),
                courseLesson.diagram(),
                courseLesson.example(),
                courseLesson.quizQuestions()
        );
        courseStore.put(id, saved);
        return saved;
    }

    public void deleteCourse(Long id) {
        courseStore.remove(id);
    }

    public List<AiPromptTemplate> findAllPrompts() {
        return aiPromptStore.values().stream()
                .sorted(Comparator.comparing(AiPromptTemplate::id))
                .toList();
    }

    public AiPromptTemplate findPrompt(Long id) {
        return aiPromptStore.get(id);
    }

    public AiPromptTemplate savePrompt(AiPromptTemplate prompt) {
        Long id = prompt.id() == null ? promptId.getAndIncrement() : prompt.id();
        AiPromptTemplate saved = new AiPromptTemplate(
                id,
                prompt.name(),
                prompt.purpose(),
                prompt.content(),
                prompt.enabled() == null || prompt.enabled()
        );
        aiPromptStore.put(id, saved);
        return saved;
    }

    public void deletePrompt(Long id) {
        aiPromptStore.remove(id);
    }

    public DivinationRecord saveRecord(DivinationRecord record) {
        if (record.getId() == null) {
            record.setId(recordId.getAndIncrement());
            record.setCreatedAt(LocalDateTime.now());
        }
        divinationStore.put(record.getId(), record);
        return record;
    }

    public List<DivinationRecord> findAllRecords() {
        return divinationStore.values().stream()
                .sorted(Comparator.comparing(DivinationRecord::getCreatedAt).reversed())
                .toList();
    }

    public DivinationRecord findRecord(Long id) {
        return divinationStore.get(id);
    }

    private void seedTrigrams() {
        List<Trigram> trigrams = List.of(
                new Trigram(1L, "乾", "☰", "天", "金", "刚健", "主动、领导、创造", "职业开创、团队领导、承担责任", "乾卦强调自强不息，适合用来理解主动开局和承担使命的阶段。", 1),
                new Trigram(2L, "坤", "☷", "地", "土", "顺承", "包容、承载、配合", "合作支撑、长期投入、稳步跟进", "坤卦强调厚德载物，提醒人先把承接能力做好，再谈扩张。", 2),
                new Trigram(3L, "震", "☳", "雷", "木", "行动", "开始、惊动、启动", "新项目启动、组织变动、主动突破", "震卦对应开端和动能，适合用来判断何时该真正迈出第一步。", 3),
                new Trigram(4L, "巽", "☴", "风", "木", "渗透", "进入、影响、顺势", "沟通渗透、长期影响、关系经营", "巽卦不是强攻，而是用持续影响和顺势切入来达成目标。", 4),
                new Trigram(5L, "坎", "☵", "水", "水", "险难", "困难、压力、陷阱", "现金流风险、信息不明、连续压力", "坎卦提醒先识别风险和出口，再谈加速推进。", 5),
                new Trigram(6L, "离", "☲", "火", "火", "光明", "看见、文明、依附", "品牌表达、认知提升、让问题被看见", "离卦强调看见真相与建立清晰表达，适合做认知澄清。", 6),
                new Trigram(7L, "艮", "☶", "山", "土", "止", "边界、停止、节制", "设边界、止损、暂停扩张", "艮卦不是消极停摆，而是知道什么时候先停下来，守住边界。", 7),
                new Trigram(8L, "兑", "☱", "泽", "金", "悦", "交流、喜悦、交换", "谈判沟通、关系缓和、社群互动", "兑卦强调沟通和反馈，适合讨论协作、表达和关系松动。", 8)
        );
        trigrams.forEach(trigram -> trigramStore.put(trigram.id(), trigram));
    }

    private void seedHexagrams() {
        List<Hexagram> hexagrams = List.of(
                new Hexagram(1L, 1, "乾卦", 1L, 1L, "乾上乾下", List.of("开创", "自强", "主动"), "代表开创、担当和持续行动。", "元亨利贞。", "大哉乾元，万物资始。", "天行健，君子以自强不息。", "乾卦提醒你具备开创潜力，但越在上升期越需要持续修身与自律。", "当前更像是主动开局期，需要先担起责任，再谈更大扩张。", "先定方向，再持续投入，把节奏拉长而不是一时冲动。", "事业上适合承担更大责任，但要避免自我消耗。", "感情上容易强势，需兼顾倾听。", "人际上宜以行动服人，而不是只讲理念。", "创业上适合做从 0 到 1 的突破，但不要忽视组织能力。", "把乾卦理解成无条件猛冲，会忽视节奏与德行。", List.of(2L, 5L)),
                new Hexagram(2L, 2, "坤卦", 2L, 2L, "坤上坤下", List.of("承载", "顺势", "稳定"), "代表承接、包容和配合。", "元亨，利牝马之贞。", "至哉坤元，万物资生。", "地势坤，君子以厚德载物。", "坤卦强调先做承接者，把土壤打厚，很多时候比抢着出头更重要。", "你所在的阶段需要稳定基础、汇聚资源，而不是急于抢功。", "保持耐心，先把底层能力、协作关系和执行质量做好。", "事业上适合做平台支持和流程稳固。", "感情上适合以耐心和照顾建立信任。", "人际上要避免过度迎合，承载不等于失去边界。", "创业上适合搭底盘、补运营和后端体系。", "把坤卦误解成消极退让，会忽视其中的厚实力量。", List.of(1L, 7L)),
                new Hexagram(3L, 3, "屯卦", 5L, 3L, "坎上震下", List.of("草创", "阻力", "起步"), "代表初生之难，适合小步试错。", "元亨利贞，勿用有攸往。", "屯，刚柔始交而难生。", "云雷屯，君子以经纶。", "屯卦讲的是事情刚启动时不可避免的混乱与阻力，重点不是退，而是建立秩序。", "目前更像项目初期、创业初期或关系初期，阻力大但并非不能做。", "先补资源、补协作、补流程，用小规模验证代替一次性重押。", "事业上适合试点，不宜直接全面铺开。", "感情上要慢慢建立信任，不宜催结果。", "人际上要先理解规则，再谈影响力。", "创业上要先验证需求和现金流。", "把屯卦理解成彻底不行，会错失早期准备窗口。", List.of(4L, 5L)),
                new Hexagram(4L, 4, "蒙卦", 7L, 5L, "艮上坎下", List.of("启蒙", "学习", "引导"), "代表先学会提问，再寻找答案。", "亨。匪我求童蒙，童蒙求我。", "蒙，山下有险，险而止。", "山下出泉，蒙。", "蒙卦适合用来理解学习和启蒙阶段，重点不是马上会，而是建立正确的问题意识。", "你现在更需要补认知模型，而不是急着做复杂判断。", "拆解问题，先问清楚自己缺什么信息，再决定向谁请教。", "事业上适合培训、导师制和流程入门。", "感情上要避免带着想象补全对方。", "人际上先学会倾听和求证。", "创业上适合用户调研与认知校准。", "把蒙卦理解成愚昧，会忽视它真正强调的启蒙过程。", List.of(3L, 6L)),
                new Hexagram(5L, 5, "需卦", 5L, 1L, "坎上乾下", List.of("等待", "蓄势", "准备"), "代表时机未到前的准备和观察。", "有孚，光亨，贞吉。", "需，须也，险在前也。", "云上于天，需。", "需卦不是让你永远不动，而是提醒你在风险前方时先准备资源、信息和退路。", "当前不是单纯看机会，而是看自己是否准备好承担变化带来的代价。", "先确认信息充分、风险可控、退路明确，再做下一步动作。", "事业上适合先观察组织走向，再决定跳槽或押注。", "感情上适合先看对方稳定性，不宜仓促投入。", "人际上需要判断合作对象是否可靠。", "创业上适合补现金流和关键伙伴。", "把需卦理解成拖延，会忽视它背后的主动准备。", List.of(3L, 6L)),
                new Hexagram(6L, 6, "讼卦", 1L, 5L, "乾上坎下", List.of("争议", "边界", "分歧"), "代表有争议时先厘清边界和依据。", "有孚窒惕，中吉。", "讼，上刚下险，险而健讼。", "天与水违行，讼。", "讼卦更像是在提醒你：不是所有冲突都要正面硬拼，先判断争什么、凭什么争。", "局势中存在利益冲突或认知分歧，关键是厘清证据和边界。", "先留痕、先厘责、先看规则，再决定沟通还是退出。", "事业上适合法务、合同、职责边界梳理。", "感情上要避免陷入情绪辩论。", "人际上需要规则而不是争一时输赢。", "创业上要先谈清楚股权与责任。", "把讼卦理解成必须硬刚，会增加无效消耗。", List.of(5L, 7L)),
                new Hexagram(7L, 10, "履卦", 1L, 8L, "乾上兑下", List.of("谨慎", "规矩", "临界"), "代表在有压力和规则的环境中谨慎行事。", "履虎尾，不咥人，亨。", "履，柔履刚也。", "上天下泽，履。", "履卦像是在老虎尾巴上行走，说明环境有压力、有规则，但并非不能走。", "你所处的阶段需要保持分寸感，既不能退缩，也不能莽撞。", "先识别权力结构、风险点和可行动边界，再稳步前进。", "事业上适合新岗位适应和高压组织生存。", "感情上要重视边界与尊重。", "人际上需谨慎发言、尊重秩序。", "创业上要在资源有限时保持节奏。", "把履卦理解成只能保守，会忽视其“可行但需谨慎”的主旨。", List.of(1L, 6L)),
                new Hexagram(8L, 11, "泰卦", 2L, 1L, "坤上乾下", List.of("通达", "协同", "顺畅"), "代表上下通气、资源流动顺畅的阶段。", "小往大来，吉亨。", "天地交而万物通也。", "天地交，泰。", "泰卦强调内外协同和资源流动，但越顺的时候越要守住节奏。", "你可能正处在推进条件较好的窗口期，适合把握机会整合资源。", "趁势推进关键动作，同时为后续波动预留缓冲。", "事业上适合推动协作和组织联动。", "感情上适合建立长期关系。", "人际上利于修复关系、形成互信。", "创业上适合整合资源、扩大验证成果。", "把泰卦理解成永久顺利，会忽视周期变化。", List.of(2L, 5L))
        );

        hexagrams.forEach(hexagram -> hexagramStore.put(hexagram.id(), hexagram));

        putLines(1L, List.of(
                line(101L, 1L, 1, "初九", "潜龙勿用。", "现在有潜力，但条件还不成熟，不宜急于表现。", "适合打基础、积累资源。", "先准备，不急于高调亮相。"),
                line(102L, 1L, 2, "九二", "见龙在田，利见大人。", "能力开始被看见，适合寻求更大的平台与协助。", "开始进入能被识别的阶段。", "主动连接靠谱的人和资源。"),
                line(103L, 1L, 3, "九三", "君子终日乾乾。", "持续努力、保持警惕，别在上升期松懈。", "越在进步阶段越要稳。", "控制节奏，避免自满。")
        ));
        putLines(2L, List.of(
                line(201L, 2L, 1, "初六", "履霜，坚冰至。", "细微变化要尽早感知，很多大问题都有早期信号。", "风险常在早期就有痕迹。", "关注征兆，提前应对。"),
                line(202L, 2L, 2, "六二", "直方大，不习无不利。", "保持正直、稳定和宽厚，很多事就会自然顺。", "把基础打正，比技巧更重要。", "先把原则和秩序立住。")
        ));
        putLines(3L, List.of(
                line(301L, 3L, 1, "初九", "磐桓，利居贞，利建侯。", "起步阶段反复试探很正常，关键是别乱动。", "草创期需要先稳住阵地。", "先小步试错，再考虑扩张。"),
                line(302L, 3L, 2, "六二", "屯如邅如，乘马班如。", "推进不顺时不是没希望，而是节奏要调整。", "先解阻力，再谈加速。", "先补缺口，再推进关键动作。")
        ));
        putLines(4L, List.of(
                line(401L, 4L, 1, "初六", "发蒙，利用刑人。", "启蒙阶段要先立规则，再谈灵活。", "学习需要边界和方法。", "先搭建基础框架。"),
                line(402L, 4L, 2, "九二", "包蒙吉。", "愿意包容学习中的笨拙，才会真正成长。", "允许自己和团队在入门阶段不完美。", "给成长留空间。")
        ));
        putLines(5L, List.of(
                line(501L, 5L, 1, "初九", "需于郊。", "风险还在远处，可以先做外圈观察和准备。", "先远距观察，而不是立刻卷入。", "先收集信息。"),
                line(502L, 5L, 2, "九二", "需于沙。", "已经接近风险地带，需要保持克制与清醒。", "离真正决策只差一步。", "先验证关键假设。"),
                line(503L, 5L, 3, "九三", "需于泥。", "如果拖得过近，风险会变得更难退。", "等待不等于无底线拖延。", "设清晰的决策截止点。")
        ));
        putLines(6L, List.of(
                line(601L, 6L, 1, "初六", "不永所事，小有言，终吉。", "早期分歧别立刻升级，先控制范围。", "不是所有争议都值得扩大。", "先压缩冲突面。"),
                line(602L, 6L, 2, "九二", "不克讼，归而逋。", "明知打不赢时，及时收手比硬撑更好。", "退出也是一种判断力。", "保全实力，避免持续消耗。")
        ));
        putLines(7L, List.of(
                line(701L, 7L, 1, "初九", "素履，往无咎。", "保持朴素和谨慎，反而能走得稳。", "别急着展示复杂动作。", "先做稳基础动作。"),
                line(702L, 7L, 2, "九二", "履道坦坦，幽人贞吉。", "心态稳、节奏正，才能在复杂环境中行得长远。", "稳住自己比取悦所有人更重要。", "守住节奏和原则。")
        ));
        putLines(8L, List.of(
                line(801L, 8L, 1, "初九", "拔茅茹，以其汇。", "顺势联动时，要看到团队之间的相互带动。", "顺利阶段要重视协同。", "优先整合可联动资源。"),
                line(802L, 8L, 2, "九二", "包荒，用冯河。", "局面好转时，也要容纳不整齐的部分。", "顺境中仍需治理复杂度。", "边推进边补管理。")
        ));
    }

    private void seedCases() {
        List<CaseStudy> cases = List.of(
                new CaseStudy(1L, "想离职创业，但资金和团队都不稳定", "创业", "用户在公司工作多年，想出来创业，但资金不足，团队也尚未确定。", 3L, "屯卦代表初生之难，说明事情刚开始，阻力较大。", "这不是不能创业，而是说明现在仍在草创阶段，需要先补资源、补队伍、补现金流。", "先做副业验证和小规模试错，不要一次性离场。", 1, true),
                new CaseStudy(2L, "被新公司挖角，但担心稳定性", "职场", "用户获得新工作机会，但担心新公司经营和团队文化不稳。", 5L, "需卦强调等待、观察和准备。", "现在不是马上做决定的阶段，而是要先核查公司基本盘、岗位权责和个人退路。", "补充尽调信息，再做转岗判断。", 2, true),
                new CaseStudy(3L, "进入新团队后受到重视，但同事关系复杂", "人际", "用户进入新团队后受到领导重视，但同事之间竞争明显。", 7L, "履卦强调谨慎、规矩和分寸感。", "说明环境可行，但必须守住边界和节奏，尤其不要高调站队。", "先建立信任和专业度，再扩大影响力。", 3, true),
                new CaseStudy(4L, "关系进入高光期，却担心后劲不足", "感情", "双方关系发展迅速、互动很好，但用户担心热度之后的不稳定。", 8L, "泰卦说明当前关系顺畅，资源互通。", "顺的时候更要建立长期机制，比如沟通习惯、边界和共同预期。", "趁顺势阶段把长期协作方式谈清楚。", 4, true)
        );
        cases.forEach(caseStudy -> caseStore.put(caseStudy.id(), caseStudy));
    }

    private void seedCourses() {
        List<CourseLesson> lessons = List.of(
                lesson(1L, 1, "什么是易经", "《易经》是一套讨论变化和判断的思维系统。", "不要把它只理解成预测工具，它更像一套观察处境、理解变化、辅助决策的方法。", "问题 -> 卦象 -> 处境 -> 行动", "例如你在换工作前，用它先看风险和准备，而不是直接问成败。"),
                lesson(2L, 2, "阴阳", "阴阳不是好坏，而是两种相反又互补的力量。", "阴可以理解为柔、静、承载；阳可以理解为刚、动、外放。", "阴爻 -- / 阳爻 —", "比如团队里既需要冲锋的人，也需要稳定推进的人。"),
                lesson(3L, 3, "爻", "爻是组成卦象的最小单位。", "三爻组成八卦，六爻组成六十四卦。动爻表示局势中的变化点。", "单爻 -> 三爻 -> 六爻", "当某一爻变化时，往往意味着局势的关键转折。"),
                lesson(4L, 4, "八卦", "八卦是理解六十四卦的基础词汇。", "乾坤震巽坎离艮兑分别对应自然象、性质和现实含义。", "上卦 + 下卦", "理解坎是险、离是明，才更容易理解具体卦象。"),
                lesson(5L, 5, "六十四卦", "六十四卦是八卦两两组合后的处境地图。", "每一卦都在描述一种局面，不是只告诉你结果，而是帮助你理解状态。", "8 x 8 = 64", "需卦讲等待和准备，屯卦讲草创阶段的阻力。"),
                lesson(6L, 6, "本卦与变卦体系", "本卦看当下，变卦看趋势。", "本卦描述当前处境，动爻标出变化点，变卦提供后续演变方向。", "本卦 -> 动爻 -> 变卦", "同一个问题里，最值得看的是哪里在变。"),
                lesson(7L, 7, "如何看一个卦", "先看结构，再看文字。", "优先看上下卦、关键词、处境分析、行动建议，再回到卦辞和爻辞。", "结构 -> 关键词 -> 场景", "如果只背原文，很难真正用到现实判断里。"),
                lesson(8L, 8, "如何结合现实问题分析", "把卦象转成风险清单和行动问题。", "不要直接问吉凶，而要问：我的信息够不够？风险能不能承受？当前该进还是该等？", "问题 -> 风险 -> 建议", "这也是这个网站坚持反迷信表达的原因。")
        );
        lessons.forEach(lesson -> courseStore.put(lesson.id(), lesson));
    }

    private void seedPrompts() {
        AiPromptTemplate prompt = new AiPromptTemplate(
                1L,
                "默认解卦提示词",
                "用于根据用户问题生成结构化解释",
                "1. 用户问题复述 2. 本卦解释 3. 动爻解释 4. 变卦解释 5. 当前处境分析 6. 风险提醒 7. 行动建议 8. 继续观察的信息 9. 复盘建议",
                true
        );
        aiPromptStore.put(prompt.id(), prompt);
    }

    private void putLines(Long hexagramIdValue, List<HexagramLine> lines) {
        lineStore.put(hexagramIdValue, lines);
    }

    private HexagramLine line(Long id, Long hexagramIdValue, int lineNo, String lineName, String originalText,
                              String plainExplanation, String modernMeaning, String advice) {
        return new HexagramLine(id, hexagramIdValue, lineNo, lineName, originalText, plainExplanation, modernMeaning, advice);
    }

    private CourseLesson lesson(Long id, int orderNo, String title, String coreConcept, String plainExplanation,
                                String diagram, String example) {
        return new CourseLesson(
                id,
                orderNo,
                title,
                coreConcept,
                plainExplanation,
                diagram,
                example,
                List.of("这一课最核心的判断框架是什么？", "如果把它放进现实问题里，你会先观察什么？")
        );
    }
}

