import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { EspecialistaService } from 'app/entities/especialista/service/especialista.service';
import { IHorarioConsulta } from '../horario-consulta.model';
import { HorarioConsultaService } from '../service/horario-consulta.service';
import { HorarioConsultaFormGroup, HorarioConsultaFormService } from './horario-consulta-form.service';

@Component({
  standalone: true,
  selector: 'jhi-horario-consulta-update',
  templateUrl: './horario-consulta-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HorarioConsultaUpdateComponent implements OnInit {
  isSaving = false;
  horarioConsulta: IHorarioConsulta | null = null;

  especialistasSharedCollection: IEspecialista[] = [];

  protected horarioConsultaService = inject(HorarioConsultaService);
  protected horarioConsultaFormService = inject(HorarioConsultaFormService);
  protected especialistaService = inject(EspecialistaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HorarioConsultaFormGroup = this.horarioConsultaFormService.createHorarioConsultaFormGroup();

  compareEspecialista = (o1: IEspecialista | null, o2: IEspecialista | null): boolean =>
    this.especialistaService.compareEspecialista(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ horarioConsulta }) => {
      this.horarioConsulta = horarioConsulta;
      if (horarioConsulta) {
        this.updateForm(horarioConsulta);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const horarioConsulta = this.horarioConsultaFormService.getHorarioConsulta(this.editForm);
    if (horarioConsulta.id !== null) {
      this.subscribeToSaveResponse(this.horarioConsultaService.update(horarioConsulta));
    } else {
      this.subscribeToSaveResponse(this.horarioConsultaService.create(horarioConsulta));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHorarioConsulta>>): void {
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

  protected updateForm(horarioConsulta: IHorarioConsulta): void {
    this.horarioConsulta = horarioConsulta;
    this.horarioConsultaFormService.resetForm(this.editForm, horarioConsulta);

    this.especialistasSharedCollection = this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(
      this.especialistasSharedCollection,
      horarioConsulta.especialista,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.especialistaService
      .query()
      .pipe(map((res: HttpResponse<IEspecialista[]>) => res.body ?? []))
      .pipe(
        map((especialistas: IEspecialista[]) =>
          this.especialistaService.addEspecialistaToCollectionIfMissing<IEspecialista>(especialistas, this.horarioConsulta?.especialista),
        ),
      )
      .subscribe((especialistas: IEspecialista[]) => (this.especialistasSharedCollection = especialistas));
  }
}
