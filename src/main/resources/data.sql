INSERT INTO book (title, author, category) VALUES
('リーダブルコード', 'Dustin Boswell', '技術書'),
('Clean Code', 'Robert C. Martin', '技術書'),
('達人プログラマー', '鈴木翔平', '技術書'),
('プリンシプルコード', '上田イサミ', '技術書'),
('SQLアンチパターン', '佐々木何某', '技術書');

INSERT INTO reading_record (book_id, status, memo) VALUES
(1, 'COMPLETED', 'とても面白い本だった'),
(2, 'READING', 'P104まで読めた'),
(3, 'UNREAD', NULL),
(4, 'COMPLETED', 'あまり面白くなかった'),
(5, 'UNREAD', 'タイトルに惹かれた');