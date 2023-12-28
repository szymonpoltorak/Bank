import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { TransactionComponent } from "./transaction.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: TransactionComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TransactionRoutingModule {
}
