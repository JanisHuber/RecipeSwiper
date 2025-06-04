import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user-service';
import { User } from '../../core/models/dto/user';
import { Group } from '../../core/models/dto/Group';
import { RecipeswiperService } from '../../core/services/recipeswiper-service';
import { GroupListComponent } from '../../features/group/group-list/group-list.component';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [GroupListComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
})
export class HomePageComponent implements OnInit {
  public currentUser: User | null = null;
  public username: string = '';
  public groups: Group[] = [];

  constructor(
    private userService: UserService,
    private recipeswiperService: RecipeswiperService
  ) {}

  ngOnInit() {
    this.userService
      .getCurrentUserObservable()
      .subscribe((user: User | null) => {
        this.currentUser = user;
        this.username = user?.username || '';
      });

    this.recipeswiperService
      .getGroups(this.currentUser?.userToken || '')
      .subscribe((groups: Group[]) => {
        this.groups = groups;
      });
  }
}
