# PowerCity Manager - Guide de compilation sans Maven

## Option 1 : Utiliser IntelliJ IDEA ou Eclipse

### IntelliJ IDEA (Recommandé)
1. Ouvrir IntelliJ IDEA
2. File → Open → Sélectionner le dossier `projet_ditoma_java`
3. Le fichier `pom.xml` sera détecté automatiquement
4. IntelliJ va télécharger automatiquement les dépendances JavaFX
5. Attendre que l'indexation se termine
6. Clic droit sur `Main.java` → Run

### Eclipse
1. Ouvrir Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Sélectionner le dossier `projet_ditoma_java`
4. Eclipse va télécharger les dépendances
5. Clic droit sur le projet → Run As → Java Application

## Option 2 : Installer Maven

### Installation Maven sur Windows
1. Télécharger Maven depuis : https://maven.apache.org/download.cgi
2. Extraire l'archive dans `C:\Program Files\Apache\maven`
3. Ajouter au PATH :
   - Variables d'environnement → Path → Ajouter `C:\Program Files\Apache\maven\bin`
4. Redémarrer PowerShell
5. Exécuter : `mvn clean install`

## Option 3 : Compilation manuelle avec JavaFX

Si JavaFX est installé localement, compiler avec :

```bash
# Windows PowerShell
javac --module-path "CHEMIN_VERS_JAVAFX\lib" --add-modules javafx.controls,javafx.fxml -d out src\main\java\com\powercity\**\*.java

java --module-path "CHEMIN_VERS_JAVAFX\lib" --add-modules javafx.controls,javafx.fxml -cp out com.powercity.Main
```

## Option 4 : Version Swing (sans JavaFX)

Si vous préférez éviter JavaFX, je peux créer une version avec Swing qui ne nécessite aucune dépendance externe.

## Recommandation

**Utilisez IntelliJ IDEA Community Edition** (gratuit) :
- Il gère automatiquement Maven
- Télécharge les dépendances JavaFX
- Meilleur support pour les projets Java modernes
- Téléchargement : https://www.jetbrains.com/idea/download/
