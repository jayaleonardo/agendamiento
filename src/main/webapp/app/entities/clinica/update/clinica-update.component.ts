import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IClinica } from '../clinica.model';
import { ClinicaService } from '../service/clinica.service';
import { ClinicaFormGroup, ClinicaFormService } from './clinica-form.service';

@Component({
  standalone: true,
  selector: 'jhi-clinica-update',
  templateUrl: './clinica-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClinicaUpdateComponent implements OnInit {
  isSaving = false;
  clinica: IClinica | null = null;

  protected clinicaService = inject(ClinicaService);
  protected clinicaFormService = inject(ClinicaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ClinicaFormGroup = this.clinicaFormService.createClinicaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clinica }) => {
      this.clinica = clinica;
      if (clinica) {
        this.updateForm(clinica);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clinica = this.clinicaFormService.getClinica(this.editForm);
    if (clinica.id !== null) {
      this.subscribeToSaveResponse(this.clinicaService.update(clinica));
    } else {
      this.subscribeToSaveResponse(this.clinicaService.create(clinica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClinica>>): void {
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

  protected updateForm(clinica: IClinica): void {
    this.clinica = clinica;
    this.clinicaFormService.resetForm(this.editForm, clinica);
  }
}
