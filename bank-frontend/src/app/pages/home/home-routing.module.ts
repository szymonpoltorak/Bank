import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { HomeComponent } from "./home.component";
import { AuthGuard } from "@core/guards/auth.guard";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: HomeComponent,
        canActivate: [AuthGuard]
    },
    {
        path: RouterPath.TRANSACTION_PATH,
        loadChildren: () => import('./transaction/transaction.module')
            .then(m => m.TransactionModule),
        canActivate: [AuthGuard]
    },
    {
        path: RouterPath.PROFILE_PATH,
        loadChildren: () => import('./profile/profile.module')
            .then(m => m.ProfileModule),
        canActivate: [AuthGuard]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
