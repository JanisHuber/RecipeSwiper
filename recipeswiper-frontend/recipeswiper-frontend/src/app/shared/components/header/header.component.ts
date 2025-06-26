import {
  Component,
  Input,
  OnInit,
  HostListener,
  ElementRef,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '@core/services/user.service';
import { User } from '@core/models/dto/User';
import { Router } from '@angular/router';
import { Group } from '@core/models/dto/Group';

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
  showGroupsSubmenu: boolean = false;

  constructor(
    private userService: UserService,
    private router: Router,
    private elementRef: ElementRef
  ) {}

  ngOnInit() {
    this.userService
      .getCurrentUserObservable()
      .subscribe(async (user: User | null) => {
        this.currentUser = user;
      });
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event) {
    const target = event.target as HTMLElement;
    const clickedInside = this.elementRef.nativeElement.contains(target);

    if (!clickedInside && this.showMenu) {
      this.showMenu = false;
      this.showGroupsSubmenu = false;
    }
  }

  toggleMenu() {
    this.showMenu = !this.showMenu;
    if (!this.showMenu) {
      this.showGroupsSubmenu = false;
    }
  }

  toggleGroupsSubmenu() {
    this.showGroupsSubmenu = !this.showGroupsSubmenu;
  }

  navigateToGroup(group: Group) {
    this.router.navigate([`/recipeswiper/group/${group.groupToken}`]);
    this.showMenu = false;
    this.showGroupsSubmenu = false;
  }

  logout() {
    this.userService.logout();
  }

  navigateToProfile() {
    this.router.navigate([`/recipeswiper/user`]);
  }

  navigateToHome() {
    this.router.navigate([`/recipeswiper/home`]);
  }
}
