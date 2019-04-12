import { ICliente } from 'app/shared/model/cliente.model';

export interface ITarjeta {
    id?: number;
    numeroTarjeta?: string;
    ccv?: string;
    tipoTarjeta?: string;
    cliente?: ICliente;
}

export class Tarjeta implements ITarjeta {
    constructor(
        public id?: number,
        public numeroTarjeta?: string,
        public ccv?: string,
        public tipoTarjeta?: string,
        public cliente?: ICliente
    ) {}
}
