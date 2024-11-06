import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHorarioConsulta } from 'app/entities/horario-consulta/horario-consulta.model';
import { HorarioConsultaService } from 'app/entities/horario-consulta/service/horario-consulta.service';
import { IProgramacion } from '../programacion.model';
import { ProgramacionService } from '../service/programacion.service';
import { ProgramacionFormGroup, ProgramacionFormService } from './programacion-form.service';

@Component({
  standalone: true,
  selector: 'jhi-programacion-update',
  templateUrl: './programacion-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProgramacionUpdateComponent implements OnInit {
  isSaving = false;
  programacion: IProgramacion | null = null;

  horarioConsultasSharedCollection: IHorarioConsulta[] = [];

  protected programacionService = inject(ProgramacionService);
  protected programacionFormService = inject(ProgramacionFormService);
  protected horarioConsultaService = inject(HorarioConsultaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProgramacionFormGroup = this.programacionFormService.createProgramacionFormGroup();

  compareHorarioConsulta = (o1: IHorarioConsulta | null, o2: IHorarioConsulta | null): boolean =>
    this.horarioConsultaService.compareHorarioConsulta(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programacion }) => {
      this.programacion = programacion;
      if (programacion) {
        this.updateForm(programacion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programacion = this.programacionFormService.getProgramacion(this.editForm);
    if (programacion.id !== null) {
      this.subscribeToSaveResponse(this.programacionService.update(programacion));
    } else {
      this.subscribeToSaveResponse(this.programacionService.create(programacion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramacion>>): void {
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

  protected updateForm(programacion: IProgramacion): void {
    this.programacion = programacion;
    this.programacionFormService.resetForm(this.editForm, programacion);

    this.horarioConsultasSharedCollection = this.horarioConsultaService.addHorarioConsultaToCollectionIfMissing<IHorarioConsulta>(
      this.horarioConsultasSharedCollection,
      programacion.horarioConsulta,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.horarioConsultaService
      .query()
      .pipe(map((res: HttpResponse<IHorarioConsulta[]>) => res.body ?? []))
      .pipe(
        map((horarioConsultas: IHorarioConsulta[]) =>
          this.horarioConsultaService.addHorarioConsultaToCollectionIfMissing<IHorarioConsulta>(
            horarioConsultas,
            this.programacion?.horarioConsulta,
          ),
        ),
      )
      .subscribe((horarioConsultas: IHorarioConsulta[]) => (this.horarioConsultasSharedCollection = horarioConsultas));
  }
}
