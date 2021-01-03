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
