import { Component } from '@angular/core';
import { RecipeswiperService } from '../../../core/services/recipeswiper.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ErrorPopupService } from '../../../core/services/error-popup.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-join-group',
  imports: [CommonModule, FormsModule],
  templateUrl: './join-group.component.html',
  styleUrl: './join-group.component.css',
})
export class JoinGroupComponent {
  groupCode: string = '';

  constructor(private recipeswiperService: RecipeswiperService, private errorPopupService: ErrorPopupService, private router: Router) {}

  joinGroup() {
    this.recipeswiperService.joinGroup(this.groupCode).subscribe({
      next: (response) => {
        this.router.navigate([`/recipeswiper/recipe/${this.groupCode}`]);
      },
      error: (error) => {
        this.errorPopupService.showError(error.message);
      },
    });
  }
}