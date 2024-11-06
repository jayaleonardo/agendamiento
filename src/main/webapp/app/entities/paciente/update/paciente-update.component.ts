import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { IPaciente } from '../paciente.model';
import { PacienteService } from '../service/paciente.service';
import { PacienteFormGroup, PacienteFormService } from './paciente-form.service';

@Component({
  standalone: true,
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PacienteUpdateComponent implements OnInit {
  isSaving = false;
  paciente: IPaciente | null = null;

  sujetosCollection: ISujeto[] = [];

  protected pacienteService = inject(PacienteService);
  protected pacienteFormService = inject(PacienteFormService);
  protected sujetoService = inject(SujetoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PacienteFormGroup = this.pacienteFormService.createPacienteFormGroup();

  compareSujeto = (o1: ISujeto | null, o2: ISujeto | null): boolean => this.sujetoService.compareSujeto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.paciente = paciente;
      if (paciente) {
        this.updateForm(paciente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paciente = this.pacienteFormService.getPaciente(this.editForm);
    if (paciente.id !== null) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>): void {
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

  protected updateForm(paciente: IPaciente): void {
    this.paciente = paciente;
    this.pacienteFormService.resetForm(this.editForm, paciente);

    this.sujetosCollection = this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(this.sujetosCollection, paciente.sujeto);
  }

  protected loadRelationshipsOptions(): void {
    this.sujetoService
      .query({ filter: 'paciente-is-null' })
      .pipe(map((res: HttpResponse<ISujeto[]>) => res.body ?? []))
      .pipe(map((sujetos: ISujeto[]) => this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(sujetos, this.paciente?.sujeto)))
      .subscribe((sujetos: ISujeto[]) => (this.sujetosCollection = sujetos));
  }
}
