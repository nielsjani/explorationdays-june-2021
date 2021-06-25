import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {StockService} from "../stock.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit, OnDestroy {
  private stockId?: string;
  stockValues: any[] = [];
  private subscription: Subscription | undefined;

  constructor(private route: ActivatedRoute, private stockService: StockService) {
  }

  ngOnInit(): void {
    this.stockId = this.route.snapshot.paramMap.get('id') as string;
    this.startStockValueSubscription();
  }

  ngOnDestroy(): void {
    this.cleanupSubscription();
  }

  private startStockValueSubscription() {
    console.log('starting stream!')
    this.subscription = this.stockService.getStockValueStream(this.stockId)
      .subscribe(
        res => this.stockValues.push(res),
        err => console.log(err),
        () => {
          this.cleanupSubscription();
          this.startStockValueSubscription();
        }
      );
  }

  getStockName() {
    if (this.stockValues.length > 0) {
      return this.stockValues[0].name;
    } else {
      return undefined;
    }
  }

  private cleanupSubscription() {
    if (this.subscription) {
      console.log('unsubscribed');
      this.subscription.unsubscribe();
    }
  }
}
