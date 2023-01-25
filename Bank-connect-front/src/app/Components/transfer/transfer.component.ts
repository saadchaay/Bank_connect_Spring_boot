import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent {
  amount!: number;
  accountNumber!: string;

  constructor(private http: HttpClient,
              private router: Router) {}

  transfer() {

    const token = localStorage.getItem('token');
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
