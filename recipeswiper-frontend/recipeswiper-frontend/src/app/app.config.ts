import { ApplicationConfig, provideZoneChangeDetection, inject, provideAppInitializer } from '@angular/core';
import { provideRouter, Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { UserService } from './core/services/user-service';
import { RecipeswiperService } from './core/services/recipeswiper-service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideRouter(routes),
    provideHttpClient(),
    provideAppInitializer(() => {
      const userService = inject(UserService);
      const recipeswiperService = inject(RecipeswiperService);
      const router = inject(Router);
      return new Promise<void>((resolve) => {
        const token = localStorage.getItem('userToken');
        if (token) {
          recipeswiperService.getUser(token).subscribe(async user => {
            await userService.setUser(user);
            if (user.groupToken) {
              router.navigate([`/recipeswiper/recipe/${user.groupToken}`]);
            } else if (user.userToken) {
              router.navigate([`/recipeswiper/user/${user.userToken}`]);
            }
            resolve();
          });
        } else {
          resolve();
        }
      });
    })
  ]
};