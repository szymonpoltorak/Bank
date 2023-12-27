import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from "./home.component";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatStepperModule } from "@angular/material/stepper";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatInputModule } from "@angular/material/input";
import { MatTableModule } from "@angular/material/table";
import { MatListModule } from "@angular/material/list";
import { BillNumberPipe } from "@core/pipes/bill-number-pipe";


@NgModule({
    declarations: [
        HomeComponent,
        BillNumberPipe
    ],
    imports: [
        CommonModule,
        HomeRoutingModule,
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        MatCardModule,
        MatStepperModule,
        MatExpansionModule,
        MatInputModule,
        MatTableModule,
        MatListModule,
    ]
})
export class HomeModule {
}
