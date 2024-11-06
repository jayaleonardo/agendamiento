import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HorarioConsultaDetailComponent } from './horario-consulta-detail.component';

describe('HorarioConsulta Management Detail Component', () => {
  let comp: HorarioConsultaDetailComponent;
  let fixture: ComponentFixture<HorarioConsultaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HorarioConsultaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./horario-consulta-detail.component').then(m => m.HorarioConsultaDetailComponent),
              resolve: { horarioConsulta: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HorarioConsultaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HorarioConsultaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load horarioConsulta on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HorarioConsultaDetailComponent);

      // THEN
      expect(instance.horarioConsulta()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
