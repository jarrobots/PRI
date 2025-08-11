INSERT INTO test25.thm_group_table (id, name) VALUES (1, 'Grupa projektu Moduł prace dyplomowe') ON CONFLICT DO NOTHING;

INSERT INTO test25.thm_student (id, email, f_name, group_id, l_name) VALUES (1, '???', 'Patryk', 1, 'Piec') ON CONFLICT DO NOTHING;
INSERT INTO test25.thm_student (id, email, f_name, group_id, l_name) VALUES (2, '???', 'Jarosław', 1, 'Romańczuk') ON CONFLICT DO NOTHING;
INSERT INTO test25.thm_student (id, email, f_name, group_id, l_name) VALUES (3, '???', 'Tomasz', 1, 'Wasyłyk') ON CONFLICT DO NOTHING;
INSERT INTO test25.thm_student (id, email, f_name, group_id, l_name) VALUES (4, '???', 'Bartosz', 1, 'Wiśniewski') ON CONFLICT DO NOTHING;
