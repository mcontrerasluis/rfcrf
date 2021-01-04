import { Component, OnInit, Input } from '@angular/core';
import { ITcManifes } from '../../app/shared/model/servicios/tc-manifes.model';
import { FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'jhi-control-manifestacion',
  templateUrl: './control-manifestacion.component.html',
  styleUrls: ['./control-manifestacion.component.scss']
})
export class ControlManifestacionComponent implements OnInit {

  constructor(private fb: FormBuilder,) { }

  ngOnInit(): void {
  }

}
