import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  public groupCode: string = '';

  constructor(private recipeswiperService: RecipeswiperService) {}

  joinGroup() {
    if (!this.groupCode) return;
    
    this.recipeswiperService.joinGroup(this.groupCode);
  }

  createGroup() {
    this.recipeswiperService.createGroup();
  }
}