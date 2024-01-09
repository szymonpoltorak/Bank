import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "@environments/environment";
import { Session } from "@core/data/home/session";

@Injectable({
    providedIn: 'root'
})
export class SessionService {
    constructor(private http: HttpClient) {
    }

    getSessionsOnPage(page: number): Observable<Session[]> {
        return this.http.get<Session[]>(`${ environment.httpBackend }/api/v1/auth/devices/getLoggedDevices`, {
            params: {
                page: page
            }
        });
    }
}
