import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../core/services/user.service';
import { User } from '../../../core/models/dto/user';
import { Router } from '@angular/router';
import { Group } from '../../../core/models/dto/Group';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  @Input() groupName: string = '';
  @Input() showUserIcon: boolean = true;
  @Input() groups: Group[] = [];

  currentUser: User | null = null;
  showMenu: boolean = false;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit() {
    this.userService
      .getCurrentUserObservable()
      .subscribe(async (user: User | null) => {
        this.currentUser = user;
      });
  }

  toggleMenu() {
    this.showMenu = !this.showMenu;
  }

  navigateToGroup(group: Group) {
    this.router.navigate([`/recipeswiper/group/${group.groupToken}`]);
    this.showMenu = false;
  }

  logout() {
    this.userService.logout();
  }

  navigateToProfile() {
    this.router.navigate([`/recipeswiper/user`]);
  }
}
