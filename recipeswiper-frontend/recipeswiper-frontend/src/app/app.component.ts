import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { Group } from './core/models/dto/Group';
import { RecipeswiperService } from './core/services/recipeswiper-service';
import { User } from './core/models/dto/user';
import { UserService } from './core/services/user-service';
import { Router } from '@angular/router';


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
  currentUser: User | null = null;
  groupName: string = '';
  showGroup: boolean = false;

  constructor(private recipeswiperService: RecipeswiperService, private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.userService.getCurrentUserObservable().subscribe((user: User | null) => {
      this.currentUser = user;
      this.recipeswiperService.getGroups(this.currentUser?.userToken || '').subscribe(groups => this.groups = groups);
    });

    this.router.events.subscribe(() => {
      const url = this.router.url;
      const match = url.match(/recipe\/([^/]+)/) || url.match(/group\/([^/]+)/);
      if (match) {
        this.showGroup = true;
        const groupToken = match[1];
        this.recipeswiperService.getGroupName(groupToken).subscribe(group => this.groupName = group.name);
      } else {
        this.showGroup = false;
      }
    });
  }
}
