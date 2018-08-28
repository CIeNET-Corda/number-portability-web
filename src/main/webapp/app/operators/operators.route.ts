import { Routes } from '@angular/router';
import { NumberPortabilityComponent } from './';
import { NpPublishNumberComponent } from './';
import { NpPortingNumberComponent } from './';

export const operatorRoute: Routes = [
    {
        path: 'number-portability/:id',
        component: NumberPortabilityComponent,
        data: {
            pageTitle: 'operator.title',
            defaultSort: 'id,asc'
        }
    },
    {
        path: 'number-portability/publish/:id',
        component: NpPublishNumberComponent,
        data: {
            pageTitle: 'operator.home.publish',
            defaultSort: 'id,asc'
        }
    },
    {
        path: 'number-portability/porting/:id',
        component: NpPortingNumberComponent,
        data: {
            pageTitle: 'operator.home.porting',
            defaultSort: 'id,asc'
        }
    }
];
