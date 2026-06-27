CREATE TABLE trigram (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    symbol VARCHAR(20),
    nature VARCHAR(50),
    element VARCHAR(20),
    attribute VARCHAR(100),
    human_meaning TEXT,
    modern_scenario TEXT,
    plain_explanation TEXT,
    sort_order INT DEFAULT 0
);

CREATE TABLE hexagram (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sequence_no INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    upper_trigram_id BIGINT,
    lower_trigram_id BIGINT,
    structure_desc VARCHAR(50),
    keywords VARCHAR(255),
    short_description TEXT,
    gua_ci TEXT,
    tuan_ci TEXT,
    xiang_ci TEXT,
    plain_explanation TEXT,
    situation_analysis TEXT,
    advice TEXT,
    career_explanation TEXT,
    love_explanation TEXT,
    relation_explanation TEXT,
    business_explanation TEXT,
    common_mistakes TEXT
);

CREATE TABLE hexagram_line (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    hexagram_id BIGINT NOT NULL,
    line_no INT NOT NULL,
    line_name VARCHAR(50),
    original_text TEXT,
    plain_explanation TEXT,
    modern_meaning TEXT,
    advice TEXT
);

CREATE TABLE case_library (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(50),
    background TEXT,
    hexagram_id BIGINT,
    analysis TEXT,
    modern_analysis TEXT,
    advice TEXT,
    sort_order INT DEFAULT 0,
    published TINYINT DEFAULT 1
);

CREATE TABLE divination_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    method VARCHAR(50),
    original_hexagram_id BIGINT,
    changed_hexagram_id BIGINT,
    moving_lines VARCHAR(50),
    analysis TEXT,
    reality_explanation TEXT,
    observation_points TEXT,
    review_result TEXT,
    create_time DATETIME
);

CREATE TABLE course_lesson (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    core_concept TEXT,
    plain_explanation TEXT,
    diagram TEXT,
    example TEXT
);

CREATE TABLE ai_prompt_template (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    purpose VARCHAR(255),
    content TEXT,
    enabled TINYINT DEFAULT 1
);

