import { Component, OnInit } from '@angular/core';
import { MainComponent } from './layouts/main/main.component';
import { AccountService } from '../app/core/auth/account.service';
import { Account } from '../app/core/user/account.model';

@Component({
  selector: 'app-topbar',
  templateUrl: './app.topbar.component.html',
})
export class AppTopBarComponent implements OnInit {
  account: Account | null = null;
  constructor(public app: MainComponent, private accountService: AccountService,) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.account = account));    
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

}
