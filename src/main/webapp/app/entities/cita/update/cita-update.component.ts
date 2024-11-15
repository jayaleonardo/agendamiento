import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { ITipoTerapia } from 'app/entities/tipo-terapia/tipo-terapia.model';
import { TipoTerapiaService } from 'app/entities/tipo-terapia/service/tipo-terapia.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { IProgramacion } from 'app/entities/programacion/programacion.model';
import { ProgramacionService } from 'app/entities/programacion/service/programacion.service';
import { CitaService } from '../service/cita.service';
import { ICita } from '../cita.model';
import { CitaFormGroup, CitaFormService } from './cita-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cita-update',
  templateUrl: './cita-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CitaUpdateComponent implements OnInit {
  isSaving = false;
  cita: ICita | null = null;

  especialistasSharedCollection: IEspecialista[] = [];
  tipoTerapiasSharedCollection: ITipoTerapia[] = [];
  pacientesSharedCollection: IPaciente[] = [];
  programacionsSharedCollection: IProgramacion[] = [];

  protected citaService = inject(CitaService);
  protected citaFormService = inject(CitaFormService);
  protected especialistaService = inject(EspecialistaService);
  protected tipoTerapiaService = inject(TipoTerapiaService);
  protected pacienteService = inject(PacienteService);
  protected programacionService = inject(ProgramacionService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CitaFormGroup = this.citaFormService.createCitaFormGroup();

  compareEspecialista = (o1: IEspecialista | null, o2: IEspecialista | null): boolean =>
    this.especialistaService.compareEspecialista(o1, o2);

  compareTipoTerapia = (o1: ITipoTerapia | null, o2: ITipoTerapia | null): boolean => this.tipoTerapiaService.compareTipoTerapia(o1, o2);

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  compareProgramacion = (o1: IProgramacion | null, o2: IProgramacion | null): boolean =>
    this.programacionService.compareProgramacion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cita }) => {
      this.cita = cita;
      if (cita) {
        this.updateForm(cita);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cita = this.citaFormService.getCita(this.editForm);
    if (cita.id !== null) {
      this.subscribeToSaveResponse(this.citaService.update(cita));
    } else {
      this.subscribeToSaveResponse(this.citaService.create(cita));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICita>>): void {
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

  protected updateForm(cita: ICita): void {
    this.cita = cita;
    this.citaFormService.resetForm(this.editForm, cita);

    this.especialistasSharedCollection = this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
      this.especialistasSharedCollection,
      cita.especialista,
    );
    this.tipoTerapiasSharedCollection = this.tipoTerapiaService.addTipoTerapiaToCollectionIfMissing<ITipoTerapia>(
      this.tipoTerapiasSharedCollection,
      cita.tipoTerapia,
    );
    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      cita.paciente,
    );
    this.programacionsSharedCollection = this.programacionService.addProgramacionToCollectionIfMissing<IProgramacion>(
      this.programacionsSharedCollection,
      cita.programacion,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.especialistaService
      .query()
      .pipe(map((res: HttpResponse<IEspecialista[]>) => res.body ?? []))
      .pipe(
        map((especialistas: IEspecialista[]) =>
          this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(especialistas, this.cita?.especialista),
        ),
      )
      .subscribe((especialistas: IEspecialista[]) => (this.especialistasSharedCollection = especialistas));

    this.tipoTerapiaService
      .query()
      .pipe(map((res: HttpResponse<ITipoTerapia[]>) => res.body ?? []))
      .pipe(
        map((tipoTerapias: ITipoTerapia[]) =>
          this.tipoTerapiaService.addTipoTerapiaToCollectionIfMissing<ITipoTerapia>(tipoTerapias, this.cita?.tipoTerapia),
        ),
      )
      .subscribe((tipoTerapias: ITipoTerapia[]) => (this.tipoTerapiasSharedCollection = tipoTerapias));

    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) => this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.cita?.paciente)),
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));

    this.programacionService
      .query()
      .pipe(map((res: HttpResponse<IProgramacion[]>) => res.body ?? []))
      .pipe(
        map((programacions: IProgramacion[]) =>
          this.programacionService.addProgramacionToCollectionIfMissing<IProgramacion>(programacions, this.cita?.programacion),
        ),
      )
      .subscribe((programacions: IProgramacion[]) => (this.programacionsSharedCollection = programacions));
  }
}
