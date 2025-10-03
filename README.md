# Guide de déploiement de l'ensemble des applications Java

Guide pour déployer l'API, l'Application et le Batch.

---

## Prérequis

- **Java 17** : Assurez-vous que la version JDK 17 est installée.
- **Base de données** : Une base de données PostgreSQL. Les scripts SQL nécessaires sont fournis (voir `src/main/resources/db`).
- Les fichiers `.jar` générés des 3 applications. 

---

## Étapes de déploiement

### 1. Préparer la base de données

1. Importez les scripts SQL fournis :
   - Créez la base de données sous le nom `dsibiblio` avec le port **5432**.
   - Exécutez les scripts disponibles dans `src/main/resources/db` pour créer les tables et insérer les données de démonstration.

2. Configurez l'application pour se connecter à la base de données :
   - Modifiez le fichier `application.properties` ou un autre fichier de configuration de l'api avec les paramètres de connexion (URL, utilisateur, mot de passe).

### 2. Installer les applications

Les applications possèdent une intégration de serveur Apache Tomcat. Un simple lancement du jar suffit à lancer chacune des applications.
Lancer chacun des fichiers avec la commande suivante : `java -jar <nom du fichier>.jar`

- L'API tourne sur le port 8080.
- Le batch tourne sur le port 3001.
- L'application tourne sur le port 3000 et est donc accessible à l'adresse `http://localhost:3000/`. L'application ne peut pas fonctionner correctement si l'API n'est pas lancée.
