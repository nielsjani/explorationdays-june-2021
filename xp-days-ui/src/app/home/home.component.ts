import {Component, OnInit} from '@angular/core';
import {StockService} from "../stock.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  overviewItems?: Observable<any>;
  portfolioGeneralInfo?: any[];
  portfolioValues?: Observable<any>;

  constructor(private stockService: StockService) {
  }

  ngOnInit(): void {
    this.overviewItems = this.stockService.getOverview();
    this.stockService.getPortfolio()
      .subscribe(res => {
        this.portfolioGeneralInfo = res;
        this.portfolioValues = this.stockService.getPortfolioValues();
      });
  }


  getOverview(): Observable<any> | undefined {
    return this.overviewItems;
  }

  getPortfolioInfoFor(id: number): string | undefined {
    let portfolioInfoForStock = this.getPortfolioAmountForStock(id);
    if (portfolioInfoForStock) {
      return 'You own ' + portfolioInfoForStock + ' stocks';
    }
    return undefined;
  }

  getPortfolioAmountForStock(id: number) {
    if (this.portfolioGeneralInfo) {
      let portfolioInfo = this.portfolioGeneralInfo.filter(info => info.stockId === id)[0];
      return portfolioInfo.amount;
    }
    return undefined
  }

  getStockWorthFor(id: number, mostRecentStockPrices: any) {
    if (!mostRecentStockPrices) {
      return "Fetching most recent stock prices...";
    }
    let worth = this.getStockWorth(mostRecentStockPrices, id);
    return "A single stock is worth " + worth;
  }

  private getStockWorth(mostRecentStockPrices: any, stockId: number) {
    return mostRecentStockPrices
      .filter((stock: any) => stock.id === stockId)
      .map((stock: any) => stock.value)[0];
  }

  getTotalStockWorthAmount(id: number, mostRecentStockPrices: any) {
    return this.getStockWorth(mostRecentStockPrices, id) * this.getPortfolioAmountForStock(id)
  }

  getTotalStockWorth(id: number, mostRecentStockPrices: any) {
    let totalStocksWorth = this.getTotalStockWorthAmount(id, mostRecentStockPrices);
    return "Total worth: " + totalStocksWorth;
  }

  calculateTotal(overviewItems: any, pfVals: any) {
    // @ts-ignore
    let sum = (x, y) => x + y;
    return overviewItems.map((oitem: any) => this.getTotalStockWorthAmount(oitem.id, pfVals)).reduce(sum);
  }
}
