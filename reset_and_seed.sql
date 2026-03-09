-- ============================================================
-- RESET COMPLET + JEU DE DONNÉES — db_note (version prof)
-- ============================================================

TRUNCATE TABLE Note_finale        RESTART IDENTITY CASCADE;
TRUNCATE TABLE Note               RESTART IDENTITY CASCADE;
TRUNCATE TABLE Parametre          RESTART IDENTITY CASCADE;
TRUNCATE TABLE Matiere_Correcteur;
TRUNCATE TABLE Candidat           RESTART IDENTITY CASCADE;
TRUNCATE TABLE Correcteur         RESTART IDENTITY CASCADE;
TRUNCATE TABLE Matiere            RESTART IDENTITY CASCADE;
TRUNCATE TABLE Operation          RESTART IDENTITY CASCADE;
TRUNCATE TABLE Resolution         RESTART IDENTITY CASCADE;

-- ============================================================
-- DONNÉES DE BASE (exactement comme l'image du prof)
-- ============================================================

-- Candidats
INSERT INTO Candidat (nom) VALUES ('Candidat1'), ('Candidat2');

-- Matières
INSERT INTO Matiere (libelle) VALUES ('JAVA'), ('PHP');

-- Correcteurs
INSERT INTO Correcteur (nom) VALUES ('Correcteur1'), ('Correcteur2'), ('Correcteur3');

-- Résolutions (libelle exact = ce qu'on compare dans le code)
INSERT INTO Resolution (libelle) VALUES ('Petit'), ('Grand'), ('Moyenne');

-- Opérations (signe exact utilisé dans evaluerCondition)
INSERT INTO Operation (signe) VALUES ('<'), ('<='), ('>'), ('>=');

-- ============================================================
-- ASSOCIATIONS Matière ↔ Correcteurs
-- JAVA  : Correcteur1, Correcteur2, Correcteur3
-- PHP   : Correcteur1, Correcteur2
-- ============================================================

INSERT INTO Matiere_Correcteur (idmatiere, idcorrecteur) VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 2), (2, 3);

-- ============================================================
-- PARAMÈTRES
-- JAVA : si E >= 3 → Petit   (cas 1 du prof)
-- PHP  : si E <  3 → Grand   (cas 2 du prof)
-- ============================================================

INSERT INTO Parametre (idMatiere, ecart, idOperation, idResolution) VALUES
    (1, 3, 4, 1),   -- JAVA  : E >= 3 → Petit
    (2, 3, 1, 2);   -- PHP   : E <  3 → Grand

-- ============================================================
-- NOTES
-- Candidat1 / JAVA  : 9 et 14  -> E=5 >= 3 -> VRAI -> note finale = 9
-- Candidat1 / PHP   : 11 et 13 -> E=2 <  3 -> VRAI -> note finale = 13
-- Candidat2 / JAVA  : 10 et 16 -> E=6 >= 3 -> VRAI -> note finale = 10
-- Candidat2 / PHP   : 12 et 15 -> E=3 <  3 -> FAUX -> pas de note finale
-- ============================================================

INSERT INTO Note (idMatiere, idCandidat, idCorrecteur, note) VALUES
    -- Candidat1 — JAVA (correcteurs 1 et 2)
    (1, 1, 1,  9.00),
    (1, 1, 2, 14.00),
    -- Candidat1 — PHP  (correcteurs 1 et 3)
    (2, 1, 1, 11.00),
    (2, 1, 3, 13.00),
    -- Candidat2 — JAVA (correcteurs 2 et 3)
    (1, 2, 2, 10.00),
    (1, 2, 3, 16.00),
    -- Candidat2 — PHP  (correcteurs 1 et 2)
    (2, 2, 1, 12.00),
    (2, 2, 2, 15.00);

-- ============================================================
-- VÉRIFICATION
-- ============================================================
SELECT 'Candidats'   AS table_name, COUNT(*) AS total FROM Candidat
UNION ALL SELECT 'Matières',     COUNT(*) FROM Matiere
UNION ALL SELECT 'Correcteurs',  COUNT(*) FROM Correcteur
UNION ALL SELECT 'Résolutions',  COUNT(*) FROM Resolution
UNION ALL SELECT 'Opérations',   COUNT(*) FROM Operation
UNION ALL SELECT 'Paramètres',   COUNT(*) FROM Parametre
UNION ALL SELECT 'Notes',        COUNT(*) FROM Note
UNION ALL SELECT 'Matière↔Corr', COUNT(*) FROM Matiere_Correcteur
UNION ALL SELECT 'Notes finales',COUNT(*) FROM Note_finale;
