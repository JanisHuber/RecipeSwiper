import { Routes } from "@angular/router";
import { HomePageComponent } from "./home-page/home-page.component";
import { RecipePageComponent } from "./recipe-page/recipe-page.component";
import { GroupPageComponent } from "./group-page/group-page.component";
import { UserPageComponent } from "./user-page/user-page.component";
import { CreateUserPageComponent } from "./create-user-page/create-user-page.component";
import { AuthGuard } from "../core/guards/auth.guard";

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: 'home', component: HomePageComponent, canActivate: [AuthGuard] },
    { path: 'recipe/:groupToken', component: RecipePageComponent, canActivate: [AuthGuard] },
    { path: 'group/:groupToken', component: GroupPageComponent, canActivate: [AuthGuard] },
    { path: 'user/:userToken', component: UserPageComponent, canActivate: [AuthGuard] },
    { path: 'create-user', component: CreateUserPageComponent },
    { path: '**', redirectTo: 'home' }
];

export default routes;