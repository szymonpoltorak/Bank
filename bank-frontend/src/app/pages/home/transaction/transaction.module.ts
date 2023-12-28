import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TransactionRoutingModule } from './transaction-routing.module';
import { TransactionComponent } from "./transaction.component";
import { HomeSharedModule } from "../home-shared/home-shared.module";
import { MatStepperModule } from "@angular/material/stepper";
import { MatInputModule } from "@angular/material/input";
import { ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatListModule } from "@angular/material/list";
import { MatFormFieldModule } from "@angular/material/form-field";
import { BillNumberPipe } from "@core/pipes/bill-number-pipe";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";


@NgModule({
    declarations: [
        TransactionComponent
    ],
    imports: [
        CommonModule,
        TransactionRoutingModule,
        HomeSharedModule,
        MatStepperModule,
        MatInputModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatListModule,
        BillNumberPipe,
        MatProgressSpinnerModule,
    ]
})
export class TransactionModule {
}
