import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "@core/data/home/user";
import { environment } from "@environments/environment";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    constructor(private http: HttpClient) {
    }

    getUserProfileInfo(): Observable<User> {
        return this.http.get<User>(`${environment.httpBackend}/api/v1/user/info`);
    }
}
