# Note Finale - Spring Boot API

Système de gestion et de calcul automatique des notes finales des étudiants.

## Prérequis

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

## Configuration

Éditer `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_note
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Démarrage

```bash
mvn spring-boot:run
```

L'API sera disponible sur `http://localhost:8080`

---

## Données initiales recommandées

```sql
-- Opérateurs
INSERT INTO Operateur (nom) VALUES ('<'), ('<='), ('>'), ('>='), ('=');

-- Résolutions
INSERT INTO Resolution (nom) VALUES ('plus grand'), ('plus petit'), ('moyenne');
```

---

## Règle de calcul de la note finale

1. Récupérer toutes les notes de l'étudiant pour la matière donnée
2. Calculer `total_diff` = somme des différences absolues entre chaque paire de notes
   - Ex: notes [12, 15, 10] → diff(12,15)=3 + diff(12,10)=2 + diff(15,10)=5 → total_diff=10
3. Comparer `total_diff` à `parametre.difference` avec `operateur`
   - Ex: parametre: diff=6, opérateur=`<`, résolution=`plus grand`
   - Si total_diff < 6 → on prend la **plus grande note**
4. Sauvegarder le résultat dans la table `Note_finale`

---

## Endpoints API

### 🎯 Note Finale (principal)

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/note-finale/calculer?etudiantId=1&matiereId=2` | Calcule sans sauvegarder |
| POST | `/api/note-finale/sauvegarder?etudiantId=1&matiereId=2` | Calcule et sauvegarde |
| GET | `/api/note-finale` | Toutes les notes finales sauvegardées |
| GET | `/api/note-finale/etudiant/{id}` | Notes finales d'un étudiant |
| GET | `/api/note-finale/matiere/{id}` | Notes finales d'une matière |
| DELETE | `/api/note-finale/{id}` | Supprime une note finale |

#### Exemple de réponse `/calculer` :

```json
{
  "etudiantId": 1,
  "etudiantNom": "RAKOTO",
  "etudiantPrenom": "Jean",
  "matiereId": 2,
  "matiereNom": "SVT",
  "coefficient": 3,
  "notes": [12.00, 15.00, 10.00],
  "details": [
    {"note1": 12.00, "note2": 15.00, "difference": 3.00},
    {"note1": 12.00, "note2": 10.00, "difference": 2.00},
    {"note1": 15.00, "note2": 10.00, "difference": 5.00}
  ],
  "totalDiff": 10.00,
  "parametreDifference": 6.00,
  "operateur": "<",
  "resolution": "plus grand",
  "parametreApplicable": false,
  "noteMax": 15.00,
  "noteMin": 10.00,
  "noteMoyenne": 12.33,
  "noteFinaleCalculee": null,
  "sauvegardee": false
}
```

---

### 👥 Étudiants

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/etudiants` | Liste tous les étudiants |
| GET | `/api/etudiants?search=rakoto` | Recherche par nom/prénom |
| GET | `/api/etudiants/{id}` | Détail d'un étudiant |
| POST | `/api/etudiants` | Créer un étudiant |
| PUT | `/api/etudiants/{id}` | Modifier un étudiant |
| DELETE | `/api/etudiants/{id}` | Supprimer un étudiant |

Body POST/PUT :
```json
{ "nom": "RAKOTO", "prenom": "Jean", "email": "jean@mail.mg" }
```

### 📝 Notes

| Méthode | URL |
|---------|-----|
| GET | `/api/notes` |
| GET | `/api/notes/{id}` |
| GET | `/api/notes/etudiant/{etudiantId}/matiere/{matiereId}` |
| POST | `/api/notes` |
| PUT | `/api/notes/{id}` |
| DELETE | `/api/notes/{id}` |

Body POST/PUT :
```json
{ "idMatiere": 1, "idEtudiant": 1, "idCorrecteur": 1, "note": 14.50 }
```

### 📚 Matières

| Méthode | URL |
|---------|-----|
| GET/POST | `/api/matieres` |
| GET/PUT/DELETE | `/api/matieres/{id}` |

### 🔧 Paramètres

| Méthode | URL |
|---------|-----|
| GET/POST | `/api/parametres` |
| GET | `/api/parametres/matiere/{matiereId}` |
| GET/PUT/DELETE | `/api/parametres/{id}` |

Body POST :
```json
{ "idMatiere": 1, "difference": 6.00, "idOperateur": 1, "idResolution": 1 }
```

### Autres CRUD

- `/api/correcteurs` - CRUD correcteurs
- `/api/operateurs` - CRUD opérateurs (`<`, `<=`, `>`, `>=`, `=`)
- `/api/resolutions` - CRUD résolutions (`plus grand`, `plus petit`, `moyenne`)

---

## Views PostgreSQL créées

| View | Description |
|------|-------------|
| `v_notes_etudiant` | Toutes les notes avec infos complètes |
| `v_parametres` | Paramètres lisibles (noms au lieu d'IDs) |
| `v_note_finale_calculee` | Note finale calculée dynamiquement |
| `v_notes_finales` | Notes finales sauvegardées avec détails |
| `v_bulletin` | Bulletin complet avec moyenne générale pondérée |

```sql
-- Exemple d'utilisation des views
SELECT * FROM v_note_finale_calculee WHERE etudiant_id = 1 AND matiere_id = 2;
SELECT * FROM v_bulletin WHERE etudiant_id = 1;
```
