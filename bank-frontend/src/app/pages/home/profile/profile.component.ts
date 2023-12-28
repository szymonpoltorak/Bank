import { Component } from '@angular/core';
import { Observable, of } from "rxjs";
import { User } from "@core/data/home/user";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
    user$: Observable<User> = of({
        name: 'John',
        surname: 'Doe',
        username: 'johndoe',
        billNumber: '123456789',
        idCard: '123456789'
    });
}
