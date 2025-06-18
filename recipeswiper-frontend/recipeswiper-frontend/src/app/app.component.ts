import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { ErrorPopupComponent } from './shared/components/error-popup/error-popup.component';
import { Group } from './core/models/dto/Group';
import { RecipeswiperService } from './core/services/recipeswiper.service';
import { User } from './core/models/dto/User';
import { UserService } from './core/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HeaderComponent, ErrorPopupComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'recipeswiper-frontend';

  groups: Group[] = [];
  currentUser: User | null = null;
  groupName: string = '';
  showGroup: boolean = false;

  constructor(
    private recipeswiperService: RecipeswiperService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.userService
      .getCurrentUserObservable()
      .subscribe((user: User | null) => {
        this.currentUser = user;
      });

    if (this.currentUser) {
      this.router.events.subscribe(() => {
        this.recipeswiperService
          .getGroups(this.currentUser?.userToken || '')
          .subscribe((groups) => (this.groups = groups));

        const url = this.router.url;
        const match =
          url.match(/recipe\/swipe\/([^/]+)/) || url.match(/group\/([^/]+)/);
        if (match) {
          this.showGroup = true;
          const groupToken = match[1];
          this.recipeswiperService
            .getGroupName(groupToken)
            .subscribe((group) => (this.groupName = group.name));
        } else {
          this.showGroup = false;
        }
      });
  }
  }
}
