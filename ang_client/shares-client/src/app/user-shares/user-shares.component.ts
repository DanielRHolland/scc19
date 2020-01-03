import { Component, OnInit } from '@angular/core';
import { SharesService } from '../shares.service';

@Component({
  selector: 'app-user-shares',
  templateUrl: './user-shares.component.html',
  styleUrls: ['./user-shares.component.css']
})
export class UserSharesComponent implements OnInit {
  userShares;
  listUserIds = false;
  constructor(private sharesService: SharesService) { }

  ngOnInit() {
    this.sharesService.getUserShares().subscribe((data)=>{
      console.log(data);
      this.userShares = data;
    });
  }
}
