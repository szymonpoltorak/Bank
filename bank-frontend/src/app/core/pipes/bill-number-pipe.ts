import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'billNumber'
})
export class BillNumberPipe implements PipeTransform {
    transform(value: string): string {
        if (!value) {
            return '';
        }
        let formattedValue = '';

        for (let i = 0; i < value.length; i++) {
            if (i > 0 && i % 4 === 0) {
                formattedValue += ' ';
            }
            formattedValue += value[i];
        }

        return formattedValue;
    }
}
