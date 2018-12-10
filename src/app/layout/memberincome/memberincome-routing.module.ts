import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MemberIncomeComponent} from './memberincome.component';


const routes: Routes = [
    {
        path: '', component: MemberIncomeComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MemberIncomeRoutingModule {
}
