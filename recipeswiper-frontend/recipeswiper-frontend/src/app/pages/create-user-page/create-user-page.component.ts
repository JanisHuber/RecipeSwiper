import { Component, resolveForwardRef } from '@angular/core';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';
import { User } from '../../core/models/dto/user';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-create-user-page',
  imports: [CommonModule, FormsModule],
  standalone: true,
  templateUrl: './create-user-page.component.html',
  styleUrl: './create-user-page.component.css'
})
export class CreateUserPageComponent {
  
  public username: string = '';

  constructor(private recipeswiperService: RecipeswiperService) {}
  
  createUser() {
    if (this.username) {
      this.recipeswiperService.createUser(this.username).subscribe(response => {
        const user: User = response as User;
        console.log(user);
      });
    }
  }
}
