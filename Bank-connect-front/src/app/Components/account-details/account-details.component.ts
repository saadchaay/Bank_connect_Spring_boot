import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit{
  accountType!: string;
  name!: string;
  accountNumber!:string;
  ngOnInit(): void {
    // @ts-ignore
    this.name = JSON.parse(localStorage.getItem('customer')).customer.name;
    // @ts-ignore
    this.accountNumber = JSON.parse(localStorage.getItem('customer')).customer.accountNumber;

  }
}
