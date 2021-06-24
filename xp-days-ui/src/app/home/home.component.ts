import { Component, OnInit } from '@angular/core';
import {StockService} from "../stock.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  overviewItems?: Observable<any>;

  constructor(private stockService: StockService) { }

  ngOnInit(): void {
    this.overviewItems = this.stockService.getOverview();
  }


  getOverview(): Observable<any> | undefined {
    return this.overviewItems;
  }
}
