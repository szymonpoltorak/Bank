import { Component, Input } from '@angular/core';
import { Account } from "@core/data/home/account";
import { Observable } from "rxjs";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent {
  @Input() account !: Observable<Account>;
}
