import { Moment } from 'moment';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IConsumo {
    id?: number;
    fecha?: Moment;
    descripcion?: string;
    monto?: number;
    cliente?: ICliente;
}

export class Consumo implements IConsumo {
    constructor(public id?: number, public fecha?: Moment, public descripcion?: string, public monto?: number, public cliente?: ICliente) {}
}
