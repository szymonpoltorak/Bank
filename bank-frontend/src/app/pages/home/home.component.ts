import { Component } from '@angular/core';
import { HistoryTransaction } from "@core/data/home/history-transaction";
import { Account } from "@core/data/home/account";
import { RouterPath } from "@enums/RouterPath";


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {
    transactions: HistoryTransaction[] = [
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        },
        {
            title: 'Payment from John Doe',
            amount: 1000,
            date: new Date(),
            from: 'John Doe',
            to: 'Me'
        }
    ];
    account: Account = {
        balance: '1240.00 PLN',
        billNumber: '12345678901234567890123456'
    }
    protected readonly RouterPath = RouterPath;
}
