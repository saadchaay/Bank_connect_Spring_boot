import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {CustomerManagementService} from "../../core/services/customer-management.service";
import {AccountService} from "../../core/services/account.service";

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent {

  amount!: number;
  accountNumber!: string;

  constructor(private http: HttpClient,
              private router: Router,
              private accService: AccountService) {}
  deposit(){

    // @ts-ignore
    const token = JSON.parse(localStorage.getItem('customer')).token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    const formData = {
      "amount": this.amount,
      "accountNumber": this.accountNumber,
    };
    console.log(formData)
    console.log(headers)


    this.accService.deposit(formData,headers)
      .subscribe(response => {
        console.log(response)
        this.router.navigate(['/']).then();
      });
  }
}
