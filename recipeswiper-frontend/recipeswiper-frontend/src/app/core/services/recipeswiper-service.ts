import { User } from "../models/dto/user";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { UserService } from "./user-service";
import { RequestCreateUser } from "../models/dto/RequestCreateUser";
import { RequestJoin } from "../models/dto/RequestJoin";
import { Group } from "../models/dto/Group";
import { Router } from "@angular/router";
import { Recipe } from "../models/Recipe";



@Injectable({providedIn: 'root'})
export class RecipeswiperService {

    private apiUrl = 'http://localhost:9090/api/recipeswiper';
    private httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json'
        })
    };

    constructor(private http: HttpClient, private userService: UserService, private router: Router) {}

    createUser(username: string): Observable<User> {
        const request: RequestCreateUser = { username: username };
        return this.http.post<User>(`${this.apiUrl}/new/user`, request, 
            this.httpOptions
        ).pipe(
            tap(user => this.userService.saveToken(user.userToken))
        );
    }

    getUser(userToken: string): Observable<User> {
        return this.http.get<User>(
            `${this.apiUrl}/${userToken}/user`,
            this.httpOptions
        );
    }

    joinGroup(groupToken: string): void {
        const userToken: string = this.userService.getUserToken() || '';
        if (userToken === '') {
            return;
        }
        const request: RequestJoin = { groupToken: groupToken, userToken: userToken };
        this.http.post(`${this.apiUrl}/join`, request, { ...this.httpOptions, responseType: 'text' }).subscribe(response => {
            console.log(response);
        });
    }

    createGroup(): void {
        const userToken: string = this.userService.getUserToken() || '';
        if (userToken === '') {
            throw new Error('User token not found');
        }
        this.http.post<Group>(`${this.apiUrl}/new/group`, this.httpOptions).subscribe(response => {
            const group: Group = response;
            this.joinGroup(group.groupToken);
            this.router.navigate([`/recipeswiper/recipe/${group.groupToken}`]);
        });
    }

    loadRecipes(groupToken: string): void {
        const userToken: string = this.userService.getUserToken() || '';
        if (userToken === '') {
            throw new Error('User token not found');
        }
        this.http.get(`${this.apiUrl}/${groupToken}/load/recipes`, {responseType: 'text'}).subscribe(response => {
            console.log(response);
        });
    }

    getRecipes(groupToken: string): Observable<Recipe[]> {
        return this.http.get<Recipe[]>(`${this.apiUrl}/groups/${groupToken}/get/recipes`);
    }
}
