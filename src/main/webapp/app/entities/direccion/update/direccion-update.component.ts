import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { IDireccion } from '../direccion.model';
import { DireccionService } from '../service/direccion.service';
import { DireccionFormGroup, DireccionFormService } from './direccion-form.service';

@Component({
  standalone: true,
  selector: 'jhi-direccion-update',
  templateUrl: './direccion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DireccionUpdateComponent implements OnInit {
  isSaving = false;
  direccion: IDireccion | null = null;

  sujetosCollection: ISujeto[] = [];

  protected direccionService = inject(DireccionService);
  protected direccionFormService = inject(DireccionFormService);
  protected sujetoService = inject(SujetoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DireccionFormGroup = this.direccionFormService.createDireccionFormGroup();

  compareSujeto = (o1: ISujeto | null, o2: ISujeto | null): boolean => this.sujetoService.compareSujeto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ direccion }) => {
      this.direccion = direccion;
      if (direccion) {
        this.updateForm(direccion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const direccion = this.direccionFormService.getDireccion(this.editForm);
    if (direccion.id !== null) {
      this.subscribeToSaveResponse(this.direccionService.update(direccion));
    } else {
      this.subscribeToSaveResponse(this.direccionService.create(direccion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDireccion>>): void {
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

  protected updateForm(direccion: IDireccion): void {
    this.direccion = direccion;
    this.direccionFormService.resetForm(this.editForm, direccion);

    this.sujetosCollection = this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(this.sujetosCollection, direccion.sujeto);
  }

  protected loadRelationshipsOptions(): void {
    this.sujetoService
      .query({ filter: 'direccion-is-null' })
      .pipe(map((res: HttpResponse<ISujeto[]>) => res.body ?? []))
      .pipe(map((sujetos: ISujeto[]) => this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(sujetos, this.direccion?.sujeto)))
      .subscribe((sujetos: ISujeto[]) => (this.sujetosCollection = sujetos));
  }
}
