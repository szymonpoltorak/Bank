import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskbarComponent } from './taskbar/taskbar.component';
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";



@NgModule({
    declarations: [
        TaskbarComponent
    ],
    exports: [
        TaskbarComponent
    ],
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatToolbarModule
    ]
})
export class HomeSharedModule { }
