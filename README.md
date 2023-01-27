# ChatSystem Project
Thomas Cayla-Ginestet et Marie Mecaliff, 4IR-SI B, 2022/2023
## Présentation
L'objectif de ce projet était de développer une application de clavardage, devant permettre à plusieurs utilisateurs connectés sur un même réseau local de communiquer en s'envoyant des messages texte. Ainsi, les fonctionnalités suivantes sont inclues dans notre application :
* une visibiltié constante sur la **liste des utilisateurs actuellement connectés** 
* l'impossibilité de **choisir un pseudonyme** déjà pris par un utilisateur actuellement connecté, ou un pseudonyme comportant un espace ou des caractères spéciaux
* la possibilité de **changer ce pseudonyme à n'importe quel moment**, tant qu'il respecte les conditions précédentes, en faisant en sorte que tous les autres utilisateurs constate immédiatement ce changement
* l'**ouverture d'une session de chat** avec un des utilisateurs connectés, et la **récupération de l'historique** des messages avec cet utilisateur à l'ouverture de la conversation
* les messages échangés sont conservés dans une **base de donnée décentralisée**
* l'affichage de l'**horodatage** de chaque message échangé
## Aspect technique
* **configuration** : ce projet est un projet maven qui peut être importé et exécuté en se basant uniquement sur le pom.xml
* **organisation du code** : l'organisation de notre code se fait en différents packages, basés sur les différentes fonctionnalités principales, fin de limiter les dépendances entre les packages (un [package data](/src/main/java/data/) pour le modèle, un [package udp](/src/main/java/udp/) et [tcp](/src/main/java/tcp/) pour les protocoles de communications respectifs, un package [service](/src/main/java/service/) pour les Controllers, et enfin un package spécifique à la [base de données](/src/main/java/bdd/) et un aux Controllers de l'[interface](/src/main/java/frontend/))
* **interface graphique** : nous avons choisi d'utiliser JavaFX, pour ses nombreuses fonctionnalités d'affichage et ses visuels relativement plus modernes 
* **thread safety** : aucun deadlock n'a été laissé sur les threads qui ne doivent pas être bloquants
* **tests** : des tests unitaires Junit ont été implantés pour chaque fonctionnalité principale du système 
* **observer design pattern** : ce design pattern a été implémenté notamment sur les serveurs TCP et UDP, à travers les interfaces Notify et MessageReceivedCallback
* **intégration continue** : implémentée sur jenkins
## Utilisation
Il est nécessaire que ```git``` soit installés sur votre machine pour utiliser l'application depuis un clone de ce repository. Il faut alors suivre les étapes suivantes :
```
# télécharger le dossier d'installation comportant un dossier lib et l'exécutable .jar de notre projet au lien suivant :
(https://drive.google.com/drive/folders/16m_NDXzv3Jle0gjDyiYlzINH4TOQL3lS?usp=sharing)
# lancer l'application
$ java --module-path ./lib --add-modules javafx.controls,javafx.fxml -jar projetMiaou.jar
```
