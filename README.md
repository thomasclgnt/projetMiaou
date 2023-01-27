# ChatSystem Project
Thomas Cayla-Ginestet et Marie Mecaliff, 4IR-SI B, 2022/2023
## Présentation
L'objectif de ce projet était de développer une application de clavardage, devant permettre à plusieurs utilisateurs connectés sur un même réseau local de communiquer en s'envoyant des messages texte. Ainsi, les fonctionnalités suivantes sont inclues dans notre application
* une visibiltié constante sur la **liste des utilisateurs actuellement connectés** 
* l'impossibilité de **choisir un pseudonyme** déjà pris par un utilisateur actuellement connecté, ou un pseudonyme comportant un espace ou des caractères spéciaux
* la possibilité de **changer ce pseudonyme à n'importe quel moment**, tant qu'il respecte les conditions précédentes, en faisant en sorte que tous les autres utilisateurs constate immédiatement ce changement
* l'**ouverture d'une session de chat** avec un des utilisateurs connectés, et la **récupération de l'historique** des messages avec cet utilisateur à l'ouverture de la conversation
* les messages échangés sont conservés dans une **base de donnée décentralisée**
* l'affichage de l'**horodatage** de chaque message échangé
## Aspect technique
* **configuration** : ce projet est un projet maven qui peut être importé et exécuté en se basant uniquement sur le pom.xml
* **organisation du code** : l'organisation de notre code se fait en différents packages, basés sur les différentes fonctionnalités principales, fin de limiter les dépendances entre les packages (un package data pour le modèle, un package udp et tcp pour les protocoles de communications respectifs, un package service pour les Controller, et enfin un package spécifique à la base de données)
* **interface graphique** : nous avons choisi d'utiliser JavaFX, pour ses nombreuses fonctionnalités d'affichage et ses visuels relativement plus modernes 
* **thread safety** : aucun deadlock n'a été laissé sur les threads qui ne doivent pas être bloquants
* **tests** : des tests unitaires Junit ont été implantés pour chaque fonctionnalité principale du système 
* **observer design pattern** : ce design pattern a été implémenté notamment sur les serveurs TCP et UDP, à travers les interfaces Notify et MessageReceivedCallback
* **intégration continue** : implémentée sur jenkins
## Utilisation
Il est nécessaire que ??? soient installés sur votre machine pour utiliser l'application. Il faut alors cloner le projet sur la machine et se placer dans ./projetMiaou avant d'exécuter :
```
$ git clone https://example.com
$ cd ../path/to/the/file
$ npm install
$ npm start BLABLA À COMPLÉTER AVEC NOTRE PROCÉDURE
```
Side information: To use the application in a special environment use ```lorem ipsum``` to start BLABLA
