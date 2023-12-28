import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from "./profile.component";
import { HomeSharedModule } from "../home-shared/home-shared.module";
import { MatCardModule } from "@angular/material/card";
import { MatListModule } from "@angular/material/list";


@NgModule({
    declarations: [
        ProfileComponent
    ],
    imports: [
        CommonModule,
        ProfileRoutingModule,
        HomeSharedModule,
        MatCardModule,
        MatListModule
    ]
})
export class ProfileModule {
}
