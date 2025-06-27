# RecipeSwiper Frontend - Projektstruktur für KI-Analyse

## Projektübersicht

**Typ:** Angular 17+ Frontend-Anwendung  
**Zweck:** Recipe-Swiping-App mit Gruppenmanagement  
**Technologie-Stack:** Angular, TypeScript, Tailwind CSS, Docker  
**Architektur:** Standalone Components, Feature-basierte Modulstruktur

## Hauptverzeichnisse

### `/src/app/` - Hauptanwendungslogik

#### `core/` - Kernfunktionalitäten

```
core/
├── guards/
│   └── auth.guard.ts              # Authentifizierungs-Schutz für Routen
├── models/                        # Datenmodelle und DTOs
│   ├── dto/                       # Data Transfer Objects
│   │   ├── group.ts              # Gruppen-Datenstruktur
│   │   ├── user.ts               # Benutzer-Datenstruktur
│   │   ├── request-create-user.ts # User-Erstellungs-Request
│   │   ├── request-join.ts       # Gruppen-Beitritts-Request
│   │   └── vote-request.ts       # Voting-Request
│   ├── Recipe.ts                 # Rezept-Modell
│   ├── RecipeResult.ts           # Rezept-Ergebnis-Modell
│   ├── VoteResult.ts             # Voting-Ergebnis
│   └── VoteType.ts               # Voting-Typen (Enum)
└── services/                     # Globale Services
    ├── error-popup.service.ts    # Fehlerbehandlung und Popups
    ├── recipeswiper.service.ts   # Hauptservice für API-Kommunikation
    └── user.service.ts           # Benutzer-Management und Authentifizierung
```

#### `features/` - Feature-Module (Domain-driven Design)

```
features/
├── group/                        # Gruppenmanagement
│   ├── create-group/            # Gruppen erstellen
│   ├── group-card/              # Gruppen-Anzeigekomponente
│   ├── group-list/              # Gruppenliste
│   ├── group-management/        # Gruppenverwaltung
│   ├── group-overview/          # Gruppenübersicht
│   ├── invitemember/            # Mitglieder einladen
│   └── join-group/              # Gruppe beitreten
├── recipe/                       # Rezept-Features
│   ├── browse-recipe-list/      # Rezepte durchsuchen
│   ├── new-recipe/              # Neues Rezept erstellen
│   ├── recipe-card/             # Rezept-Karte (groß)
│   ├── recipe-card-small/       # Rezept-Karte (klein)
│   ├── recipe-full/             # Vollständige Rezeptansicht
│   ├── recipe-list/             # Rezeptliste
│   ├── recipe-swipe/            # Swipe-Funktionalität
│   └── vote-bar/                # Voting-Interface
├── user/                         # Benutzerverwaltung
│   ├── create-user/             # Benutzer registrieren
│   └── user-profile/            # Benutzerprofil
└── voting/                       # Voting-System
    └── vote-summary/            # Voting-Zusammenfassung
```

#### `pages/` - Seitenkomponenten (Container)

```
pages/
├── group-page/                   # Gruppenseite (Container)
├── home-page/                    # Startseite
├── recipe-page/                  # Rezeptseite (Container)
├── recipe-view/                  # Einzelrezept-Ansicht
├── user-page/                    # Benutzerseite
└── recipeswiper-routing.ts       # Routing-Konfiguration
```

#### `shared/` - Wiederverwendbare Komponenten

```
shared/
└── components/
    ├── error-popup/              # Globale Fehler-Popups
    ├── header/                   # Anwendungsheader mit Navigation
    └── invite-popup/             # Einladungs-Popup
```

### Konfigurationsdateien (Root-Level)

- `angular.json` - Angular CLI Konfiguration
- `package.json` - NPM Dependencies und Scripts
- `tailwind.config.js` - Tailwind CSS Konfiguration
- `tsconfig.json` - TypeScript Konfiguration
- `Dockerfile` - Container-Konfiguration
- `default.conf` - Nginx-Konfiguration für Production

## Architektur-Prinzipien

### 1. Feature-basierte Struktur

- Jedes Feature ist in sich geschlossen
- Klare Trennung von Business-Logik
- Wiederverwendbare Komponenten in `shared/`

### 2. Angular Standalone Components

- Moderne Angular-Architektur ohne NgModules
- Direkte Imports in Komponenten
- Bessere Tree-Shaking-Unterstützung

### 3. Service-orientierte Architektur

- `core/services/` für globale Business-Logik
- Dependency Injection für lose Kopplung
- Observable-basierte State-Management

### 4. Typisierung mit TypeScript

- Starke Typisierung durch DTOs in `core/models/dto/`
- Interface-Definition für API-Kommunikation
- Type-Safety für bessere Entwicklererfahrung

## Wichtige Erkenntnisse für KI

### Hauptfunktionalitäten

1. **Rezept-Swiping:** Tinder-ähnliche Rezeptauswahl
2. **Gruppenmanagement:** Gemeinsame Rezeptauswahl in Gruppen
3. **Voting-System:** Abstimmung über Rezepte
4. **Benutzerauthentifizierung:** Login/Logout-System

### Datenfluss

1. `user.service.ts` - Authentifizierung und Benutzerverwaltung
2. `recipeswiper.service.ts` - API-Kommunikation
3. `error-popup.service.ts` - Globale Fehlerbehandlung

### UI/UX Konzepte

- Mobile-First Design (Tailwind CSS)
- Responsive Komponenten
- Swipe-Gesten für Rezeptauswahl
- Modal-Popups für Interaktionen

### Routing-Struktur

- `/recipeswiper/home` - Startseite
- `/recipeswiper/group/{token}` - Gruppenseiten
- `/recipeswiper/user` - Benutzerprofil
- Guards für Authentifizierung

## Entwicklungshinweise für KI

- Komponenten sind stark typisiert und verwenden Interfaces
- Services verwenden RxJS Observables für reaktive Programmierung
- Styling erfolgt durch Tailwind CSS-Klassen
- Standalone Components erfordern explizite Imports
- Error-Handling erfolgt zentral über `error-popup.service.ts`
