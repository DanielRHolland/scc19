import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExchangeRatesComponent } from './exchange-rates/exchange-rates.component'
import { SharesListComponent } from './shares-list/shares-list.component'

const routes: Routes = [
  {path:'rates', component: ExchangeRatesComponent},
  {path:'listall', component: SharesListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
