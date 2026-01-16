# PowerCity Manager 🏙️⚡

**Jeu de gestion énergétique de type tycoon - Projet INF2328**

## Description

PowerCity Manager est un jeu de gestion où le joueur incarne un gestionnaire de production et de distribution d'énergie dans une ville virtuelle. Le but est de maintenir un approvisionnement énergétique stable tout en assurant la satisfaction des habitants.

### Scénario

Vous gérez l'électricité de **NeoVille**, une petite ville en développement. Les habitants vivent dans des résidences de différents niveaux, avec des besoins énergétiques et un pouvoir d'achat variables. Votre mission : construire et améliorer des centrales électriques pour répondre à la demande croissante, tout en maintenant le bonheur de la population au-dessus d'un seuil critique.

**Attention** : Si le niveau de satisfaction descend trop bas, le maire vous révoquera de vos fonctions !

## Fonctionnalités

### Mécaniques de jeu

- **Production d'énergie** : Construisez 5 types de centrales (Charbon, Solaire, Éolienne, Nucléaire, Hydraulique)
- **Amélioration** : Augmentez le niveau de vos centrales pour produire plus d'énergie
- **Maintenance** : Entretenez vos installations pour éviter les pannes
- **Gestion financière** : Équilibrez vos dépenses et vos revenus de la vente d'électricité
- **Satisfaction des habitants** : Maintenez le bonheur de la population en assurant l'approvisionnement
- **Croissance de la ville** : De nouvelles résidences apparaissent si la gestion est bonne
- **Cycles de jeu** : Le jeu progresse par cycles simulant le passage du temps

### Caractéristiques techniques

- **Architecture MVC** : Modèle-Vue-Contrôleur strictement respectée
- **Interface Swing** : Interface graphique native Java (aucune dépendance externe)
- **Pattern Observer** : Communication Modèle-Vue via listeners
- **Valeurs aléatoires** : Consommation et pouvoir d'achat variables pour éviter la répétitivité
- **Système de progression** : Pas de victoire immédiate, objectif de durabilité

## Types de centrales

| Type | Coût | Production | Score environnemental | Description |
|------|------|------------|----------------------|-------------|
| 🏭 **Charbon** | 500 € | 100 kWh/cycle | 30% | Polluante mais fiable |
| ☀️ **Solaire** | 300 € | 50 kWh/cycle | 90% | Énergie propre du soleil |
| 💨 **Éolienne** | 400 € | 70 kWh/cycle | 80% | Vent capricieux mais écologique |
| ⚛️ **Nucléaire** | 2000 € | 500 kWh/cycle | 50% | Puissante mais coûteuse |
| 💧 **Hydraulique** | 800 € | 200 kWh/cycle | 70% | Force de l'eau |

## Niveaux de résidences

| Niveau | Nom | Consommation | Pouvoir d'achat |
|--------|-----|--------------|-----------------|
| 1 | Cabane Modeste | 10-20 kWh | 0.5-1.5 €/kWh |
| 2 | Maison Confortable | 20-40 kWh | 1.0-3.0 €/kWh |
| 3 | Villa Luxueuse | 30-60 kWh | 1.5-4.5 €/kWh |
| 4 | Penthouse Prestigieux | 40-80 kWh | 2.0-6.0 €/kWh |

## Installation et exécution

### Prérequis

- **Java JDK 11** ou supérieur
- **Aucune dépendance externe** (utilise Swing inclus dans Java)

### Compilation et exécution (Simple)

#### Windows - Double-clic
1. Double-cliquez sur `run.bat`
2. Le jeu compilera et se lancera automatiquement

#### Windows - PowerShell
```powershell
.\run.ps1
```

#### Ligne de commande manuelle

**Compilation:**
```bash
javac -d out -encoding UTF-8 src\main\java\com\powercity\model\*.java src\main\java\com\powercity\controller\*.java src\main\java\com\powercity\view\*.java src\main\java\com\powercity\*.java
```

**Exécution:**
```bash
java -cp out com.powercity.Main
```

### Alternative : Utiliser un IDE

#### IntelliJ IDEA
1. File → Open → Sélectionner le dossier du projet
2. Clic droit sur `Main.java` → Run 'Main.main()'

#### Eclipse
1. File → Open Projects from File System
2. Sélectionner le dossier du projet
3. Clic droit sur `Main.java` → Run As → Java Application

#### VS Code
1. Ouvrir le dossier du projet
2. Installer l'extension "Extension Pack for Java"
3. Ouvrir `Main.java` et cliquer sur "Run"

## Structure du projet

```
src/main/java/com/powercity/
├── Main.java                           # Point d'entrée
├── model/                              # Modèle (logique métier)
│   ├── City.java                       # Représente la ville
│   ├── PowerPlant.java                 # Centrale électrique
│   ├── PowerPlantType.java             # Types de centrales (enum)
│   ├── Residence.java                  # Résidence
│   └── ResidenceLevel.java             # Niveaux de résidences (enum)
├── view/                               # Vue (interface graphique)
│   └── GameView.java                   # Interface JavaFX principale
└── controller/                         # Contrôleur
    └── GameController.java             # Logique de contrôle MVC
```

## Architecture MVC

### Modèle (`model/`)
Contient toute la logique métier du jeu :
- Gestion des résidences et de leur satisfaction
- Production d'énergie des centrales
- Distribution de l'énergie et calcul des revenus
- Croissance de la ville et évolution

### Vue (`view/`)Swing :
- Affichage des statistiques (argent, bonheur, énergie)
- Listes des centrales et résidences
- Boutons d'action et interactions utilisateur
- Barres de progression visuelles
- Look and Feel natif du système d'exploitations utilisateur
- Barres de progression visuelles

### Contrôleur (`controller/`)
Intermédiaire entre le modèle et la vue :
- Traite les actions du joueur
- Met à jole pattern Observer pour notifier la vue des changement
- Utilise des propriétés observables JavaFX pour la liaison de données
- Gère la logique de progression du jeu

## Guide de jeu

### Démarrage
Vous commencez avec :
- **2000 €** de budget
- **7 résidences** (5 basiques, 2 moyennes)
- **1 centrale à charbon** niveau 1

### Stratégie recommandée
1. **Équilibrez production et demande** : Assurez-vous d'avoir assez d'énergie pour tous
2. **Diversifiez vos sources** : Combinez différents types de centrales
3. **Maintenez vos installations** : Les centrales peuvent tomber en panne
4. **Surveillez le bonheur** : C'est la métrique critique !
5. **Investissez pour l'avenir** : Préparez-vous à la croissance de la ville

### Conditions de défaite
- Bonheur moyen < 20%
- Dette > 1000 €

## Éléments de lore

NeoVille est une ville écologique expérimentale où vous avez été nommé directeur de l'énergie. Les habitants sont particulièrement exigeants et n'hésiteront pas à vous remplacer si vous ne répondez pas à leurs besoins. La ville attire de nouveaux résidents lorsque la qualité de vie est élevée, créant un cycle de croissance que vous devez anticiper.

Le maire surveille de près votre performance et évalue régulièrement la satisfaction de la population. Attention : un mandat mal géré se termine rapidement dans NeoVille !

## Auteur

Projet développé pour le cours **INF2328** - Jeu de gestion de type tycoon

## Licence

Projet académique - 2026
