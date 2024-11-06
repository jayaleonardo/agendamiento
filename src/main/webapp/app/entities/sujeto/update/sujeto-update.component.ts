import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISujeto } from '../sujeto.model';
import { SujetoService } from '../service/sujeto.service';
import { SujetoFormGroup, SujetoFormService } from './sujeto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sujeto-update',
  templateUrl: './sujeto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SujetoUpdateComponent implements OnInit {
  isSaving = false;
  sujeto: ISujeto | null = null;

  protected sujetoService = inject(SujetoService);
  protected sujetoFormService = inject(SujetoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SujetoFormGroup = this.sujetoFormService.createSujetoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sujeto }) => {
      this.sujeto = sujeto;
      if (sujeto) {
        this.updateForm(sujeto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sujeto = this.sujetoFormService.getSujeto(this.editForm);
    if (sujeto.id !== null) {
      this.subscribeToSaveResponse(this.sujetoService.update(sujeto));
    } else {
      this.subscribeToSaveResponse(this.sujetoService.create(sujeto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISujeto>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sujeto: ISujeto): void {
    this.sujeto = sujeto;
    this.sujetoFormService.resetForm(this.editForm, sujeto);
  }
}
