import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SessionsRoutingModule } from './sessions-routing.module';
import { SessionsComponent } from "./sessions.component";
import { SessionComponent } from "./session/session.component";
import { MatCardModule } from "@angular/material/card";
import { MatDividerModule } from "@angular/material/divider";
import { MatPaginatorModule } from "@angular/material/paginator";
import { HomeSharedModule } from "../home-shared/home-shared.module";
import { MatIconModule } from "@angular/material/icon";


@NgModule({
    declarations: [
        SessionsComponent,
        SessionComponent
    ],
    imports: [
        CommonModule,
        SessionsRoutingModule,
        MatCardModule,
        MatDividerModule,
        MatPaginatorModule,
        HomeSharedModule,
        MatIconModule
    ]
})
export class SessionsModule {
}
