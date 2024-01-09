import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SessionsComponent } from "./sessions.component";
import { RouterPath } from "@enums/RouterPath";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: SessionsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SessionsRoutingModule {
}
