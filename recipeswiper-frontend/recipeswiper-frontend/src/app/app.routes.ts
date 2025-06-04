import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: '', redirectTo: '/recipeswiper', pathMatch: 'full' },
    {
        path: 'recipeswiper',
        loadChildren: () =>
          import('./pages/recipeswiper-routing').then((m) => m.default)
    },
    { path: '**', redirectTo: '/recipeswiper' }
];
