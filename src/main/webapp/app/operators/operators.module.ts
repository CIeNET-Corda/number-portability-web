import { NumberPortabilityComponent } from './number-portability/number-portability.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NpwebappSharedModule } from 'app/shared';
import { operatorRoute } from './';

@NgModule({
    imports: [NpwebappSharedModule, RouterModule.forChild([operatorRoute])],
    declarations: [NumberPortabilityComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NpOperatorsModule {}
