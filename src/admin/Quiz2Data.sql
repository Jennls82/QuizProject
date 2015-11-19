
            
INSERT INTO question (id, text)
            VALUES (5, 'What does the Latin American monster, the chupacabra eat?');
INSERT INTO question (id, text)
            VALUES (6, 'According to Voodoo legand, feeding a zombie what, will make it return to the grave?');
INSERT INTO question (id, text)
            VALUES (7, 'What type of Greek monster was Medusa?');
INSERT INTO question (id, text)
            VALUES (8, 'What does hearing the wail of a Banshee three times mean?');

INSERT INTO answer (question_id, text, isCorrect)
            VALUES (5, 'small children', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (5, 'goat blood', 'Y');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (5, 'virgins', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (5, 'rats', 'N');
            
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (6, 'brains', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (6, 'dirt', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (6, 'salt', 'Y');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (6, 'chicken bones', 'N');

INSERT INTO answer (question_id, text, isCorrect)
            VALUES (7, 'a gorgon', 'Y');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (7, 'a manticore', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (7, 'a griffin', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (7, 'a mermaid', 'N');

INSERT INTO answer (question_id, text, isCorrect)
            VALUES (8, 'the death of a person', 'Y');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (8, 'a famine', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (8, 'a draught', 'N');
INSERT INTO answer (question_id, text, isCorrect)
            VALUES (8, 'a plague', 'N');

INSERT INTO quiz (name)
            VALUES ('Creature Quiz');

INSERT INTO quiz_question (quiz_id, question_id)
            VALUES (2, 5);
INSERT INTO quiz_question (quiz_id, question_id)
            VALUES (2, 6);
INSERT INTO quiz_question (quiz_id, question_id)
            VALUES (2, 7);
INSERT INTO quiz_question (quiz_id, question_id)
            VALUES (2, 8);
            
