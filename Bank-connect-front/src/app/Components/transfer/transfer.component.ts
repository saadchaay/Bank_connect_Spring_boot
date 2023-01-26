import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {CustomerManagementService} from "../../core/services/customer-management.service";

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit{
  amount!: number;
  accountNumber!: string;



  constructor(private http: HttpClient,
              private router: Router,
              private cstService: CustomerManagementService) {}

  ngOnInit(): void {
    // @ts-ignore
    this.name = JSON.parse(localStorage.getItem('customer')).customer.name;
    // @ts-ignore
    this.accountNumber = JSON.parse(localStorage.getItem('customer')).customer.accountNumber;

  }

  transfer() {

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


      this.cstService.transfer(formData,headers)
      .subscribe(response => {
        console.log(response)
        this.router.navigate(['/']).then();
      });
}
}
