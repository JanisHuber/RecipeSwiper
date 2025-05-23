import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'recipeswiper',
        loadChildren: () =>
          import('./pages/recipeswiper-routing').then((m) => m.default)
    }
];
