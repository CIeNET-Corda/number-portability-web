import { Route } from '@angular/router';
import { NumberPortabilityComponent } from './number-portability/number-portability.component';

export const operatorRoute: Route = {
    path: 'number-portability/:id',
    component: NumberPortabilityComponent,
    data: {
        pageTitle: 'operator.title',
        defaultSort: 'id,asc'
    }
};
