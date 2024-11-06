import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { IContacto } from '../contacto.model';
import { ContactoService } from '../service/contacto.service';
import { ContactoFormGroup, ContactoFormService } from './contacto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contacto-update',
  templateUrl: './contacto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContactoUpdateComponent implements OnInit {
  isSaving = false;
  contacto: IContacto | null = null;

  sujetosCollection: ISujeto[] = [];

  protected contactoService = inject(ContactoService);
  protected contactoFormService = inject(ContactoFormService);
  protected sujetoService = inject(SujetoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ContactoFormGroup = this.contactoFormService.createContactoFormGroup();

  compareSujeto = (o1: ISujeto | null, o2: ISujeto | null): boolean => this.sujetoService.compareSujeto(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contacto }) => {
      this.contacto = contacto;
      if (contacto) {
        this.updateForm(contacto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contacto = this.contactoFormService.getContacto(this.editForm);
    if (contacto.id !== null) {
      this.subscribeToSaveResponse(this.contactoService.update(contacto));
    } else {
      this.subscribeToSaveResponse(this.contactoService.create(contacto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContacto>>): void {
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

  protected updateForm(contacto: IContacto): void {
    this.contacto = contacto;
    this.contactoFormService.resetForm(this.editForm, contacto);

    this.sujetosCollection = this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(this.sujetosCollection, contacto.sujeto);
  }

  protected loadRelationshipsOptions(): void {
    this.sujetoService
      .query({ filter: 'contacto-is-null' })
      .pipe(map((res: HttpResponse<ISujeto[]>) => res.body ?? []))
      .pipe(map((sujetos: ISujeto[]) => this.sujetoService.addSujetoToCollectionIfMissing<ISujeto>(sujetos, this.contacto?.sujeto)))
      .subscribe((sujetos: ISujeto[]) => (this.sujetosCollection = sujetos));
  }
}
