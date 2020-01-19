import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';
import { Purchase } from '../models/purchase.model';
import { ApiKeyService } from '../api-key.service';
import { RatesService } from '../rates.service';


@Component({
  selector: 'app-user-shares',
  templateUrl: './user-shares.component.html',
  styleUrls: ['./user-shares.component.css']
})
export class UserSharesComponent implements OnInit {
  userShares;
  change = 10;
  currencies = ["GBP", "USD", "EUR"];
  currency = "GBP";
  orderBy = "default"
  constructor(private ratesService: RatesService, private sharesService: SharesService, private apiKeyService: ApiKeyService) { }

  ngOnInit() 
    {
     this.update(); 
  }


  buyShare(symbol: string) {
    this.makeTransaction(symbol, this.change); 
  }

  sellShare(symbol: string) {
    this.makeTransaction(symbol, -this.change); 
  }

  makeTransaction(symbol: string, change: number) {
    let purchase = new Purchase(symbol, change);
    this.sharesService.buyShare(purchase).subscribe( data => {
      var updatedSQ = data['obj'];
      this.userShares.shareQuantities.forEach( shareQuantity =>
        {
          if (shareQuantity.share.companySymbol == symbol) {
          shareQuantity.quantity = updatedSQ.quantity;
          shareQuantity.share.numberOfSharesAvailable = updatedSQ.share.numberOfSharesAvailable;
          }
        }
      );
    });
  }

  currencyChange() {
    var rates: {[id: string] : number;} = {};
    this.userShares.shareQuantities.forEach(sq => {
      let prevCurr: string = sq.share.sharePrice.currency;
      if (prevCurr != this.currency) {
        if (!(prevCurr+this.currency in rates)) {
          this.ratesService.getRate(prevCurr, this.currency).subscribe(data => {
              let rate = data as number;
              rates[prevCurr+this.currency] = rate;
              sq.share.sharePrice.currency = this.currency;
              sq.share.sharePrice.value *= rate;
              console.log(rates);
              console.log("Added rate " +prevCurr+this.currency+rate);
            });
        } else {
          let rate = rates[prevCurr+this.currency];
          sq.share.sharePrice.currency = this.currency;
          sq.share.sharePrice.value *= rate;
        }
        
      }
    });
  }

  setOrderBy(orderBy) {
    this.orderBy = orderBy;
    this.update();
    this.currencyChange();
  }

  update() {
    this.sharesService.getUserShares(this.orderBy).subscribe((data)=>{
      this.userShares = data;
    });
  }


}
