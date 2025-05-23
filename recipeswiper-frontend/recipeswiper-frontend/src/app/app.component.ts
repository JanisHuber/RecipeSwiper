import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserService } from './core/services/user-service';
import { RecipeswiperService } from './core/services/recipeswiper-service';
import { Router } from '@angular/router';
import { User } from './core/models/dto/user';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'recipeswiper-frontend';

  public username: string = '';

  constructor(
    private userService: UserService,
    private recipeswiperService: RecipeswiperService,
    private router: Router
  ) {
    this.initializeApp();
  }

  private async initializeApp(): Promise<void> {
    await this.userService.loadUserState();
    
    this.userService.getUserTokenObservable().subscribe(token => {
      if (token) {
        this.recipeswiperService.getUser(token).subscribe(user => {
          this.username = user.username;
          if (user.groupToken) {
            this.router.navigate([`/recipeswiper/recipe/${user.groupToken}`]);
          } else {
            this.router.navigate(['/recipeswiper/home']);
          }
        });
      }
    });
  }

  logout() {
    this.userService.clearUserToken();
    this.username = '';
    this.router.navigate(['/recipeswiper/create-user']);
  }
}
