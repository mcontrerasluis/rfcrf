import { ITdRegFront } from 'app/shared/model/servicios/td-reg-front.model';

export interface ITcManifes {
  id?: number;
  clave?: string;
  descripcion?: string;
  moral?: number;
  fisica?: number;
  isr?: number;
  iva?: number;
  rfnorte?: number;
  rfsur?: number;
  s01?: number;
  s02?: number;
  s03?: number;
  s04?: number;
  activa: boolean;
  generals?: ITdRegFront[];
}

export class TcManifes implements ITcManifes {
  constructor(
    public id?: number,
    public clave?: string,
    public descripcion?: string,
    public moral?: number,
    public fisica?: number,
    public isr?: number,
    public iva?: number,
    public rfnorte?: number,
    public rfsur?: number,
    public s01?: number,
    public s02?: number,
    public s03?: number,
    public s04?: number,
    public activa: boolean = false,
    public generals?: ITdRegFront[]
  ) {}
}
