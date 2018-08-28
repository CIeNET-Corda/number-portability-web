import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NumberPortabilityComponent } from './';
import { NpPortingNumberComponent } from './';
import { NpPublishNumberComponent } from './';
import { NpwebappSharedModule } from 'app/shared';
import { operatorRoute } from './';

@NgModule({
    imports: [NpwebappSharedModule, RouterModule.forChild(operatorRoute)],
    declarations: [NumberPortabilityComponent, NpPortingNumberComponent, NpPublishNumberComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NpOperatorsModule {}
