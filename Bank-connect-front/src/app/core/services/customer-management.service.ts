import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environment/environment";
import {Customer} from "../models/customer";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CustomerManagementService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  registerCustomer(customer: Customer): Observable<String> {
    return this.http.post(this.apiUrl.registerCustomer, customer, {responseType: "text"});
  }

  verifyCustomer(codeVer: number, accType: String): Observable<String> {
    return this.http.post(this.apiUrl.verifyEmail, {verCode: codeVer,accType: accType}, {responseType: "text"})
  }




}
