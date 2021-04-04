import {ProductModel} from '../product/product.model';

export class SelectedProductModel {
  [productCode: string]: ProductModel[];
}
