import { Component, Input } from '@angular/core';
import { RecipeswiperService } from '../../../core/services/recipeswiper.service';
import { FormsModule } from '@angular/forms';
import { JoinGroupComponent } from '../join-group/join-group.component';
import { CreateGroupComponent } from '../create-group/create-group.component';

@Component({
  selector: 'app-group-management',
  imports: [FormsModule, JoinGroupComponent, CreateGroupComponent],
  standalone: true,
  templateUrl: './group-management.component.html',
  styleUrl: './group-management.component.css',
})
export class GroupManagementComponent {
  public groupCode: string = '';
  public groupName: string = '';
  public activeTab: 'join' | 'create' = 'join';

  constructor(private recipeswiperService: RecipeswiperService) {}

  getTabClass(tab: 'join' | 'create'): string {
    const baseClass =
      'px-6 py-3 rounded-lg font-medium text-sm hover:bg-blue-600 hover:text-white transition-colors border border-gray-200';
    return this.activeTab === tab
      ? `${baseClass} bg-blue-600 text-white`
      : `${baseClass} bg-gray-50 text-black`;
  }
}
