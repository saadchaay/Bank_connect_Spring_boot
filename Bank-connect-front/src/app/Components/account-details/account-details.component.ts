import {Component, OnInit} from '@angular/core';
import {Customer} from "../../core/models/customer";
import {AccountService} from "../../core/services/account.service";
import {HttpHeaders} from "@angular/common/http";
import {Account} from "../../core/models/account";

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit{
  accountType!: string;
  name!: string;
  accountNumber!:string;
  customer!: Customer;
  account!: Account;

  constructor(private accServise: AccountService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.name = JSON.parse(localStorage.getItem('customer')).customer.name;
    // @ts-ignore
    this.accountNumber = JSON.parse(localStorage.getItem('customer')).customer.accountNumber;
    this.getAccount()
  }
  getAccount(){
    // @ts-ignore
    const token = JSON.parse(localStorage.getItem('customer')).token
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.accServise.getAccount(headers).subscribe(
      (response: Account) => {
        this.account = response;
        console.log(this.account)
      }
    )

  }
}
