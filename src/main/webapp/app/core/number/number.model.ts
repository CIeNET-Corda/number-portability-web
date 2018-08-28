export interface INumber {
    id?: any;
    number?: string;
    operator?: string;
    status?: number;
}

export class Number implements INumber {
    constructor(public id?: any, public number?: string, public operator?: string, public status?: number) {
        this.id = id ? id : null;
        this.number = number ? number : null;
        this.operator = operator ? operator : null;
        this.status = status ? status : 1;
    }
}
