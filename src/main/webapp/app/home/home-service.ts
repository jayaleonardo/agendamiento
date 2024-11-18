import { HttpClient, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { lastValueFrom } from 'rxjs';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { ITurnoDisponible } from './turnos.model';
import { ICita } from 'app/entities/cita/cita.model';
@Injectable({ providedIn: 'root' })
export class HomeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/public');

  async buscarEspecialidades(): Promise<HttpResponse<string[]>> {
    const data = this.http.get<string[]>(`${this.resourceUrl}/especialidades`, { observe: 'response' });
    return await lastValueFrom(data);
  }

  async especialistasPorEspecialidad(especialidad: string): Promise<HttpResponse<IEspecialista[]>> {
    const data = this.http.get<IEspecialista[]>(`${this.resourceUrl}/especialistas-por-especialidad/${especialidad}`, {
      observe: 'response',
    });
    return await lastValueFrom(data);
  }

  async buscarFoto(especialistaid: number): Promise<HttpResponse<IEspecialista>> {
    const data = this.http.get<IEspecialista>(`${this.resourceUrl}/buscar-foto/${especialistaid}`, {
      observe: 'response',
    });
    return await lastValueFrom(data);
  }

  async turnosDisponibles(fecha: Date): Promise<HttpResponse<ITurnoDisponible[]>> {
    const parametros = {
      fecha,
    };

    const data = this.http.post<ITurnoDisponible[]>(`${this.resourceUrl}/turnos-disponibles`, parametros, {
      observe: 'response',
    });
    return await lastValueFrom(data);
  }

  async guardarPreReserva(datos: any): Promise<HttpResponse<ICita>> {
    const data = this.http.post<ICita>(`${this.resourceUrl}/guardar-prereserva`, datos, { observe: 'response' });
    return await lastValueFrom(data);
  }
}
