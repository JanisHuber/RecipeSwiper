import { Component, Input } from '@angular/core';
import { Group } from '../../../core/models/dto/Group';
import { CommonModule } from '@angular/common';
import { GroupCardComponent } from '../group-card/group-card.component';
import { GroupManagementComponent } from '../group-management/group-management.component';


@Component({
  selector: 'app-group-list',
  imports: [CommonModule, GroupCardComponent, GroupManagementComponent],
  standalone: true,
  templateUrl: './group-list.component.html',
  styleUrl: './group-list.component.css'
})
export class GroupListComponent {
  @Input() groups: Group[] = [];


}
