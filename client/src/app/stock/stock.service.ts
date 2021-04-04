import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StockModel} from './stock.model';
import {StockUpdateRequestModel} from './stock-update-request.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http: HttpClient) {
  }

  update(request: StockUpdateRequestModel): Promise<StockModel> {
    return this.http.put<StockModel>('/api/inventory', request).toPromise();
  }

  loadInventory(): Observable<StockModel[]> {
    return this.http.get<StockModel[]>('/api/inventory');
  }

}
