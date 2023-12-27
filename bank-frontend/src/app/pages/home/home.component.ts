import { Component } from '@angular/core';
import { Transaction } from "@core/data/home/transaction";
import { Account } from "@core/data/home/account";


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {
    transactions: Transaction[] = [
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
}
