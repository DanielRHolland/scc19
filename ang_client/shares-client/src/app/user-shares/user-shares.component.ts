import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';
import { Purchase } from '../models/purchase.model';
import { ApiKeyService } from '../api-key.service';

@Component({
  selector: 'app-user-shares',
  templateUrl: './user-shares.component.html',
  styleUrls: ['./user-shares.component.css']
})
export class UserSharesComponent implements OnInit {
  userShares;
  constructor(private sharesService: SharesService, private apiKeyService: ApiKeyService) { }

  ngOnInit() {
    console.log('usc1');
    this.apiKeyService.current.subscribe( apiKey => {
      console.log("usc"+apiKey);
      if (apiKey != '') {
    this.sharesService.getUserShares().subscribe((data)=>{
      console.log(data);
      this.userShares = data;
    });
      }
    }
    );
  }


  buyShare(symbol: string) {
    this.makeTransaction(symbol, 1); 
  }

  sellShare(symbol: string) {
    this.makeTransaction(symbol, -1); 
  }

  makeTransaction(symbol: string, change: number) {
    let purchase = new Purchase(this.userShares.userId, symbol, change);
    this.sharesService.buyShare(purchase).subscribe( x => {
      this.userShares.shareQuantities.forEach( shareQuantity =>
        {
          if (shareQuantity.share.companySymbol == symbol) {
          shareQuantity.quantity += change;
          shareQuantity.share.numberOfSharesAvailable -= change;
          }
        }
      );
    });
  }

}
