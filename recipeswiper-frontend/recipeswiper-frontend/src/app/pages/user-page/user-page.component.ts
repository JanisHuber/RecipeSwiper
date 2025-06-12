import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../core/models/dto/User';
import { UserProfileComponent } from '../../features/user/user-profile/user-profile.component';
import { CreateUserComponent } from '../../features/user/create-user/create-user.component';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-user-page',
  standalone: true,
  imports: [CommonModule, UserProfileComponent, CreateUserComponent],
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css',
})
export class UserPageComponent implements OnInit {
  user: User | null = null;
  userToken: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    if (this.user) {
      this.userToken = this.user.userToken;
    }
  }
}
