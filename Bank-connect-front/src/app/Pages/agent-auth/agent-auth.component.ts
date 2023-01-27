import { Component } from '@angular/core';
import {NgForm} from "@angular/forms";
import {AuthenticationService} from "../../core/services/authentication.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-agent-auth',
  templateUrl: './agent-auth.component.html',
  styleUrls: ['./agent-auth.component.css']
})
export class AgentAuthComponent {

  constructor(private authService: AuthenticationService, private router: Router) {}

  login(agentForm: NgForm){
    console.log(agentForm.value);
    this.authService.agentLogin(agentForm.value).subscribe(
      (res: Object) => {
        localStorage.setItem("auth", JSON.stringify(res));
        this.router.navigate(['/agent/dashboard']).then();
      }, (err: HttpErrorResponse) => {
        console.log(err.message);
      }
    )
  }

}
