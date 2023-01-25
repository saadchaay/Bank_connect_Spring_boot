import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule} from "@angular/forms";
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginFormComponent } from './Components/login-form/login-form.component';
import { AgentLoginComponent } from './Pages/agent-login/agent-login.component';
import { DashboardComponent } from './Pages/dashboard/dashboard.component';
import { HomeComponent } from './Pages/home/home.component';
import { RequestsComponent } from './Components/requests/requests.component';
import { TransferComponent } from './Components/transfer/transfer.component';
import { HeaderComponent } from './Components/header/header.component';
import { CustomerLoginComponent } from './Pages/customer-login/customer-login.component';
import { RegisterComponent } from './Pages/register/register.component';
import { RegisterFormComponent } from './Components/register-form/register-form.component';
import { LoaderComponent } from './Components/loader/loader.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    AgentLoginComponent,
    DashboardComponent,
    HomeComponent,
    RequestsComponent,
    TransferComponent,
    HeaderComponent,
    CustomerLoginComponent,
    RegisterComponent,
    RegisterFormComponent,
    LoaderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
