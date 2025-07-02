import { Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { RecipePageComponent } from './recipe-page/recipe-page.component';
import { GroupPageComponent } from './group-page/group-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { AuthGuard } from '../core/guards/auth.guard';
import { RecipeViewComponent } from './recipe-view/recipe-view.component';
import { NewRecipeComponent } from '../features/recipe/new-recipe/new-recipe.component';


export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent, canActivate: [AuthGuard] },
  {
    path: 'recipe/:groupToken',
    component: RecipePageComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'group/:groupToken',
    component: GroupPageComponent,
    canActivate: [AuthGuard],
  },
  { path: 'recipe/view/:recipeId', component: RecipeViewComponent },
  { path: 'user', component: UserPageComponent },
  { path: 'new/recipe', component: NewRecipeComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'home' },
];

export default routes;