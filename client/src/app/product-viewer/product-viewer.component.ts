import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {CatalogModel} from '../catalog/catalog.model';
import {SelectedProductModel} from './selected-product.model';
import {ProductModel} from '../product/product.model';
import {catchError, finalize, map, tap} from 'rxjs/operators';
import {CatalogService} from '../catalog/catalog.service';

@Component({
  selector: 'app-product-viewer',
  templateUrl: './product-viewer.component.html',
  styleUrls: ['./product-viewer.component.scss']
})
export class ProductViewerComponent implements OnInit {

  products: Observable<ProductModel[]>;
  selectedProducts: SelectedProductModel = new SelectedProductModel();
  showLoader = false;
  noProductAvailable = false;

  @Input()
  productType: number;
  @Output()
  selectedProductsChanged: EventEmitter<SelectedProductModel> = new EventEmitter<SelectedProductModel>();

  constructor(private service: CatalogService) {
  }

  ngOnInit(): void {
    this.load();
  }

  clearAllProducts(): void {
    this.selectedProducts = new SelectedProductModel();
  }

  reloadAllProducts(): void {
    this.load();
  }

  onRemoveAll(product: ProductModel): void {
    this.selectedProducts[product.code] = [];
    this.emitSelectedProducts();
  }

  onSelect(product: ProductModel): void {
    if (this.selectedProducts.hasOwnProperty(product.code)) {
      this.selectedProducts[product.code].push(product);
    } else {
      this.selectedProducts[product.code] = [product];
    }
    this.emitSelectedProducts();
  }

  onRemove(product: ProductModel): void {
    if (this.selectedProducts.hasOwnProperty(product.code)) {
      this.selectedProducts[product.code].pop();
    } else {
      this.selectedProducts[product.code] = [];
    }
    this.emitSelectedProducts();
  }

  private convert(catalog: CatalogModel): ProductModel {
    return {
      code: catalog.code,
      name: catalog.name,
      src: catalog.avatarUrl,
      alt: catalog.description,
      stock: catalog.stock,
      price: catalog.price,
      currencyCode: catalog.currencyCode,
    };
  }

  private emitSelectedProducts(): void {
    this.selectedProductsChanged.emit(this.selectedProducts);
  }

  private load(): void {
    this.showLoader = true;
    this.products = this.service.fetchByProductType(this.productType).pipe(
      map(values => {
        return values.map(value => this.convert(value));
      }),
      tap(values => this.noProductAvailable = values.length === 0),
      finalize(() => this.showLoader = false),
      catchError(err => {
        this.noProductAvailable = true;
        return throwError(err);
      })
    );
  }

}
