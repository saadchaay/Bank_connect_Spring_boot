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

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    AgentLoginComponent,
    DashboardComponent,
    HomeComponent,
    RequestsComponent,
    TransferComponent
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
