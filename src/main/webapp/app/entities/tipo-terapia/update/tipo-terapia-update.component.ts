import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITipoTerapia } from '../tipo-terapia.model';
import { TipoTerapiaService } from '../service/tipo-terapia.service';
import { TipoTerapiaFormGroup, TipoTerapiaFormService } from './tipo-terapia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tipo-terapia-update',
  templateUrl: './tipo-terapia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TipoTerapiaUpdateComponent implements OnInit {
  isSaving = false;
  tipoTerapia: ITipoTerapia | null = null;

  protected tipoTerapiaService = inject(TipoTerapiaService);
  protected tipoTerapiaFormService = inject(TipoTerapiaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TipoTerapiaFormGroup = this.tipoTerapiaFormService.createTipoTerapiaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoTerapia }) => {
      this.tipoTerapia = tipoTerapia;
      if (tipoTerapia) {
        this.updateForm(tipoTerapia);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoTerapia = this.tipoTerapiaFormService.getTipoTerapia(this.editForm);
    if (tipoTerapia.id !== null) {
      this.subscribeToSaveResponse(this.tipoTerapiaService.update(tipoTerapia));
    } else {
      this.subscribeToSaveResponse(this.tipoTerapiaService.create(tipoTerapia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoTerapia>>): void {
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

  protected updateForm(tipoTerapia: ITipoTerapia): void {
    this.tipoTerapia = tipoTerapia;
    this.tipoTerapiaFormService.resetForm(this.editForm, tipoTerapia);
  }
}
