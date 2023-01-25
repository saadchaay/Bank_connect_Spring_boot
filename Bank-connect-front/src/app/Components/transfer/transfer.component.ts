import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit{
  amount!: number;
  accountNumber!: string;

  accountType!: string;
  name!: string;

  constructor(private http: HttpClient,
              private router: Router) {}

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


    this.http.post('http://localhost:8080/customer/transfer', formData,{headers: headers, responseType: "text"})
      .subscribe(response => {
        console.log(response)
        this.router.navigate(['/test']).then();
      });
}
}
