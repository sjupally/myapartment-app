import { AdduserComponent } from './adduser.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbCarouselModule, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';

import { StatModule } from '../../shared';
import { FormsModule } from '@angular/forms';
import { AddUserRoutingModule } from './adduser-routing.module';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbCarouselModule.forRoot(),
        NgbAlertModule.forRoot(),
        AddUserRoutingModule,
        StatModule,
        MDBBootstrapModule.forRoot(),
    ],
    declarations: [
        AdduserComponent
    ]
})
export class AddUserModule {}
