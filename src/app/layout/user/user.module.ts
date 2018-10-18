import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbCarouselModule, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';

import { StatModule } from '../../shared';
import {UserRoutingModule} from './user-routing.module';
import {UserComponent} from './user.component';

@NgModule({
    imports: [
        CommonModule,
        NgbCarouselModule.forRoot(),
        NgbAlertModule.forRoot(),
        UserRoutingModule,
        StatModule
    ],
    declarations: [
        UserComponent
    ]
})
export class UserModule {}
