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
  change = 10;
  constructor(private sharesService: SharesService, private apiKeyService: ApiKeyService) { }

  ngOnInit() {
    this.apiKeyService.current.subscribe( apiKey => {
          if (apiKey != '') {
            this.sharesService.getUserShares().subscribe((data)=>{
            this.userShares = data;
          });
      }
    }
    );
  }


  buyShare(symbol: string) {
    this.makeTransaction(symbol, this.change); 
  }

  sellShare(symbol: string) {
    this.makeTransaction(symbol, -this.change); 
  }

  makeTransaction(symbol: string, change: number) {
    let purchase = new Purchase(this.userShares.userId, symbol, change);
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

}
