import { Component, OnInit } from '@angular/core';
import { MainComponent } from './layouts/main/main.component';

@Component({
  selector: 'app-menu',
  template: `
    <ul class="layout-menu">
      <li app-menuitem *ngFor="let item of model; let i = index" [item]="item" [index]="i" [root]="true"></li>
    </ul>
  `,
})
export class AppMenuComponent implements OnInit {
  model?: any[];

  constructor(public app: MainComponent) {}

  ngOnInit() {
    this.model = [
      { label: 'Inicio', icon: 'pi pi-fw pi-home', routerLink: ['/'] },
      {
        label: 'Región Fronteriza',
        icon: 'pi pi-fw pi-compass',
        routerLink: ['utilities'],
        items: [          
          { label: 'Registro de Solicitud', icon: 'pi pi-fw pi-external-link', routerLink: ['td-reg-front'] },
          { label: 'Catalogo Manifiesto', icon: 'pi pi-fw pi-directions', routerLink: ['tc-manifes'] },
          { label: 'Catalogo Solicitudes', icon: 'pi pi-fw pi-search', routerLink: ['tc-tipo-sol'] },
          { label: 'Catalogo Impuestos', icon: 'pi pi-fw pi-pencil', routerLink: ['tc-tipo-imp'] },
          { label: 'Catalogo Validaciones', icon: 'pi pi-fw pi-star-o', routerLink: ['tc-valida'] },
          { label: 'Catalogo Ejercicios', icon: 'pi pi-fw pi-th-large', routerLink: ['tc-ejerc'] },
        ],
      },
      {
        label: 'Salir',
        icon: 'pi pi-fw pi-info-circle',
        routerLink: ['/documentation'],
      },
    ];
  }
}
