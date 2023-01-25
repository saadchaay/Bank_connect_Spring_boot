import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  email!: string;
  password!: string;

  constructor(private http: HttpClient,
              private router: Router) {}

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


    this.http.post('http://localhost:8080/auth/customer', formData)
      .subscribe(response => {
        console.log("response")
        // @ts-ignore
        const token = response['token'];
        // @ts-ignore
        const customer = response['customer'];
        // localStorage.setItem('tokenCst', token);
        localStorage.setItem('customer', JSON.stringify(response));
        this.router.navigate(['/']).then();
      });
  }

}
