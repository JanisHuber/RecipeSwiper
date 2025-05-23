import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "../models/dto/user";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class UserService {
    private userToken: string | null = null;
    private userTokenSubject = new BehaviorSubject<string | null>(null);

    constructor(private router: Router) {}
  
    async loadUserState(): Promise<void> {
      const token = await this.getUserTokenFromIndexedDB();
      this.setUserToken(token);
      
      if (!token) {
        this.router.navigate(['/recipeswiper/create-user']);
      }
    }
  
    getUserToken(): string | null {
      return this.userToken;
    }

    getUserTokenObservable(): Observable<string | null> {
      return this.userTokenSubject.asObservable();
    }
  
    private async getUserTokenFromIndexedDB(): Promise<string | null> {
      return localStorage.getItem('userToken');
    }

    public async saveToken(userToken: string): Promise<void> {
      await localStorage.setItem('userToken', userToken);
      this.setUserToken(userToken);
    }

    private setUserToken(token: string | null): void {
      this.userToken = token;
      this.userTokenSubject.next(token);
    }

    public clearUserToken(): void {
      this.userToken = null;
      this.userTokenSubject.next(null);
      localStorage.removeItem('userToken');
    }
}