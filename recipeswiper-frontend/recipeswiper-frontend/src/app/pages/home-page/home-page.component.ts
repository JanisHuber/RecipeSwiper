import { Component, OnInit } from '@angular/core';
import { JoinGroupComponent } from '../../features/group/join-group/join-group.component';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { UserProfileComponent } from '../../features/user/user-profile/user-profile.component';
import { UserService } from '../../core/services/user-service';
import { User } from '../../core/models/dto/user';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [JoinGroupComponent, UserProfileComponent],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  public currentUser: User | null = null;
  public username: string = '';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.getCurrentUserObservable().subscribe((user: User | null) => {
      this.currentUser = user;
      this.username = user?.username || '';
    });
  }
}