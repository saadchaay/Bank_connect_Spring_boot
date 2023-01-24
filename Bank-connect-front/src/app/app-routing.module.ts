import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { AgentLoginComponent } from './Pages/agent-login/agent-login.component'
import { LoginFormComponent } from './Components/login-form/login-form.component'
import { HomeComponent} from "./Pages/home/home.component";
import { DashboardComponent } from "./Pages/dashboard/dashboard.component";

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'login', component: AgentLoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
