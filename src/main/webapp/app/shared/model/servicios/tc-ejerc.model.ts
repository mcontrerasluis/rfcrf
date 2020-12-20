import { Moment } from 'moment';

export interface ITcEjerc {
  id?: number;
  clave?: string;
  valor?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
}

export class TcEjerc implements ITcEjerc {
  constructor(public id?: number, public clave?: string, public valor?: number, public fechaInicio?: Moment, public fechaFin?: Moment) {}
}
