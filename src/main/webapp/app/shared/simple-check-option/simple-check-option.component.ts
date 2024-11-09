import { Component, Input, forwardRef } from '@angular/core';
import SharedModule from 'app/shared/shared.module';
import { FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { MultiCheckOption } from '../model/multi-check-option';

@Component({
  selector: 'jhi-simple-check-option',
  templateUrl: './simple-check-option.component.html',
  styleUrls: ['./simple-check-option.component.scss'],
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SimpleCheckOptionComponent),
      multi: true,
    },
    {
      provide: MultiCheckOption,
      useExisting: forwardRef(() => SimpleCheckOptionComponent),
    },
  ],
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SimpleCheckOptionComponent extends MultiCheckOption {
  @Input() value: any;
  @Input()
  label = '';
}
