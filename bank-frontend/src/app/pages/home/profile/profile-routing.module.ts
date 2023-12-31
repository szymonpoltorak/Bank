import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { ProfileComponent } from "./profile.component";
import { AuthGuard } from "@core/guards/auth.guard";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: ProfileComponent,
        canActivate: [AuthGuard]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProfileRoutingModule {
}
