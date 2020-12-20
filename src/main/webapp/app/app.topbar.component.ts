import { Component } from '@angular/core';
import { MainComponent } from './layouts/main/main.component';

@Component({
  selector: 'app-topbar',
  templateUrl: './app.topbar.component.html',
})
export class AppTopBarComponent {
  constructor(public app: MainComponent) {}
}
