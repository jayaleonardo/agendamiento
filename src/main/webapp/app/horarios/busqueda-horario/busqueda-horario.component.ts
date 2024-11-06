/* eslint-disable prettier/prettier */
import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HorarioService } from '../horarios-service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import SharedModule from 'app/shared/shared.module';
@Component({
  selector: 'jhi-busqueda-horario',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './busqueda-horario.component.html',
  styleUrl: './busqueda-horario.component.scss',
})
export class BusquedaHorarioComponent implements OnInit {
  horarioService = inject(HorarioService);
  especialistas?: IEspecialista[];

  form: FormGroup = new FormGroup({
    especialidad: new FormControl('', Validators.required),
    especialista: new FormControl('', Validators.required),
    desde: new FormControl(new Date()),
    hasta: new FormControl(new Date()),
  });

  // eslint-disable-next-line @typescript-eslint/no-misused-promises
  async ngOnInit(): Promise<void> {
    const rta = await this.horarioService.buscarEspecialistas();
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialistas = body;
    }
  }
}
