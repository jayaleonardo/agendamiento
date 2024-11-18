import { Component, OnDestroy, OnInit, inject, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { HorarioService } from 'app/horarios/horarios-service';
import { HomeService } from './home-service';

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [SharedModule, RouterModule, FormsModule, ReactiveFormsModule],
})
export default class HomeComponent implements OnInit, OnDestroy {
  homeService = inject(HomeService);
  account = signal<Account | null>(null);
  foto = 'https://cdn-icons-png.flaticon.com/512/9193/9193824.png';
  especialistas?: IEspecialista[];
  especialidades?: string[];
  form1: FormGroup = new FormGroup({
    especialidad: new FormControl(''),
    especialista: new FormControl(''),
  });
  private readonly destroy$ = new Subject<void>();
  private readonly accountService = inject(AccountService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));

    this.cargarDatos();
  }

  async cargarDatos(): Promise<void> {
    const rta = await this.homeService.buscarEspecialidades();
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialidades = body;
      if (this.especialidades.length > 0) {
        // fijar un valor por defecto de especialidad
        this.form1.patchValue({ especialidad: this.especialidades[0] });
        this.buscarEspecialista(this.especialidades[0]);
      }
    }
  }
  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  async buscarEspecialista(especialidad: string): Promise<void> {
    // eslint-disable-next-line no-console
    console.log(especialidad);
    const rta = await this.homeService.especialistasPorEspecialidad(especialidad);
    const body = rta.body ?? null;
    if (body !== null) {
      this.especialistas = body;
    }
  }

  async buscarFoto(especialistaid: number): Promise<void> {
    const rta = await this.homeService.buscarFoto(especialistaid);
    const body = rta.body ?? null;
    this.foto = 'https://cdn-icons-png.flaticon.com/512/9193/9193824.png';
    if (body !== null) {
      if (body.fotoUrl !== '') {
        this.foto = body.fotoUrl!;
      }
    }
  }
}
