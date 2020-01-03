import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';

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

}
