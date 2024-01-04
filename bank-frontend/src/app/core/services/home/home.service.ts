import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { HistoryTransaction } from "@core/data/home/history-transaction";
import { environment } from "@environments/environment";
import { Account } from "@core/data/home/account";
import { Transaction } from "@core/data/home/transaction";

@Injectable({
    providedIn: 'root'
})
export class HomeService {
    constructor(private http: HttpClient) {
    }

    getTransactionsHistory(): Observable<HistoryTransaction[]> {
        return this.http.get<HistoryTransaction[]>(`${ environment.httpBackend }/api/v1/transaction/getTransactions`);
    }

    getAccountDetails(): Observable<Account> {
        return this.http.get<Account>(`${ environment.httpBackend }/api/v1/account/details`);
    }

    makeNewTransaction(currentTransaction: Transaction): Observable<void> {
        return this.http.post<void>(`${ environment.httpBackend }/api/v1/transaction/makeTransaction`, currentTransaction);
    }
}
