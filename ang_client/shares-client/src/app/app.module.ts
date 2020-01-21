import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule }   from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharesListComponent } from './shares-list/shares-list.component';
import { UserSharesComponent } from './user-shares/user-shares.component';
import { LoginComponent } from './login/login.component';
import { ApiKeyService } from './api-key.service';
import { MenuComponent } from './menu/menu.component';
import { MainComponent } from './main/main.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSortModule } from '@angular/material/sort';
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { SelectIpComponent } from './select-ip/select-ip.component'
import { IpService } from './ip.service'



@NgModule({
  declarations: [
    AppComponent,
    SharesListComponent,
    UserSharesComponent,
    LoginComponent,
    MenuComponent,
    MainComponent,
    SelectIpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatRadioModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatSortModule,
    MatSnackBarModule
  ],
  providers: [ApiKeyService, IpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
