import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/dto/User';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  private currentUser: User | null = null;
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);

  constructor(private router: Router) {}

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
    this.router.navigate(['/recipeswiper/user']);
  }

  private async saveUserToStorage(user: User): Promise<void> {
    localStorage.setItem('userToken', user.userToken);
  }

  private clearUserFromStorage(): void {
    localStorage.removeItem('userToken');
  }

  private setCurrentUser(user: User | null): void {
    this.currentUser = user;
    this.currentUserSubject.next(user);
  }

  async saveToken(userToken: string): Promise<void> {
    await this.setUser({ id: 0, username: '', userToken: userToken });
  }

  getUserTokenObservable(): Observable<string | null> {
    return new Observable((observer) => {
      this.currentUserSubject.subscribe((user) => {
        observer.next(user?.userToken || null);
      });
    });
  }
}
