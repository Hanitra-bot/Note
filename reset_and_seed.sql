
TRUNCATE TABLE Note_finale        RESTART IDENTITY CASCADE;
TRUNCATE TABLE Note               RESTART IDENTITY CASCADE;
TRUNCATE TABLE Parametre          RESTART IDENTITY CASCADE;
TRUNCATE TABLE Matiere_Correcteur;
TRUNCATE TABLE Candidat           RESTART IDENTITY CASCADE;
TRUNCATE TABLE Correcteur         RESTART IDENTITY CASCADE;
TRUNCATE TABLE Matiere            RESTART IDENTITY CASCADE;
TRUNCATE TABLE Operation          RESTART IDENTITY CASCADE;
TRUNCATE TABLE Resolution         RESTART IDENTITY CASCADE;

INSERT INTO Candidat (nom) VALUES ('Candidat1'), ('Candidat2');

INSERT INTO Matiere (libelle) VALUES ('JAVA'), ('PHP');

INSERT INTO Correcteur (nom) VALUES ('Correcteur1'), ('Correcteur2'), ('Correcteur3');

INSERT INTO Resolution (libelle) VALUES ('Petit'), ('Grand'), ('Moyenne');

INSERT INTO Operation (signe) VALUES ('<'), ('<='), ('>'), ('>=');

INSERT INTO Matiere_Correcteur (idmatiere, idcorrecteur) VALUES
    (1,1),(1,2),(1,3),(2,1),(2,2),(2,3);

INSERT INTO Parametre (idMatiere, ecart, idOperation, idResolution) VALUES
    (1, 7, 1, 2),   -- JAVA : E <  7 → Grand
    (1, 7, 4, 3),   -- JAVA : E >= 7 → Moyenne
    (2, 2, 2, 1),   -- PHP  : E <= 2 → Petit
    (2, 2, 3, 2);   -- PHP  : E >  2 → Grand

INSERT INTO Note (idMatiere, idCandidat, idCorrecteur, note) VALUES
    (1,1,1,15),(1,1,2,10),(1,1,3,12),
    (1,2,1,9), (1,2,2,8), (1,2,3,11),
    (2,1,1,10),(2,1,2,10),
    (2,2,1,13),(2,2,2,11);


SELECT 'Candidats'   AS table_name, COUNT(*) AS total FROM Candidat
UNION ALL SELECT 'Matières',     COUNT(*) FROM Matiere
UNION ALL SELECT 'Correcteurs',  COUNT(*) FROM Correcteur
UNION ALL SELECT 'Résolutions',  COUNT(*) FROM Resolution
UNION ALL SELECT 'Opérations',   COUNT(*) FROM Operation
UNION ALL SELECT 'Paramètres',   COUNT(*) FROM Parametre
UNION ALL SELECT 'Notes',        COUNT(*) FROM Note
UNION ALL SELECT 'Matière↔Corr', COUNT(*) FROM Matiere_Correcteur
UNION ALL SELECT 'Notes finales',COUNT(*) FROM Note_finale;
