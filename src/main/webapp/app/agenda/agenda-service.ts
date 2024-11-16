import { HttpClient, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { lastValueFrom } from 'rxjs';
import { ICitaData } from './model/citadata.model';
import dayjs from 'dayjs';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { ICita } from 'app/entities/cita/cita.model';
@Injectable({ providedIn: 'root' })
export class AgendaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/horarios');

  async consultarCitas(
    desde: string,
    hasta: string,
    especialidad: string,
    especialistaId: number,
    estado: string,
    criterio: string,
  ): Promise<HttpResponse<ICitaData[]>> {
    const parameter = {
      desde,
      hasta,
      especialidad,
      especialistaId,
      estado,
      criterio,
    };
    const data = this.http.post<ICitaData[]>(`${this.resourceUrl}/consultarCitas`, parameter, { observe: 'response' });
    return await lastValueFrom(data);
  }

  async obtenerPacientes(): Promise<HttpResponse<IPaciente[]>> {
    const data = this.http.post<IPaciente[]>(`${this.resourceUrl}/todos-pacientes`, null, { observe: 'response' });
    return await lastValueFrom(data);
  }

  async guardarCita(datos: any): Promise<HttpResponse<ICita>> {
    const data = this.http.post<ICita>(`${this.resourceUrl}/guardar-cita`, datos, { observe: 'response' });
    return await lastValueFrom(data);
  }
}
