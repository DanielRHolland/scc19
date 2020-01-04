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
    console.log(symbol);
    let purchase = new Purchase();
    purchase.userId = this.userShares.userId;
    purchase.companySymbol = symbol;
    purchase.change = 1;
    console.log(purchase);
    this.sharesService.buyShare(purchase).subscribe( x => console.log(symbol + 'purchased'));
  }

  sellShare(symbol: string) {
    console.log(symbol);
  }
}
