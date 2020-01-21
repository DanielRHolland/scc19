import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharesListComponent } from './shares-list/shares-list.component'
import { UserSharesComponent } from './user-shares/user-shares.component'
import { LoginComponent } from './login/login.component'
import { MainComponent } from './main/main.component'

const routes: Routes = [
  {path:'listall', component: SharesListComponent},
  {path:'usershares', component: UserSharesComponent},
  {path:'', component: LoginComponent},
  {path:'main', component: MainComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
