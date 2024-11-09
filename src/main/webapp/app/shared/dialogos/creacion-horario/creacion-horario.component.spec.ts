import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreacionHorarioComponent } from './creacion-horario.component';

describe('CreacionHorarioComponent', () => {
  let component: CreacionHorarioComponent;
  let fixture: ComponentFixture<CreacionHorarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreacionHorarioComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CreacionHorarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
