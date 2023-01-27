import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {Credentials} from "../models/credentials";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../env/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {}

  agentLogin(credentials: Credentials): Observable<Object> {
    return this.http.post(this.apiUrl.loginAgent, credentials);
  }
  loginCustomer(formData:Object): Observable<Object>{
    return this.http.post(this.apiUrl.loginCustomer, formData)
  }

}
