import { Component } from '@angular/core';
import { RecipeswiperService } from '../../../core/services/recipeswiper.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { ErrorPopupService } from '../../../core/services/error-popup.service';
import { throwError } from 'rxjs';


@Component({
  selector: 'app-join-group',
  imports: [CommonModule, FormsModule],
  templateUrl: './join-group.component.html',
  styleUrl: './join-group.component.css',
})
export class JoinGroupComponent {
  groupCode: string = '';

  constructor(private recipeswiperService: RecipeswiperService, private errorPopupService: ErrorPopupService) {}

  joinGroup() {
    // TODO: Catch error
    this.recipeswiperService.joinGroup(this.groupCode);
  }
}