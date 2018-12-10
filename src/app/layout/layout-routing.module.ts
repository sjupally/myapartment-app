import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            { path: '', redirectTo: 'dashboard', pathMatch: 'prefix' },
            { path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule' },
            { path: 'user', loadChildren: './user/user.module#UserModule' },
            { path: 'addUser', loadChildren: './adduser/adduser.module#AddUserModule' },
            { path: 'memberIncome', loadChildren: './memberincome/memberincome.module#MemberIncomeModule' },
            { path: 'forms', loadChildren: './form/form.module#FormModule' },
        ]
    }
];


@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LayoutRoutingModule {}
