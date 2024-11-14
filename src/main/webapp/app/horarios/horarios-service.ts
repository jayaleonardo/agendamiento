import { HttpClient, HttpResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IEspecialista } from 'app/entities/especialista/especialista.model';
import { lastValueFrom } from 'rxjs';
import { ITurnoEspecialidad } from './model/turno.model';
import { IHorarioConsulta } from 'app/entities/horario-consulta/horario-consulta.model';
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

  async buscarTurnos(especialistaId: number, desde: Date, hasta: Date): Promise<HttpResponse<ITurnoEspecialidad[]>> {
    const parametros = {
      especialistaId,
      desde,
      hasta,
    };
    const data = this.http.post<ITurnoEspecialidad[]>(`${this.resourceUrl}/buscarTurnos`, parametros, { observe: 'response' });
    return await lastValueFrom(data);
  }

  async crearProgramacion(
    desde: Date,
    hasta: Date,
    horaInicio: string,
    horaFin: string,
    almuerzoDesde: string,
    almuerzoHasta: string,
    duracion: number,
    diasSemana: string,
    especialistaId: number,
    cantidad: number,
  ): Promise<HttpResponse<IHorarioConsulta>> {
    const parametros = {
      desde,
      hasta,
      horaInicio,
      horaFin,
      almuerzoDesde,
      almuerzoHasta,
      duracion,
      diasSemana,
      especialistaId,
      cantidad,
    };

    // eslint-disable-next-line no-console
    console.log(parametros);
    const data = this.http.post<IHorarioConsulta>(`${this.resourceUrl}/crear-programacion`, parametros, { observe: 'response' });
    return await lastValueFrom(data);
  }
}
