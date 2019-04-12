export interface IAsesor {
    id?: number;
    nombre?: string;
    especialidad?: string;
}

export class Asesor implements IAsesor {
    constructor(public id?: number, public nombre?: string, public especialidad?: string) {}
}
