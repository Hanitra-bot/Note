-- ============================================================
-- SCHEMA db_note — version prof
-- ============================================================

CREATE TABLE IF NOT EXISTS Candidat (
    id   SERIAL PRIMARY KEY,
    nom  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Correcteur (
    id   SERIAL PRIMARY KEY,
    nom  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Matiere (
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Matiere_Correcteur (
    idmatiere    INT NOT NULL REFERENCES Matiere(id)    ON DELETE CASCADE,
    idcorrecteur INT NOT NULL REFERENCES Correcteur(id) ON DELETE CASCADE,
    PRIMARY KEY (idmatiere, idcorrecteur)
);

CREATE TABLE IF NOT EXISTS Resolution (
    id      SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Operation (
    id    SERIAL PRIMARY KEY,
    signe VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Note (
    id           SERIAL PRIMARY KEY,
    idMatiere    INT          NOT NULL REFERENCES Matiere(id),
    idCandidat   INT          NOT NULL REFERENCES Candidat(id),
    idCorrecteur INT          NOT NULL REFERENCES Correcteur(id),
    note         NUMERIC(5,2) CHECK (note >= 0 AND note <= 20)
);

CREATE TABLE IF NOT EXISTS Parametre (
    id           SERIAL PRIMARY KEY,
    idMatiere    INT           NOT NULL REFERENCES Matiere(id),
    ecart        NUMERIC(5,2)  NOT NULL,
    idOperation  INT           NOT NULL REFERENCES Operation(id),
    idResolution INT           NOT NULL REFERENCES Resolution(id)
);

CREATE TABLE IF NOT EXISTS Note_finale (
    id         SERIAL PRIMARY KEY,
    idCandidat INT NOT NULL REFERENCES Candidat(id),
    idMatiere  INT NOT NULL REFERENCES Matiere(id),
    note       NUMERIC(5,2) CHECK (note >= 0 AND note <= 20),
    UNIQUE (idCandidat, idMatiere)
);

-- ============================================================
-- VIEWS
-- ============================================================

CREATE OR REPLACE VIEW v_notes_candidat AS
SELECT c.id AS candidat_id, c.nom AS candidat_nom,
       m.id AS matiere_id,  m.libelle AS matiere_libelle,
       n.id AS note_id,     n.note,
       cr.nom AS correcteur_nom
FROM Note n
JOIN Candidat c ON c.id = n.idCandidat
JOIN Matiere  m ON m.id = n.idMatiere
JOIN Correcteur cr ON cr.id = n.idCorrecteur;

CREATE OR REPLACE VIEW v_parametres AS
SELECT p.id, m.libelle AS matiere_libelle,
       p.ecart, o.signe AS operation, r.libelle AS resolution
FROM Parametre p
JOIN Matiere   m ON m.id = p.idMatiere
JOIN Operation o ON o.id = p.idOperation
JOIN Resolution r ON r.id = p.idResolution;

CREATE OR REPLACE VIEW v_notes_finales AS
SELECT nf.id, c.nom AS candidat_nom, m.libelle AS matiere_libelle, nf.note AS note_finale
FROM Note_finale nf
JOIN Candidat c ON c.id = nf.idCandidat
JOIN Matiere  m ON m.id = nf.idMatiere;

CREATE OR REPLACE VIEW v_matiere_correcteurs AS
SELECT m.id AS matiere_id, m.libelle AS matiere_libelle,
       c.id AS correcteur_id, c.nom AS correcteur_nom
FROM Matiere_Correcteur mc
JOIN Matiere    m ON m.id = mc.idmatiere
JOIN Correcteur c ON c.id = mc.idcorrecteur;
