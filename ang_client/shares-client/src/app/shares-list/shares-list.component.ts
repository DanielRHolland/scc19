import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';
import { Purchase } from '../models/purchase.model';

@Component({
  selector: 'app-shares-list',
  templateUrl: './shares-list.component.html',
  styleUrls: ['./shares-list.component.css']
})
export class SharesListComponent implements OnInit {
  shares;
  constructor(private sharesService: SharesService) { }

  ngOnInit() {
    this.sharesService.getShares().subscribe((data)=>{
      console.log(data);
      this.shares = data;
    });
  }

  addShare(symbol: string) {
    this.sharesService.buyShare(new Purchase(symbol, 0)).subscribe(data => console.log(data));
  }
}
