import { Component } from '@angular/core';
import {StockService} from "./stock.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'xp-days-ui';

  constructor(private stockService: StockService) {

  }

}
