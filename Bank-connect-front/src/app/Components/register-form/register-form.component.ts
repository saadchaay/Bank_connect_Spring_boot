import { Component } from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {CustomerManagementService} from "../../core/services/customer-management.service";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent {
  error = {
    email: '',
    password: '',
    confirmPassword: '',
    emailVerification: ''
  };
  ver = false;
  loader = false;
  publicId = '';
  accType!: String;

  constructor(private router: Router, private cstService: CustomerManagementService){}

  ngOnInit(): void {}

  register(customerInfo: NgForm){
    this.loader = true;
    if(customerInfo.value.password != customerInfo.value.confirmPassword){
      this.error.confirmPassword = "Password and confirm password doesn't match"
      this.loader = false;
    }else{
      console.log(customerInfo.value)
      this.accType = customerInfo.value.accType;
      this.cstService.registerCustomer(customerInfo.value).subscribe(
        (res: String) => {
          console.log(res);
          this.error.confirmPassword = '';
          customerInfo.reset();
          this.loader = false;
          this.ver = true;
        },
        (error: HttpErrorResponse) => {
          console.log(error.message)
          this.loader = false;
        }
      )
    }
  }

  confirmRegistration(code: String){
    this.loader = true;
    console.log("confirm code  " + Number(code));
    this.cstService.verifyCustomer(Number(code), this.accType).subscribe(
      (res: String) => {
        console.log(res);
        this.loader = false;
        this.router.navigate(['']).then();
      },
      (error: HttpErrorResponse) => {
        this.loader = false;
        this.error.emailVerification = "The code is invalid, try again!"
        console.log(error.message)
      }
    )
  }
}
