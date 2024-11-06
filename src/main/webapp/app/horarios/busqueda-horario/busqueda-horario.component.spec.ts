/* eslint-disable prettier/prettier */
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusquedaHorarioComponent } from './busqueda-horario.component';

describe('BusquedaHorarioComponent', () => {
  let component: BusquedaHorarioComponent;
  let fixture: ComponentFixture<BusquedaHorarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BusquedaHorarioComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BusquedaHorarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
