import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Account } from "@core/data/home/account";
import { Transaction } from "@core/data/home/transaction";

@Component({
    selector: 'app-transaction',
    templateUrl: './transaction.component.html',
    styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {
    account !: Account;
    transactionForm !: FormGroup;
    currentTransaction !: Transaction;
    isEditable: boolean = true;

    constructor(private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.account = {
            balance: '1240.00 PLN',
            billNumber: '12345678901234567890123456'
        };
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
        console.log(this.currentTransaction);
    }

    finalizeTransaction(): void {
        this.isEditable = false;
    }
}
