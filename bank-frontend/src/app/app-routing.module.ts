import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";

const routes: Routes = [
    {
        path: RouterPath.AUTH_PATH,
        loadChildren: () => import("./pages/auth/auth.module")
            .then(module => module.AuthModule)
    },
    {
        path: RouterPath.CURRENT_PATH,
        redirectTo: "auth/login",
        pathMatch: 'full'
    },
    {
        path: "**",
        redirectTo: "/auth/login",
        pathMatch: "full"
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
