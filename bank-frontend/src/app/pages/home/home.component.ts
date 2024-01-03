import { Component, OnInit } from '@angular/core';
import { HistoryTransaction } from "@core/data/home/history-transaction";
import { Account } from "@core/data/home/account";
import { RouterPath } from "@enums/RouterPath";
import { HomeService } from "@core/services/home/home.service";
import { Observable } from "rxjs";


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    protected transactions !: Observable<HistoryTransaction[]>;
    protected account !: Observable<Account>;
    protected readonly RouterPath = RouterPath;

    constructor(private homeService: HomeService) {
    }

    ngOnInit(): void {
        this.transactions = this.homeService.getTransactionsHistory();
        this.account = this.homeService.getAccountDetails();
    }
}
