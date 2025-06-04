# RecipeSwiper Frontend

Eine moderne Angular-Anwendung für das Swipen und Teilen von Rezepten mit Freunden.

## 🚀 Features

- **Modernes UI Design** mit Tailwind CSS
- **Responsive Design** für alle Geräte
- **Recipe Swiping** - Like oder Dislike Rezepte
- **Gruppen-Funktionalität** - Erstelle oder trete Gruppen bei
- **Ergebnisse anzeigen** - Sehe welche Rezepte von der Gruppe gemocht wurden
- **Benutzer-Management** - Erstelle und verwalte Benutzerprofile

## 🎨 Design

Das Frontend wurde komplett neu gestaltet mit:

- Dunkler Header mit Benutzer-Icon
- Moderne Karten-basierte UI
- Schöne Buttons und Eingabefelder
- Responsive Layout
- Smooth Transitions und Hover-Effekte

## 📱 Seiten

1. **Home Page** (`/recipeswiper/home`) - Gruppe beitreten oder erstellen
2. **Recipe Page** (`/recipeswiper/recipe/:groupToken`) - Rezepte swipen
3. **Group Page** (`/recipeswiper/group/:groupToken`) - Gruppenergebnisse anzeigen
4. **User Page** (`/recipeswiper/user/:userToken`) - Benutzerprofil
5. **Create User Page** (`/recipeswiper/create-user`) - Neuen Benutzer erstellen

## 🛠️ Installation

```bash
# Dependencies installieren
npm install

# Development Server starten
npm start

# Build für Production
npm run build
```

## 🎯 Verwendung

1. Starte die Anwendung mit `npm start`
2. Navigiere zu `http://localhost:4200`
3. Erstelle einen neuen Benutzer oder verwende einen bestehenden
4. Erstelle eine neue Gruppe oder trete einer bestehenden bei
5. Swipe durch Rezepte (Like/Dislike)
6. Schaue dir die Gruppenergebnisse an

## 🔧 Technologien

- **Angular 19** - Frontend Framework
- **Tailwind CSS** - Styling
- **TypeScript** - Programmiersprache
- **RxJS** - Reactive Programming

## 📦 Projektstruktur

```
src/app/
├── core/
│   ├── models/          # Datenmodelle
│   └── services/        # Services für API-Calls
├── pages/               # Haupt-Seiten
│   ├── home-page/
│   ├── recipe-page/
│   ├── group-page/
│   ├── user-page/
│   └── create-user-page/
└── shared/
    └── components/      # Wiederverwendbare Komponenten
        └── header/
```

## 🎨 Design System

Das Design verwendet ein konsistentes Farbschema:

- **Primary**: Grau-Töne für Header und Buttons
- **Secondary**: Blau für Akzente
- **Success**: Grün für Like-Buttons
- **Danger**: Rot für Dislike-Buttons

## 🔗 API Integration

Die Anwendung ist vorbereitet für die Integration mit dem Backend:

- Base URL: `http://localhost:9090/api/recipeswiper`
- Alle Services sind implementiert
- Mock-Daten für Entwicklung verfügbar
