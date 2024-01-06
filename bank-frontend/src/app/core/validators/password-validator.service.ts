import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from "@angular/forms";

@Injectable({
    providedIn: 'root'
})
export class PasswordValidatorService {
    private readonly MIN_PASSWORD_ENTROPY: number = 3.0;

    passwordMatchValidator(passwordFieldName: string, repeatFieldName: string): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const userPassword: AbstractControl<any, any> | null = control.get(passwordFieldName);
            const repeatPassword: AbstractControl<any, any> | null = control.get(repeatFieldName);

            if (!userPassword || !repeatPassword) {
                return null;
            }
            return userPassword.value !== repeatPassword.value ? { 'passwordMatch': true } : null;
        };
    }

    passwordEntropyValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const password: string = control.value;

            const entropy: number = this.calculateEntropy(password);

            return entropy < this.MIN_PASSWORD_ENTROPY ? { 'passwordEntropy': true } : null;
        };
    }

    private calculateEntropy(data: string): number {
        const occurrences: { [key: string]: number } = {};
        const dataSize = data.length;

        for (let i = 0; i < dataSize; i++) {
            const character = data[i];

            if (occurrences[character]) {
                occurrences[character]++;
            } else {
                occurrences[character] = 1;
            }
        }
        let entropy = 0;

        for (const key in occurrences) {
            if (occurrences.hasOwnProperty(key)) {
                const probability = occurrences[key] / dataSize;

                entropy -= probability * (Math.log2(probability) || 0);
            }
        }
        return entropy;
    }
}
