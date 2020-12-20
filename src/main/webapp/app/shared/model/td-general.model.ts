import { Moment } from 'moment';

export interface ITdGeneral {
  id?: number;
  rfc?: string;
  fecha?: Moment;
  tipoSolicitudd?: string;
  folio?: string;
  estatus?: number;
  tipoSolicitudId?: number;
}

export class TdGeneral implements ITdGeneral {
  constructor(
    public id?: number,
    public rfc?: string,
    public fecha?: Moment,
    public tipoSolicitudd?: string,
    public folio?: string,
    public estatus?: number,
    public tipoSolicitudId?: number
  ) {}
}
