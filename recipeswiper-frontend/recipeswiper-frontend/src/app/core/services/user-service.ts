import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "../models/dto/user";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class UserService {
    private currentUser: User | null = null;
    private currentUserSubject = new BehaviorSubject<User | null>(null);
    private isLoadingSubject = new BehaviorSubject<boolean>(false);
    private isInitialized = false;

    constructor(private router: Router) {}

    async initializeUser(): Promise<void> {
        if (this.isInitialized) return;
        this.isLoadingSubject.next(true);
        try {
            const token = this.getUserTokenFromStorage();
            if (token) {
                this.setCurrentUser({ id: 0, username: '', userToken: token, groupToken: '' });
            }
            this.isInitialized = true;
        } catch (error) {
            console.error('Error initializing user:', error);
        } finally {
            this.isLoadingSubject.next(false);
        }
    }

    getCurrentUser(): User | null {
        return this.currentUser;
    }

    getCurrentUserObservable(): Observable<User | null> {
        return this.currentUserSubject.asObservable();
    }

    getLoadingObservable(): Observable<boolean> {
        return this.isLoadingSubject.asObservable();
    }

    isLoggedIn(): boolean {
        return this.currentUser !== null && this.currentUser.userToken !== null;
    }

    getUserToken(): string | null {
        return this.currentUser?.userToken || null;
    }

    async setUser(user: User): Promise<void> {
        await this.saveUserToStorage(user);
        this.setCurrentUser(user);
    }

    logout(): void {
        this.clearUserFromStorage();
        this.setCurrentUser(null);
        this.router.navigate(['/recipeswiper/create-user']);
    }

    redirectToCreateUserIfNeeded(): void {
        if (!this.isLoggedIn()) {
            this.router.navigate(['/recipeswiper/create-user']);
        }
    }

    private getUserTokenFromStorage(): string | null {
        return localStorage.getItem('userToken');
    }

    private async saveUserToStorage(user: User): Promise<void> {
        localStorage.setItem('userToken', user.userToken);
        if (user.groupToken) {
            localStorage.setItem('groupToken', user.groupToken);
        }
    }

    private clearUserFromStorage(): void {
        localStorage.removeItem('userToken');
        localStorage.removeItem('groupToken');
    }

    private setCurrentUser(user: User | null): void {
        this.currentUser = user;
        this.currentUserSubject.next(user);
    }

    async saveToken(userToken: string): Promise<void> {
        await this.setUser({ id: 0, username: '', userToken: userToken, groupToken: '' });
    }

    getUserTokenObservable(): Observable<string | null> {
        return new Observable(observer => {
            this.currentUserSubject.subscribe(user => {
                observer.next(user?.userToken || null);
            });
        });
    }
}