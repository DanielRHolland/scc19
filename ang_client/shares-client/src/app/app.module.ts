import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule }   from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ExchangeRatesComponent } from './exchange-rates/exchange-rates.component';
import { SharesListComponent } from './shares-list/shares-list.component';
import { UserSharesComponent } from './user-shares/user-shares.component';
import { LoginComponent } from './login/login.component';
import { ApiKeyService } from './api-key.service';
import { MenuComponent } from './menu/menu.component';
import { MainComponent } from './main/main.component'

@NgModule({
  declarations: [
    AppComponent,
    ExchangeRatesComponent,
    SharesListComponent,
    UserSharesComponent,
    LoginComponent,
    MenuComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [ApiKeyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
