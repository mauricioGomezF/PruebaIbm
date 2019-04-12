import { ITarjeta } from 'app/shared/model/tarjeta.model';
import { IConsumo } from 'app/shared/model/consumo.model';

export interface ICliente {
    id?: number;
    nombre?: string;
    direccion?: string;
    ciudad?: string;
    telefono?: string;
    tarjetas?: ITarjeta[];
    consumos?: IConsumo[];
}

export class Cliente implements ICliente {
    constructor(
        public id?: number,
        public nombre?: string,
        public direccion?: string,
        public ciudad?: string,
        public telefono?: string,
        public tarjetas?: ITarjeta[],
        public consumos?: IConsumo[]
    ) {}
}
