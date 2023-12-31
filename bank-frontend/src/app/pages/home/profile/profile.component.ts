import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { User } from "@core/data/home/user";
import { RouterPath } from "@enums/RouterPath";
import { ProfileService } from "@core/services/home/profile.service";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    user$ !: Observable<User>;
    protected readonly RouterPath = RouterPath;

    constructor(private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.user$ = this.profileService.getUserProfileInfo();
    }
}
