import { User } from '../models/dto/user';
import { RequestCreateUser } from '../models/dto/request-create-user';
import { RequestJoin } from '../models/dto/request-join';
import { Group } from '../models/dto/group';
import { VoteRequest } from '../models/dto/vote-request';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import { Recipe } from '../models/recipe';
import { VoteType } from '../models/vote-type';
import { RecipeResult } from '../models/recipe-result';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RecipeswiperService {
  //private apiUrl = environment.apiUrl;
  private apiUrl = 'http://91.99.218.208:9080/api/recipeswiper'
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private router: Router
  ) {}

  createRecipe(recipe: Recipe): Observable<String> {
    return this.http.post<String>(`${this.apiUrl}/new/recipe`, recipe, {
      ...this.httpOptions,
      responseType: 'text' as 'json',
    });
  }

  createUser(username: string): Observable<User> {
    const request: RequestCreateUser = { username: username };
    return this.http
      .post<User>(`${this.apiUrl}/new/user`, request, this.httpOptions)
      .pipe(
        tap(async (user) => {
          await this.userService.setUser(user);
          this.router.navigate(['/recipeswiper/home']);
        })
      );
  }

  getUser(userToken: string): Observable<User> {
    return this.http
      .get<User>(`${this.apiUrl}/${userToken}/user`, this.httpOptions)
      .pipe(
        catchError((error) => {
          if (error.status === 404) {
            return throwError(() => new Error('User not found'));
          }
          return throwError(() => error);
        })
      );
  }

  joinGroup(groupToken: string): Observable<string> {
    const userToken: string = this.userService.getUserToken() || '';
    if (userToken === '') {
      return throwError(() => new Error('User token not found'));
    }
    const request: RequestJoin = {
      groupToken: groupToken,
      userToken: userToken,
    };

    return this.http
      .post(`${this.apiUrl}/join`, request, {
        ...this.httpOptions,
        responseType: 'text',
      })
      .pipe(
        catchError((error) => {
          if (error.status === 404) {
            return throwError(() => new Error('Group Code invalid'));
          }
          if (error.status === 400) {
            return throwError(() => new Error('You are already in this group'));
          }
          return throwError(() => new Error('Error joining group'));
        })
      );
  }

  createGroup(name: string): void {
    const userToken: string = this.userService.getUserToken() || '';
    if (userToken === '') {
      throw new Error('User token not found');
    }
    this.http
      .post<Group>(`${this.apiUrl}/new/group`, name, {
        headers: new HttpHeaders({ 'Content-Type': 'text/plain' }),
      })
      .subscribe((response) => {
        const group: Group = response;
        this.joinGroup(group.groupToken).subscribe((response) => {
          this.router.navigate([`/recipeswiper/recipe/${group.groupToken}`]);
        });
      });
  }

  loadRecipes(groupToken: string): Observable<string> {
    const userToken: string = this.userService.getUserToken() || '';
    if (userToken === '') {
      throw new Error('User token not found');
    }
    return this.http.get(`${this.apiUrl}/${groupToken}/load/recipes`, {
      responseType: 'text',
    });
  }

  getRecipeById(recipeId: number): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.apiUrl}/recipes/${recipeId}`);
  }

  getRecipesForUser(groupToken: string): Observable<Recipe[]> {
    const userToken: string = this.userService.getUserToken() || '';
    if (userToken === '') {
      throw new Error('User token not found');
    }
    return this.http
      .get<Recipe[]>(
        `${this.apiUrl}/groups/${groupToken}/${userToken}/get/recipes`
      )
      .pipe(
        catchError((error) => {
          if (error.status === 404) {
            return of([]);
          }
          return throwError(() => error);
        })
      );
  }

  getAllRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(`${this.apiUrl}/get/recipes`).pipe(
      catchError((error) => {
        if (error.status === 404) {
          return of([]);
        }
        return throwError(() => error);
      })
    );
  }

  vote(
    groupToken: string,
    recipeId: number,
    voteType: VoteType
  ): Observable<string> {
    const userToken: string = this.userService.getUserToken() || '';
    if (userToken === '') {
      throw new Error('User token not found');
    }
    if (recipeId === undefined) {
      throw new Error('Recipe ID not found');
    }
    if (groupToken === '') {
      throw new Error('Group token not found');
    }

    let voteRequest: VoteRequest = {
      userToken: userToken,
      recipeId: recipeId,
      voteType: voteType,
    };

    return this.http
      .post<string>(`${this.apiUrl}/${groupToken}/vote`, voteRequest, {
        ...this.httpOptions,
        responseType: 'text' as 'json',
      })
      .pipe(
        catchError((error) => {
          if (error.status === 400) {
            return throwError(
              () => new Error('You already voted for this recipe')
            );
          }
          return throwError(() => error);
        })
      );
  }

  getResultRecipes(groupToken: string): Observable<RecipeResult[]> {
    return this.http
      .get<RecipeResult[]>(`${this.apiUrl}/groups/${groupToken}/get/results`)
      .pipe(
        catchError((error) => {
          if (error.status === 404) {
            return of([]);
          }
          return throwError(() => error);
        })
      );
  }

  getGroups(userToken: string): Observable<Group[]> {
    if (!userToken) {
      throw new Error('User token not found');
    }
    return this.http
      .get<Group[]>(`${this.apiUrl}/${userToken}/get/groups`)
      .pipe(
        catchError((error) => {
          if (error.status === 404) {
            return of([]);
          }
          return throwError(() => error);
        })
      );
  }

  getGroupName(groupToken: string): Observable<Group> {
    return this.http.get<Group>(`${this.apiUrl}/groups/${groupToken}`).pipe(
      catchError((error) => {
        return throwError(() => error);
      })
    );
  }
}
