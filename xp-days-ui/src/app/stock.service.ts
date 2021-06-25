import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {filter, map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http: HttpClient) {
  }

  public getOverview(): Observable<any> {
    return this.http.get('http://localhost:8080/stonks/');
  }

  //what the actual fuck is this?
  //https://stackoverflow.com/questions/49065527/how-to-handle-json-stream-issued-by-spring-boot-2-in-angular-5
  public getStockValueStream(id: string | undefined): Observable<any> {
    return this.http.get('http://localhost:8080/stonks/' + id + '/stream?delay=4', {
      observe: 'events',
      responseType: 'text',
      reportProgress: true
    })
      .pipe(filter((res: any) => res.type === 3))
      .pipe(map((res: any) => {
        const lastResult = res.partialText.trim().split('\n').pop();
        return JSON.parse(lastResult);
      }));
  }
}
