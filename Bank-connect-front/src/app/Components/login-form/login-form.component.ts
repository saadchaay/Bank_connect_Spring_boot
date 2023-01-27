import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../environment/environment";
import {CustomerManagementService} from "../../core/services/customer-management.service";
import {AuthenticationService} from "../../core/services/authentication.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  email!: string;
  password!: string;
  apiUrl = environment.apiUrl;


  constructor(private http: HttpClient,
              private router: Router,
              private authService: AuthenticationService) {}

  ngOnInit(): void {
    if(localStorage.getItem("customer") != null){
      this.router.navigate(["/"]).then();
    }
  }
  login() {

    const formData = {
      "email": this.email,
      "password": this.password
    };
    console.log(formData)


    this.authService.loginCustomer(formData)
      .subscribe(response => {
        // @ts-ignore
        const customer = response['customer'];
        // localStorage.setItem('tokenCst', token);
        localStorage.setItem('customer', JSON.stringify(response));
        this.router.navigate(['/']).then();
      });
  }

}
