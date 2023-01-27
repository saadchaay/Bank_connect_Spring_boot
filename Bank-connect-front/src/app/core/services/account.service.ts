import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Request} from "../models/request";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "../models/account";
import {environment} from "../../../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {}

  requestAccount(token: String): Observable<Request[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Request[]>(this.apiUrl.requestsAccount, {headers});
  }

  activateAccount(token: String, id: number): Observable<String>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.apiUrl.activateAccount+''+id, {headers, responseType: "text"});
  }

  disableAccount(token: String, id: number): Observable<String>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.apiUrl.disableAccount+''+id, {headers, responseType: "text"});
  }

  deleteAccount(token: String, id: number): Observable<String>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(this.apiUrl.deleteAccount+''+id, {headers, responseType: "text"});
  }

  transfer(formData:Object, headers: Object): Observable<String>{
    //@ts-ignore
    return this.http.post(this.apiUrl.transfer, formData,{headers,responseType: "text"})
  }

  deposit(formData:Object, headers: Object): Observable<String>{
    //@ts-ignore
    return this.http.post(this.apiUrl.deposit, formData,{headers,responseType: "text"})
  }

  withdraw(formData:Object, headers: Object): Observable<String>{
    //@ts-ignore
    return this.http.post(this.apiUrl.withdraw, formData,{headers,responseType: "text"})
  }
  getAccount(headers: Object): Observable<Account>{
    //@ts-ignore
    return this.http.get<Account>(this.apiUrl.getAccount,{headers})
  }

}
