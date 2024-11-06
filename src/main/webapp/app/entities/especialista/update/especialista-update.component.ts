import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { IEspecialista } from '../especialista.model';
import { EspecialistaService } from '../service/especialista.service';
import { EspecialistaFormGroup, EspecialistaFormService } from './especialista-form.service';

@Component({
  standalone: true,
  selector: 'jhi-especialista-update',
  templateUrl: './especialista-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EspecialistaUpdateComponent implements OnInit {
  isSaving = false;
  especialista: IEspecialista | null = null;

  sujetosCollection: ISujeto[] = [];

  protected especialistaService = inject(EspecialistaService);
  protected especialistaFormService = inject(EspecialistaFormService);
  protected sujetoService = inject(SujetoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EspecialistaFormGroup = this.especialistaFormService.createEspecialistaFormGroup();

  compareSujeto = (o1: ISujeto | null, o2: ISujeto | null): boolean => this.sujetoService.compareSujeto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ especialista }) => {
      this.especialista = especialista;
      if (especialista) {
        this.updateForm(especialista);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const especialista = this.especialistaFormService.getEspecialista(this.editForm);
    if (especialista.id !== null) {
      this.subscribeToSaveResponse(this.especialistaService.update(especialista));
    } else {
      this.subscribeToSaveResponse(this.especialistaService.create(especialista));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEspecialista>>): void {
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

  protected updateForm(especialista: IEspecialista): void {
    this.especialista = especialista;
    this.especialistaFormService.resetForm(this.editForm, especialista);

    this.sujetosCollection = this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(this.sujetosCollection, especialista.sujeto);
  }

  protected loadRelationshipsOptions(): void {
    this.sujetoService
      .query({ filter: 'especialista-is-null' })
      .pipe(map((res: HttpResponse<ISujeto[]>) => res.body ?? []))
      .pipe(map((sujetos: ISujeto[]) => this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(sujetos, this.especialista?.sujeto)))
      .subscribe((sujetos: ISujeto[]) => (this.sujetosCollection = sujetos));
  }
}
