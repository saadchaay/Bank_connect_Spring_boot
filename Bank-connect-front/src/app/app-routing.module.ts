import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { CustomerLoginComponent} from "./Pages/customer-login/customer-login.component";
import { LoginFormComponent } from './Components/login-form/login-form.component'
import { HomeComponent} from "./Pages/home/home.component";
import {RegisterComponent} from "./Pages/register/register.component";
import {TransferComponent} from "./Components/transfer/transfer.component";

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: AgentLoginComponent },
  { path: 'test' , component: LoginFormComponent},
  { path: 'register' , component: RegisterComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
