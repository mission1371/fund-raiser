import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CatalogModel} from './catalog.model';

@Injectable({providedIn: 'root'})
export class CatalogService {

  constructor(private http: HttpClient) {
  }

  fetchByProductType(type: number): Observable<CatalogModel[]> {
    const parameters = new HttpParams().set('type', String(type));
    return this.http.get<CatalogModel[]>('/api/product', {params: parameters});
  }
}
