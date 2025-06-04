import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../core/services/user-service';
import { User } from '../../../core/models/dto/user';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="bg-white rounded-lg shadow-lg p-6">
      <h3 class="text-lg font-semibold text-gray-800 mb-4">User Profile</h3>
      
      @if (currentUser) {
        <div class="space-y-3">
          <div>
            <label class="text-sm font-medium text-gray-600">Username:</label>
            <p class="text-gray-800">{{ currentUser.username }}</p>
          </div>
          <div>
            <label class="text-sm font-medium text-gray-600">User Token:</label>
            <p class="text-gray-800 font-mono text-xs">{{ currentUser.userToken }}</p>
          </div>
          @if (currentUser.groupToken) {
            <div>
              <label class="text-sm font-medium text-gray-600">Group Token:</label>
              <p class="text-gray-800 font-mono text-xs">{{ currentUser.groupToken }}</p>
            </div>
          }
          <div>
            <label class="text-sm font-medium text-gray-600">Status:</label>
            <span class="inline-block bg-green-100 text-green-800 text-xs px-2 py-1 rounded">
              Logged In
            </span>
          </div>
        </div>
      } @else {
        <p class="text-gray-500">No user data available</p>
      }
    </div>
  `,
  styles: []
})
export class UserProfileComponent implements OnInit {
  currentUser: User | null = null;

  constructor(private userService: UserService) {}

  ngOnInit() {
    // Einfacher Zugriff auf den globalen User-State
    this.userService.getCurrentUserObservable().subscribe((user: User | null) => {
      this.currentUser = user;
    });
  }
}
