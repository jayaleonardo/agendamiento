/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, Input, Host } from '@angular/core';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CheckboxGroupComponent } from '../checkbox-group/checkbox-group.component';

@Component({
  selector: 'jhi-checkbox',
  templateUrl: './check-box.component.html',
  styleUrls: ['./check-box.component.scss'],
  standalone: true,
  imports: [SharedModule, RouterModule, FormsModule, ReactiveFormsModule],
})
export class CheckboxComponent {
  @Input() value: any;
  @Input() label = '';

  constructor(@Host() private checkboxGroup: CheckboxGroupComponent) {}

  toggleCheck() {
    this.checkboxGroup.addOrRemove(this.value);
  }

  isChecked(): boolean {
    return this.checkboxGroup.contains(this.value);
  }
}
