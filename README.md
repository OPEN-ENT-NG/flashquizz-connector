# Flashquizz

# À propos de l'application Flashquizz
* Licence : [AGPL v3](http://www.gnu.org/licenses/agpl.txt) - Copyright CGI
* Développeur : CGI
* Financeurs : CRNA
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
  "structures": [
    {
      "id": "struct-xyz789",
      "name": "Collège Victor Hugo",
      "uai": "0751234A"
    }
  ],
  "classes": [
    {
      "id": "class-def456",
      "name": "4ème A"
    },
    {
      "id": "class-ghi789",
      "name": "3ème B"
    }
  ],
  "workflowRights": [
    {
      "workflowId": "wf-flashquizz-001",
      "workflowName": "flashquizz",
      "rights": ["create", "read", "update", "delete"],
      "scope": ["struct-xyz789"]
    }
  ]
}
```

## Configuration
Pas de configuration ou de variable d'environnement spécifique