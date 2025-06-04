import { Component } from '@angular/core';
import { RecipeswiperService } from '../../../core/services/recipeswiper-service';
import { User } from '../../../core/models/dto/user';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-create-user',
  imports: [FormsModule],
  standalone: true,
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent {
  public username: string = '';
  public isLoading: boolean = false;
  public errorMessage: string = '';

  constructor(private recipeswiperService: RecipeswiperService) {}
  
  createUser() {
    if (this.username.trim()) {
      this.isLoading = true;
      this.errorMessage = '';
      
      this.recipeswiperService.createUser(this.username.trim()).subscribe({
        next: (user: User) => {
          this.isLoading = false;
        },
        error: (error) => {
          this.errorMessage = 'Error creating user. Please try again.';
          this.isLoading = false;
        }
      });
    }
  }
}
