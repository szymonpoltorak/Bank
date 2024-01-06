import { Component, Input } from '@angular/core';
import { RouterPath } from "@enums/RouterPath";
import { AuthService } from "@core/services/auth/auth.service";
import { take } from "rxjs";
import { UtilService } from "@core/services/utils/util.service";

@Component({
    selector: 'app-taskbar',
    templateUrl: './taskbar.component.html',
    styleUrls: ['./taskbar.component.scss']
})
export class TaskbarComponent {
    protected readonly RouterPath = RouterPath;
    @Input() currentRoute !: RouterPath;

    constructor(private authService: AuthService,
                private utilService: UtilService) {
    }

    logoutUser(): void {
        this.authService
            .logoutUser()
            .pipe(take(1))
            .subscribe(() => {
                this.utilService.clearStorage();
                this.changeRoute(RouterPath.LOGIN_DIRECT);
            });
    }

    changeRoute(routerPath: RouterPath): void {
        this.utilService.navigate(routerPath);
    }
}
