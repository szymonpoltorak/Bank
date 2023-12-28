import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskbarComponent } from './taskbar/taskbar.component';
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatInputModule } from "@angular/material/input";
import { AccountComponent } from './account/account.component';
import { MatCardModule } from "@angular/material/card";
import { BillNumberPipe } from "@core/pipes/bill-number-pipe";



@NgModule({
    declarations: [
        TaskbarComponent,
        AccountComponent
    ],
    exports: [
        TaskbarComponent,
        AccountComponent
    ],
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatToolbarModule,
        MatInputModule,
        MatCardModule,
        BillNumberPipe
    ]
})
export class HomeSharedModule { }
