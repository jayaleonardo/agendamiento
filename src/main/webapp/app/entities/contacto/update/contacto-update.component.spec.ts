import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ISujeto } from 'app/entities/sujeto/sujeto.model';
import { SujetoService } from 'app/entities/sujeto/service/sujeto.service';
import { ContactoService } from '../service/contacto.service';
import { IContacto } from '../contacto.model';
import { ContactoFormService } from './contacto-form.service';

import { ContactoUpdateComponent } from './contacto-update.component';

describe('Contacto Management Update Component', () => {
  let comp: ContactoUpdateComponent;
  let fixture: ComponentFixture<ContactoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contactoFormService: ContactoFormService;
  let contactoService: ContactoService;
  let sujetoService: SujetoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContactoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContactoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContactoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contactoFormService = TestBed.inject(ContactoFormService);
    contactoService = TestBed.inject(ContactoService);
    sujetoService = TestBed.inject(SujetoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call sujeto query and add missing value', () => {
      const contacto: IContacto = { id: 456 };
      const sujeto: ISujeto = { id: 14712 };
      contacto.sujeto = sujeto;

      const sujetoCollection: ISujeto[] = [{ id: 20231 }];
      jest.spyOn(sujetoService, 'query').mockReturnValue(of(new HttpResponse({ body: sujetoCollection })));
      const expectedCollection: ISujeto[] = [sujeto, ...sujetoCollection];
      jest.spyOn(sujetoService, 'addSujetoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contacto });
      comp.ngOnInit();

      expect(sujetoService.query).toHaveBeenCalled();
      expect(sujetoService.addSujetoToCollectionIfMissing).toHaveBeenCalledWith(sujetoCollection, sujeto);
      expect(comp.sujetosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contacto: IContacto = { id: 456 };
      const sujeto: ISujeto = { id: 863 };
      contacto.sujeto = sujeto;

      activatedRoute.data = of({ contacto });
      comp.ngOnInit();

      expect(comp.sujetosCollection).toContain(sujeto);
      expect(comp.contacto).toEqual(contacto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContacto>>();
      const contacto = { id: 123 };
      jest.spyOn(contactoFormService, 'getContacto').mockReturnValue(contacto);
      jest.spyOn(contactoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contacto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contacto }));
      saveSubject.complete();

      // THEN
      expect(contactoFormService.getContacto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contactoService.update).toHaveBeenCalledWith(expect.objectContaining(contacto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContacto>>();
      const contacto = { id: 123 };
      jest.spyOn(contactoFormService, 'getContacto').mockReturnValue({ id: null });
      jest.spyOn(contactoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contacto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contacto }));
      saveSubject.complete();

      // THEN
      expect(contactoFormService.getContacto).toHaveBeenCalled();
      expect(contactoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContacto>>();
      const contacto = { id: 123 };
      jest.spyOn(contactoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contacto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contactoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSujeto', () => {
      it('Should forward to sujetoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(sujetoService, 'compareSujeto');
        comp.compareSujeto(entity, entity2);
        expect(sujetoService.compareSujeto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
