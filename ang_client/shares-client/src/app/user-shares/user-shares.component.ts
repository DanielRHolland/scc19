import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';
import { Purchase } from '../models/purchase.model';


@Component({
  selector: 'app-user-shares',
  templateUrl: './user-shares.component.html',
  styleUrls: ['./user-shares.component.css']
})
export class UserSharesComponent implements OnInit {
  userShares;
  constructor(private sharesService: SharesService) { }

  ngOnInit() {
    this.sharesService.getUserShares().subscribe((data)=>{
      console.log(data);
      this.userShares = data;
    });
  }


  buyShare(symbol: string) {
    this.makeTransaction(symbol, 1); 
  }

  sellShare(symbol: string) {
    this.makeTransaction(symbol, -1); 
  }

  makeTransaction(symbol: string, change: number) {
    let purchase = new Purchase(this.userShares.userId, symbol, change);
    this.sharesService.buyShare(purchase).subscribe( x => console.log(change + ' of '+ symbol + ' bought '));
  }

}
