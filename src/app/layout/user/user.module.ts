import { SearchByNamePipe } from './search-by-name.pipe';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbCarouselModule, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';

import { StatModule } from '../../shared';
import {UserRoutingModule} from './user-routing.module';
import {UserComponent} from './user.component';
import { FormsModule } from '@angular/forms';
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbCarouselModule.forRoot(),
        NgbAlertModule.forRoot(),
        UserRoutingModule,
        StatModule
    ],
    declarations: [
        UserComponent,
        SearchByNamePipe
    ]
})
export class UserModule {}
