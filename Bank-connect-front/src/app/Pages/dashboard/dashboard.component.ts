import {Component, OnInit} from '@angular/core';

import {AccountService} from "../../core/services/account.service";
import {Request} from "../../core/models/request";
import {HttpErrorResponse} from "@angular/common/http";
import {LoggedIn} from "../../core/models/logged-in";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{

  requests!: Request[];
  auth!: LoggedIn;

  constructor(private accService: AccountService) {}

  ngOnInit() {
    // @ts-ignore
    this.auth = JSON.parse(localStorage.getItem("auth"));
    this.fetchRequests(this.auth.token);
  }

  logout(){
    console.log("logout");
  }

  fetchRequests(token: String){
    this.accService.requestAccount(token).subscribe(
      (res: Request[]) => {
        this.requests = res;
      },(error: HttpErrorResponse) => {
        console.log(error.message);
      }
    );
  }

  activateCustomer(customerId: number){
    this.accService.activateAccount(this.auth.token, customerId).subscribe(
      (res: String) => {
        console.log(res);
        this.ngOnInit();
      }, (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    )
  }

  disabledAccount(customerId: number){
    this.accService.disableAccount(this.auth.token, customerId).subscribe(
      (res: String) => {
        console.log(res);
        this.ngOnInit();
      }, (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    )
  }

  deleteCustomer(customerId: number){
    this.accService.deleteAccount(this.auth.token, customerId).subscribe(
      (res: String) => {
        console.log(res);
        this.ngOnInit();
      }, (error: HttpErrorResponse) => {
        console.log(error.message);
      }
    )
  }
}
