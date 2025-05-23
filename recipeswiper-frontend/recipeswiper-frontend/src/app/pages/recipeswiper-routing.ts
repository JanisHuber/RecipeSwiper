import { Routes } from "@angular/router";
import { HomePageComponent } from "./home-page/home-page.component";
import { RecipePageComponent } from "./recipe-page/recipe-page.component";
import { GroupPageComponent } from "./group-page/group-page.component";
import { UserPageComponent } from "./user-page/user-page.component";
import { CreateUserPageComponent } from "./create-user-page/create-user-page.component";


export const routes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomePageComponent },
    { path: 'recipe/:groupToken', component: RecipePageComponent },
    { path: 'group/:groupToken', component: GroupPageComponent },
    { path: 'user/:userToken', component: UserPageComponent },
    { path: 'create-user', component: CreateUserPageComponent },
    { path: '**', redirectTo: '/home' }
];

export default routes;