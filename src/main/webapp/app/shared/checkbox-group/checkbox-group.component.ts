/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable no-extra-boolean-cast */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, forwardRef } from '@angular/core';
import {
  ControlValueAccessor,
  FormArray,
  FormControl,
  FormGroup,
  FormsModule,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import { RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';

@Component({
  selector: 'jhi-checkbox-group',
  templateUrl: './checkbox-group.component.html',
  styleUrls: ['./checkbox-group.component.scss'],
  standalone: true,
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => CheckboxGroupComponent), multi: true }],
  imports: [SharedModule, RouterModule, FormsModule, ReactiveFormsModule],
})
export class CheckboxGroupComponent implements ControlValueAccessor {
  private _model: any;
  private onChange!: (m: any) => void;
  private onTouched!: (m: any) => void;

  // eslint-disable-next-line @typescript-eslint/member-ordering, @angular-eslint/no-output-on-prefix
  @Output() onCheck = new EventEmitter<any>();

  get model() {
    return this._model;
  }

  writeValue(value: any): void {
    this._model = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  set(value: any) {
    this._model = value;
    this.onChange(this._model);
  }

  addOrRemove(value: any) {
    if (this.contains(value)) {
      this.remove(value);
    } else {
      this.add(value);
    }
    this.onCheck.emit(this._model);
  }

  contains(value: any): boolean {
    if (this._model instanceof Array) {
      return this._model.indexOf(value) > -1;
    } else if (!!this._model) {
      return this._model === value;
    }

    return false;
  }

  private add(value: any) {
    if (!this.contains(value)) {
      if (this._model instanceof Array) {
        this._model.push(value);
      } else {
        this._model = [value];
      }
      this.onChange(this._model);
    }
  }

  private remove(value: any) {
    const index = this._model.indexOf(value);
    if (!this._model || index < 0) {
      return;
    }

    this._model.splice(index, 1);
    this.onChange(this._model);
  }
}
