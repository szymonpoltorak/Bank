import { Component, OnInit } from '@angular/core';
import { RouterPath } from "@enums/RouterPath";
import { Observable } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { SessionService } from "@core/services/home/session.service";
import { PageEvent } from "@angular/material/paginator";
import { Session } from "@core/data/home/session";


@Component({
    selector: 'app-sessions',
    templateUrl: './sessions.component.html',
    styleUrls: ['./sessions.component.scss']
})
export class SessionsComponent implements OnInit {
    protected readonly RouterPath = RouterPath;
    sessions$ !: Observable<Session[]>;

    constructor(private authService: AuthService,
                private sessionService: SessionService) {
    }

    ngOnInit(): void {
        this.sessions$ = this.sessionService.getSessionsOnPage(0);
    }

    changePage(event: PageEvent): void {
        this.sessions$ = this.sessionService.getSessionsOnPage(event.pageIndex);
    }
}
