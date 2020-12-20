import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'td-general',
        loadChildren: () => import('./servicios/td-general/td-general.module').then(m => m.ServiciosTdGeneralModule),
      },
      {
        path: 'td-reg-front',
        loadChildren: () => import('./servicios/td-reg-front/td-reg-front.module').then(m => m.ServiciosTdRegFrontModule),
      },
      {
        path: 'tc-manifes',
        loadChildren: () => import('./servicios/tc-manifes/tc-manifes.module').then(m => m.ServiciosTcManifesModule),
      },
      {
        path: 'tc-tipo-sol',
        loadChildren: () => import('./servicios/tc-tipo-sol/tc-tipo-sol.module').then(m => m.ServiciosTcTipoSolModule),
      },
      {
        path: 'tc-tipo-imp',
        loadChildren: () => import('./servicios/tc-tipo-imp/tc-tipo-imp.module').then(m => m.ServiciosTcTipoImpModule),
      },
      {
        path: 'tc-valida',
        loadChildren: () => import('./servicios/tc-valida/tc-valida.module').then(m => m.ServiciosTcValidaModule),
      },
      {
        path: 'tc-ejerc',
        loadChildren: () => import('./servicios/tc-ejerc/tc-ejerc.module').then(m => m.ServiciosTcEjercModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RfcrfEntityModule {}
