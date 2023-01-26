import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { CustomerLoginComponent} from "./Pages/customer-login/customer-login.component";
import { LoginFormComponent } from './Components/login-form/login-form.component'
import { HomeComponent} from "./Pages/home/home.component";
import {RegisterComponent} from "./Pages/register/register.component";
import {TransferComponent} from "./Components/transfer/transfer.component";
import {DepositComponent} from "./Components/deposit/deposit.component";
import {WithdrawComponent} from "./Components/withdraw/withdraw.component";
import {DashboardComponent} from "./Pages/dashboard/dashboard.component";
import {AgentAuthComponent} from "./Pages/agent-auth/agent-auth.component";

const routes: Routes = [
  { path: '', component: HomeComponent, children:[
      {path: 'transfer', component: TransferComponent},
      {path: 'deposit', component: DepositComponent},
      {path: 'withdraw', component: WithdrawComponent},
    ]},
  { path: 'login', component: CustomerLoginComponent },
  { path: 'test' , component: LoginFormComponent},
  { path: 'register' , component: RegisterComponent},
  { path: 'auth-agent' , component: AgentAuthComponent},
  { path: 'agent/dashboard' , component: DashboardComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
