import { Component, OnInit } from '@angular/core';
import { Sort } from '@angular/material/sort';
import { SharesService } from '../shares.service';
import { Purchase } from '../models/purchase.model';
import { ApiKeyService } from '../api-key.service';
import { RatesService } from '../rates.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { isNull } from 'util';


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
  orderBy = "default";
  searchTerms = "";
  count = 25;
  constructor(private ratesService: RatesService,
     private sharesService: SharesService,
      private apiKeyService: ApiKeyService,
      private _snackBar: MatSnackBar ) { }

  ngOnInit() 
    {
     this.update();
     this.ratesService.getSymbols().subscribe(data => {
      this.currencies = data as string[];
     }) 
  }

  buyShare(symbol: string) {
    if (isNull(this.change)) {
      this.displayError("Buy/Sell Quantity must be a number");
    } else this.makeTransaction(symbol, Math.floor(this.change)); 
  }

  sellShare(symbol: string) {
    if (isNull(this.change)) {
      this.displayError("Buy/Sell Quantity must be a number");
    } else this.makeTransaction(symbol, -Math.floor(this.change)); 
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
    if (this.currency == "Original") {
      this.update();
    } else {
      var rates: {[id: string] : number;} = {};
      this.userShares.shareQuantities.forEach(sq => {
        let prevCurr: string = sq.share.sharePrice.currency;
        if (prevCurr != this.currency) {
          if (!(prevCurr+this.currency in rates)) {
            this.ratesService.getRate(this.currency, prevCurr).subscribe(data => {
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
  }

  setOrderBy(orderBy) {
    this.orderBy = orderBy;
    this.update();
  }

  update() {
      if (isNull(this.count)){
        this.displayError("Results to display must be a number");
      } else {
          this.sharesService.getUserShares(this.orderBy, this.searchTerms, Math.floor(this.count)).subscribe((data)=>{
          this.userShares = data;
          this.currencyChange();
        });
      }
  }

  sortData(sort: Sort) {
    if (sort.active && sort.direction !== '') {
      let dir = (sort.direction==='asc') ? 'Asc' : 'Desc';
      this.orderBy = sort.active + dir;
      this.update();
    }
  }

displayError(msg: string) {
  this._snackBar.open(msg, "Close", {
    duration: 5000,
  });
}
}
