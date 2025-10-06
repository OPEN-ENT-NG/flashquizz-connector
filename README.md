# Flashquizz

# À propos de l'application Flashquizz
* Licence : [AGPL v3](http://www.gnu.org/licenses/agpl.txt) - Copyright CGI
* Développeur : CGI
* Financeurs : VDP / NA 77
* Description : Connecteur permettant d'alimenter Flashquizz

# Présentation du module
L'application **Flashquizz** est un connectreur permettant de fournir de façon efficace les informations nécessaires au bon fonctionnement de l'application **Flashquizz**
Les infos en question sont du type :
```
{
  "id": "user-abc123",
  "login": "jean.dupont",
  "displayName": "Jean Dupont",
  "email": "jean.dupont@college.fr",
  "profile": "Teacher",
  "workflowRights": {
    "hasQuizzView": true,
    "hasQuizzGestion": true,
    "hasGameView": false,
    "hasGameGestion": false
  }
}
```

## Configuration
Pas de configuration ou de variable d'environnement spécifique