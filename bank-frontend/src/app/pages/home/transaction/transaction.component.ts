import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Account } from "@core/data/home/account";
import { Transaction } from "@core/data/home/transaction";
import { HomeService } from "@core/services/home/home.service";
import { catchError, take } from "rxjs";

@Component({
    selector: 'app-transaction',
    templateUrl: './transaction.component.html',
    styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {
    protected account !: Account;
    protected transactionForm !: FormGroup;
    protected currentTransaction !: Transaction;
    protected isEditable: boolean = true;
    protected hasFinished: boolean = false;
    protected errorOccurred: boolean = false;

    constructor(private formBuilder: FormBuilder,
                private homeService: HomeService) {
    }

    ngOnInit(): void {
        this.homeService
            .getAccountDetails()
            .pipe(take(1))
            .subscribe((data: Account) => {
                data.balance = `${data.balance} PLN`;

                this.account = data;

                this.transactionForm = this.formBuilder.group({
                    transactionTitle: ["", [
                        Validators.required,
                        Validators.minLength(4),
                        Validators.maxLength(120),
                        Validators.pattern('^[a-zA-Z0-9 _-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$')
                    ]],
                    transactionAmount: ["", [
                        Validators.required,
                        Validators.min(1),
                        Validators.max(Number(this.account.balance.split(" ")[0])),
                        Validators.pattern("^[0-9]+(\.[0-9]{2})?$")
                    ]],
                    billNumber: ["", [
                        Validators.required,
                        Validators.minLength(26),
                        Validators.maxLength(26),
                        Validators.pattern("^[0-9]+$")
                    ]]
                });
            });
    }

    buildTransactionFromForm(): void {
        if (this.transactionForm.invalid) {
            return;
        }
        this.currentTransaction = {
            to: this.transactionForm.controls['billNumber'].value,
            title: this.transactionForm.controls['transactionTitle'].value,
            amount: Number(this.transactionForm.controls['transactionAmount'].value)
        };
    }

    finalizeTransaction(): void {
        this.isEditable = false;

        this.homeService
            .makeNewTransaction(this.currentTransaction)
            .pipe(take(1), catchError(() => {
                this.errorOccurred = true;

                return [];
            }))
            .subscribe(() => {
                this.hasFinished = true;
            });
    }
}
