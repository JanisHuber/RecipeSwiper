import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';
import { User } from '../../core/models/dto/user';

@Component({
  selector: 'app-user-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent implements OnInit {
  user: User | null = null;
  userToken: string = '';

  constructor(
    private route: ActivatedRoute,
    private recipeswiperService: RecipeswiperService
  ) {
    this.route.params.subscribe(params => {
      this.userToken = params['userToken'];
    });
  }

  ngOnInit() {
    if (this.userToken) {
      this.recipeswiperService.getUser(this.userToken).subscribe(user => {
        this.user = user;
      });
    }
  }
}
