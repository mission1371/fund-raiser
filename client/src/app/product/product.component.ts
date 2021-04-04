import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ProductModel} from './product.model';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent {

  @Input()
  details: ProductModel;

  @Input()
  amount: number;

  @Output()
  removed: EventEmitter<ProductModel> = new EventEmitter<ProductModel>();

  @Output()
  selected: EventEmitter<ProductModel> = new EventEmitter<ProductModel>();

  @Output()
  removeAll: EventEmitter<ProductModel> = new EventEmitter<ProductModel>();

  removeFromBasket(): void {
    this.removed.emit(this.details);
  }

  clearProductFromBasket(): void {
    this.removeAll.emit(this.details);
  }

  addToBasket(): void {
    this.selected.emit(this.details);
  }
}
