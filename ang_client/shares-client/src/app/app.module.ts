import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ExchangeRatesComponent } from './exchange-rates/exchange-rates.component';
import { SharesListComponent } from './shares-list/shares-list.component';
import { UserSharesComponent } from './user-shares/user-shares.component';

@NgModule({
  declarations: [
    AppComponent,
    ExchangeRatesComponent,
    SharesListComponent,
    UserSharesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
