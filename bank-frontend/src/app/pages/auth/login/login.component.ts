import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormValidatorService} from "@core/validators/form-validator.service";
import {LoginRequest} from "@core/data/auth/login-request";
import {AuthService} from "@core/services/auth/auth.service";
import {Observable, of, take} from "rxjs";
import {AuthResponse} from "@core/data/auth/auth-response";
import {AuthConstants} from "@enums/auth/AuthConstants";
import {StorageKeys} from "@enums/auth/StorageKeys";
import {UtilService} from "@core/services/utils/util.service";
import {UserService} from "@core/services/utils/user.service";
import {RouterPath} from "@enums/RouterPath";
import {FormFieldNames} from "@enums/auth/FormFieldNames";
import {AuthApiCalls} from "@enums/auth/AuthApiCalls";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    protected readonly MAX_NUM_OF_TRIES: number = 5;
    private readonly MINIMUM_DELAY: number = 3000;
    protected currentDelay: number = 0;
    protected numOfTries: number = 0;
    private multiplyBy: number = 1;
    protected loginForm !: FormGroup;
    protected currentUsername: string = "";

    constructor(public loginValidatorService: FormValidatorService,
                private authService: AuthService,
                private utilService: UtilService,
                private userService: UserService) {
    }

    ngOnInit(): void {
        this.loginForm = this.loginValidatorService.buildLoginFormGroup();
        this.loginValidatorService.passwordControl.disable();
        this.loginForm.reset();
    }

    submitForm(): void {
        if (this.loginForm.invalid) {
            this.numOfTries++;

            this.checkIfUserExceededMaxNumOfTriesAndBlock();

            return;
        }
        const request: LoginRequest = this.buildLoginRequest();

        const phone: string = this.loginForm.get("telephone")?.value;

        if (phone != undefined && phone !== "") {
            console.error(JSON.parse(AuthApiCalls.ERROR_FOUND));

            return;
        }
        this.authService.loginUser(request)
            .pipe(take(1))
            .subscribe((data: AuthResponse): void => {
                console.log(data);
                if (data.authToken === AuthConstants.NO_TOKEN) {
                    return;
                }
                const username: string = this.loginForm.get(FormFieldNames.EMAIL_FIELD)?.value;

                this.userService.setUserAuthentication = true;

                this.authService.saveData(data);

                this.utilService.addValueToStorage(StorageKeys.USERNAME, username);
                this.utilService.navigate(RouterPath.HOME_LOGIN_PATH);
            });
    }

    private checkIfUserExceededMaxNumOfTriesAndBlock(): void {
        if (this.numOfTries !== this.MAX_NUM_OF_TRIES) {
            return;
        }
        this.loginForm.disable();

        this.currentDelay = this.MINIMUM_DELAY * this.multiplyBy;

        setTimeout(() => {
            this.loginForm.enable();

            this.numOfTries = 0;

            this.multiplyBy++;
        }, this.currentDelay);
    }

    private buildLoginRequest(): LoginRequest {
        const loginRequest: LoginRequest = new LoginRequest();

        loginRequest.username = this.loginForm.get(FormFieldNames.EMAIL_FIELD)!.value;
        const serializedString: string = this.serializePassword();

        console.log(serializedString);

        return loginRequest;
    }

    getPasswordCombinationForUser(): void {
        const username: string = this.loginForm.get(FormFieldNames.EMAIL_FIELD)?.value;

        this.loginValidatorService.emailControl.disable();
        this.loginValidatorService.passwordControl.enable();

        this.currentUsername = username;
    }

    private serializePassword() {
        const values: string[] = ["first", "second", "third", "fourth", "fifth", "sixth"];
        let serializedString: string = "";

        for (const value of values) {
            const currentValue: string = this.loginValidatorService.passwordCombinationGroup.get(value)?.value;

            serializedString += `${currentValue};`;
        }
        return serializedString;
    }
}
