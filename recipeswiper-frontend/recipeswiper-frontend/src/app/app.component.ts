import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { Group } from './core/models/dto/Group';
import { RecipeswiperService } from './core/services/recipeswiper-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'recipeswiper-frontend';

  groups: Group[] = [];

  constructor(private recipeswiperService: RecipeswiperService) {}

  ngOnInit() {
    this.recipeswiperService.getGroups().subscribe(groups => this.groups = groups);
  }
}
