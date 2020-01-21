import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';
import { Purchase } from '../models/purchase.model';
import { Sort } from '@angular/material/sort';
import { ApiKeyService } from '../api-key.service';
import { RatesService } from '../rates.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { isNull } from 'util';

@Component({
  selector: 'app-shares-list',
  templateUrl: './shares-list.component.html',
  styleUrls: ['./shares-list.component.css']
})
export class SharesListComponent implements OnInit {
  shares;
  currencies = ["GBP", "USD", "EUR"];
  currency = "GBP";
  orderBy = "default";
  searchTerms = "";
  count = 25;

  constructor(private ratesService: RatesService,
     private sharesService: SharesService,
      private apiKeyService: ApiKeyService,
      private _snackBar: MatSnackBar ) { }

  ngOnInit() {
    this.update();
    this.ratesService.getSymbols().subscribe(data => {
      this.currencies = data as string[];
     }) 
  }

  update() {
    if (isNull(this.count)){
      this.displayError("Results to display must be a number");
    } else {
        this.sharesService.getShares(this.orderBy, this.searchTerms, Math.floor(this.count)).subscribe((data)=>{
        this.shares = data;
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

currencyChange() {
    if (this.currency == "Original") {
      this.update();
    } else {
      var rates: {[id: string] : number;} = {};
      this.shares.forEach(s => {
        let prevCurr: string = s.sharePrice.currency;
        if (prevCurr != this.currency) {
          if (!(prevCurr+this.currency in rates)) {
            this.ratesService.getRate(this.currency, prevCurr).subscribe(data => {
                let rate = data as number;
                rates[prevCurr+this.currency] = rate;
                s.sharePrice.currency = this.currency;
                s.sharePrice.value *= rate;
              });
          } else {
            let rate = rates[prevCurr+this.currency];
            s.sharePrice.currency = this.currency;
            s.sharePrice.value *= rate;
          }
        }
      });
    }
  }

  setOrderBy(orderBy) {
    this.orderBy = orderBy;
    this.update();
  }


  addShare(symbol: string) {
    this.sharesService.buyShare(new Purchase(symbol, 0)).subscribe(data => {
      if(data["msg"]=="Created") {
        this.displayError("Added new share to your Shares: "+symbol);
      } else if(data["msg"]=="Updated") {
       this.displayError("You are already tracking " +symbol); 
      } else {
        this.displayError("Action Failed");
      }
    });
  }
}
