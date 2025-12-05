-- 1. 데이터베이스 생성 (권장: 접속 전에 수동으로 생성)
-- CREATE DATABASE IF NOT EXISTS jcmd_local_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE your_project_db;

-- 2. 테이블 삭제 (테스트 환경에서만 사용)
DROP TABLE IF EXISTS board;

-- 3. 테이블 생성
CREATE TABLE board (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. 인덱스 생성 (선택 사항)
CREATE INDEX idx_board_title ON board (title);