import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'agendamientoApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'cita',
    data: { pageTitle: 'agendamientoApp.cita.home.title' },
    loadChildren: () => import('./cita/cita.routes'),
  },
  {
    path: 'clinica',
    data: { pageTitle: 'agendamientoApp.clinica.home.title' },
    loadChildren: () => import('./clinica/clinica.routes'),
  },
  {
    path: 'contacto',
    data: { pageTitle: 'agendamientoApp.contacto.home.title' },
    loadChildren: () => import('./contacto/contacto.routes'),
  },
  {
    path: 'direccion',
    data: { pageTitle: 'agendamientoApp.direccion.home.title' },
    loadChildren: () => import('./direccion/direccion.routes'),
  },
  {
    path: 'especialista',
    data: { pageTitle: 'agendamientoApp.especialista.home.title' },
    loadChildren: () => import('./especialista/especialista.routes'),
  },
  {
    path: 'horario-consulta',
    data: { pageTitle: 'agendamientoApp.horarioConsulta.home.title' },
    loadChildren: () => import('./horario-consulta/horario-consulta.routes'),
  },
  {
    path: 'paciente',
    data: { pageTitle: 'agendamientoApp.paciente.home.title' },
    loadChildren: () => import('./paciente/paciente.routes'),
  },
  {
    path: 'programacion',
    data: { pageTitle: 'agendamientoApp.programacion.home.title' },
    loadChildren: () => import('./programacion/programacion.routes'),
  },
  {
    path: 'sujeto',
    data: { pageTitle: 'agendamientoApp.sujeto.home.title' },
    loadChildren: () => import('./sujeto/sujeto.routes'),
  },
  {
    path: 'tipo-terapia',
    data: { pageTitle: 'agendamientoApp.tipoTerapia.home.title' },
    loadChildren: () => import('./tipo-terapia/tipo-terapia.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
