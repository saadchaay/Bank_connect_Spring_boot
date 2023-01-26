import { Component } from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthenticationService} from "../../core/services/authentication.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-agent-auth',
  templateUrl: './agent-auth.component.html',
  styleUrls: ['./agent-auth.component.css']
})
export class AgentAuthComponent {

  constructor(private authService: AuthenticationService) {}

  login(agentForm: NgForm){
    console.log(agentForm.value);
    this.authService.agentLogin(agentForm.value).subscribe(
      (res: Object) => {
        console.log(res);
        localStorage.setItem("auth", JSON.stringify(res));
      }, (err: HttpErrorResponse) => {
        console.log(err.message);
      }
    )
  }

}
