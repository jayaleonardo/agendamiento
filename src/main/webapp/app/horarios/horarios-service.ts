/* eslint-disable prettier/prettier */
import { HttpClient, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { lastValueFrom } from 'rxjs';
@Injectable({ providedIn: 'root' })
export class HorarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/horarios');

  async especialistasPorEspecialidad(especialidad: string): Promise<HttpResponse<IEspecialista[]>> {
    const data = this.http.get<IEspecialista[]>(`${this.resourceUrl}/especialistas-por-especialidad/${especialidad}`, {
      observe: 'response',
    });
    return await lastValueFrom(data);
  }

  async buscarEspecialidades(): Promise<HttpResponse<string[]>> {
    const data = this.http.post<string[]>(`${this.resourceUrl}/listarEspecialidades`, null, { observe: 'response' });
    return await lastValueFrom(data);
  }
}
