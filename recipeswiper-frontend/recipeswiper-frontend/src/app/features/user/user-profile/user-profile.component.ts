import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../../core/models/dto/User';
import { RecipeswiperService } from '../../../core/services/recipeswiper.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css',
})
export class UserProfileComponent {
  @Input() user: User | null = null;
  @Input() userToken: string = '';

  constructor(
    private route: ActivatedRoute,
    private recipeswiperService: RecipeswiperService
  ) {
    this.route.params.subscribe((params) => {
      this.userToken = params['userToken'];
    });
  }
}
