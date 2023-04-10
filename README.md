<div align="center">
    <img src="http://boilerconnect.ddns.net/logo.png" alt="Logo" width="100" height="100">

  <h3 align="center">BoilerConnect</h3>

  <p align="center">
    Système de gestion de rapports d'intervention pour chaudières
    <br />
    <a href="https://drive.google.com/drive/folders/1pocVKzrKxUJeOKoWazV7Zrlh0SxABBp_?usp=share_link"><strong>Documentation</strong></a>
    <br />
    <br />
    <p align="center">Supports</p>
    <a href="https://github.com/AimFried/boilerconnect_web">Web</a>
    ·
    <a href="https://github.com/AimFried/boilerconnect_api">API</a>
    ·
    <a href="https://github.com/AimFried/boilerconnect_apk">Android</a>
  </p>
</div>

# Version Android

Cette version permet l'enregistrement et l'envoi de rapport d'intervention.

## 📦 Prérequis

1. Avoir au préalable installé l'<a href="https://github.com/AimFried/boilerconnect_api">API</a> BoilerConnect. <br >
2. Avoir installé la version <a href="https://github.com/AimFried/boilerconnect_web">Web</a>.

## :bookmark_tabs: Installation

1. Télécharger le dépôt <br >
2. Deux choix possible pour utiliser l'application Android
   * Télécharger l'APK
   ```bash
   http://boilerconnect.ddns.net/BoilerConnect.apk
   ```
      ou
      
   * Générer l'APK sur Android Studio

## 🛠️ Configuration

Connection à l'API : En fonction de vos besoins, vous pouvez changer l'url utilisée.
(par défaut: boilerconnect.ddns.net)

* Nom de domaine.
   ```bash
   /app/src/main/res/values/string/xml
   ```
* Autorisation nom de domaine.
   ```bash
   /app/src/main/res/values/xml/network_security_config.xml
    ```
 
## 🤠 Auteur

- [@aimfried](https://www.github.com/AimFried])
