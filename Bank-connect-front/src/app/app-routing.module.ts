import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { AgentLoginComponent } from './Pages/agent-login/agent-login.component'
import { LoginFormComponent } from './Components/login-form/login-form.component'
import { HomeComponent} from "./Pages/home/home.component";
import {RegisterComponent} from "./Pages/register/register.component";
import {DashboardComponent} from "./Pages/dashboard/dashboard.component";
import {AgentAuthComponent} from "./Pages/agent-auth/agent-auth.component";

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: AgentLoginComponent },
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
