# Projet Démo : Concepts de Base Angular 19

## 📝 Description du Projet
Ce projet est une application web de type **Single Page Application (SPA)** développée pour assimiler les concepts fondamentaux d'Angular 19. L'application illustre la création d'une partie frontend dynamique avec un système de navigation (Home et Products), la gestion d'une liste de produits (affichage conditionnel, suppression) et la communication avec un backend (API REST) via des requêtes HTTP.

## 🛠️ Technologies Utilisées
*   **Frontend :** Angular 19, TypeScript, HTML5, CSS3.
*   **Design/UI :** Bootstrap et Bootstrap Icons.
*   **Backend (ciblé) :** Spring Boot avec Spring Data (sur le port 8083).

## ⚙️ Prérequis et Commandes CLI
*   **Node.js & npm** : Nécessaires pour gérer les dépendances du projet.
*   **Angular CLI** : Installé globalement avec `npm install -g @angular/cli` pour générer et gérer le projet.
*   `ng new <nom-projet>` : Pour générer un nouveau projet Angular.
*   `ng serve` : Pour compiler et lancer le serveur de développement local sur le port 4200.
*   `ng generate component <nom>` (ou `ng g c`) : Pour générer de nouveaux web components.
*   `ng generate service <nom>` : Pour créer des services applicatifs.

---

## 📚 Notions Apprises et Implémentées

### 1. Architecture Standalone (Sans Module)
Historiquement basé sur le concept de modules (`app.module.ts`), Angular s'oriente désormais vers les **Standalone Components**. Depuis la version 17 (et par défaut en version 19), les composants sont générés de manière autonome avec la propriété `standalone: true`, ce qui simplifie grandement l'architecture en évitant de déclarer chaque composant dans un module global. La configuration (comme le routeur ou le client HTTP) se fait directement dans le fichier `app.config.ts`.

### 2. Composants Web et Pattern MVVM
Une application Angular est une hiérarchie de composants imbriqués (ex: `app`, `home`, `products`). Angular utilise le modèle **MVVM (Model-View-ViewModel)** pour séparer la logique et l'affichage. Un composant généré contient 4 fichiers :
*   **`.ts`** : La classe TypeScript (le Modèle), contenant les données et la logique applicative.
*   **`.html`** : Le template (la Vue), responsable de l'affichage.
*   **`.css`** : Les styles isolés et spécifiques à ce composant.
*   **`.spec.ts`** : Le fichier dédié aux tests unitaires avec le framework Jasmine.

### 3. Data Binding (Liaison de Données)
La synchronisation entre les variables TypeScript et le HTML se fait via le mécanisme de **Data Binding**.
*   **String Interpolation :** Affichage unidirectionnel d'une donnée du modèle vers la vue avec les doubles accolades `{{ variable }}`.
*   **Event Binding :** Écoute d'événements déclenchés par l'utilisateur dans la vue, par exemple `(click)="handleDelete(p)"` pour appeler une méthode du modèle lors d'un clic.

### 4. Le Nouveau Moteur de Template (Control Flow)
Depuis la version 17, Angular a introduit une nouvelle syntaxe de template plus intuitive, remplaçant les anciennes directives structurelles (`*ngIf`, `*ngFor`) :
*   **Boucles :** Utilisation de `@for (p of products; track p.id)` pour parcourir efficacement une liste.
*   **Conditions :** Utilisation de `@if (condition) { ... } @else { ... }` pour afficher des éléments selon l'état des données (ex: afficher une icône différente si un produit est sélectionné ou non).

### 5. Système de Routage
Pour naviguer entre les vues sans recharger toute la page web :
*   **Déclaration :** Les routes sont définies dans `app.routes.ts` (ex: associer le chemin `'home'` au `HomeComponent`).
*   **Navigation :** La directive `routerLink="/chemin"` est utilisée sur les boutons de navigation.
*   **Affichage :** La balise dynamique `<router-outlet>` définit la zone où le composant correspondant à la route active sera injecté.

### 6. Services et Injection de Dépendances
Pour éviter qu'un composant ne devienne trop complexe et pour faciliter le partage de données/traitements entre plusieurs composants distants, la logique métier est externalisée dans des **Services**.
*   Un service est instancié une seule fois et partagé.
*   Angular utilise le principe de **l'Injection de Dépendances** : il suffit de déclarer le service dans le `constructor(private productService: ProductService)` d'un composant pour pouvoir l'utiliser.

### 7. Communication HTTP et Observables (RxJS)
Pour interagir avec le backend Spring Boot, le projet utilise le module `HttpClient` (fourni via `provideHttpClient` dans `app.config.ts`).
*   JavaScript étant de nature *single-thread*, faire attendre l'interface pendant une requête réseau bloquerait l'application.
*   Pour résoudre cela, les requêtes comme `http.get()` ou `http.delete()` retournent des **Observables** (programmation réactive).
*   Le composant exécute un `.subscribe({ next: (data) => ..., error: (err) => ... })` pour "s'abonner" à la réponse. Il ne met à jour l'interface utilisateur que lorsque les données (généralement au format JSON) sont effectivement reçues du serveur.

### 8. Gestion de la sécurité CORS (Cross-Origin Resource Sharing)
Une erreur fréquente survient lors de la séparation du frontend (ex: localhost:4200) et du backend (ex: localhost:8083). Les navigateurs bloquent les requêtes Ajax vers un domaine différent par sécurité (CORS policy).
*   Avant la requête principale, le navigateur envoie une requête préalable `OPTIONS` pour vérifier les autorisations.
*   **Solution :** Configurer le backend pour renvoyer le header `Access-Control-Allow-Origin`. Dans Spring Boot, cela se règle en ajoutant simplement l'annotation `@CrossOrigin("*")` (ou en ciblant le port 4200) sur le contrôleur REST.
