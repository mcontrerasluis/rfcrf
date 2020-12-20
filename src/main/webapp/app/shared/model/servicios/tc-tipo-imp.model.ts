import { Moment } from 'moment';

export interface ITcTipoImp {
  id?: number;
  clave?: string;
  descripcion?: string;
  fechaInicio?: Moment;
  fechaFin?: Moment;
}

export class TcTipoImp implements ITcTipoImp {
  constructor(
    public id?: number,
    public clave?: string,
    public descripcion?: string,
    public fechaInicio?: Moment,
    public fechaFin?: Moment
  ) {}
}
