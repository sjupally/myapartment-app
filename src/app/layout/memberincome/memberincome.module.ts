import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbCarouselModule, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';

import { StatModule } from '../../shared';
import { FormsModule } from '@angular/forms';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import {MemberIncomeRoutingModule} from './memberincome-routing.module';
import {MemberIncomeComponent} from './memberincome.component';
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbCarouselModule.forRoot(),
        NgbAlertModule.forRoot(),
        MemberIncomeRoutingModule,
        StatModule,
        MDBBootstrapModule.forRoot(),
    ],
    declarations: [
        MemberIncomeComponent
    ]
})
export class MemberIncomeModule {}
