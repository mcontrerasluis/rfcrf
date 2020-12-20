import { Moment } from 'moment';

export interface ITcTipoSol {
  id?: number;
  clave?: string;
  descripcion?: string;
  isr?: number;
  iva?: number;
  efirma?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
}

export class TcTipoSol implements ITcTipoSol {
  constructor(
    public id?: number,
    public clave?: string,
    public descripcion?: string,
    public isr?: number,
    public iva?: number,
    public efirma?: number,
    public fechaInicio?: Moment,
    public fechaFin?: Moment
  ) {}
}
