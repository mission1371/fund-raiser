import {Component, QueryList, ViewChildren} from '@angular/core';
import {SelectedProductModel} from '../product-viewer/selected-product.model';
import {MatDialog} from '@angular/material/dialog';
import {CashPaymentDialogComponent} from '../payment/cash-payment-dialog/cash-payment-dialog.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ProductViewerComponent} from '../product-viewer/product-viewer.component';

@Component({
  selector: 'app-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.scss']
})
export class CatalogComponent {

  cateringTotal = 0;
  caterings: ProductLine[] = [];
  readonly cateringProductType = 1;

  donationTotal = 0;
  donatibles: ProductLine[] = [];
  readonly donatiblesProductType = 2;

  @ViewChildren(ProductViewerComponent) components: QueryList<ProductViewerComponent>;

  constructor(public dialog: MatDialog, public snackBar: MatSnackBar) {
  }

  emptyBasket(): void {
    this.components.forEach(component => component.clearAllProducts());
    this.emptyCaterings();
    this.emptyDonations();
  }

  checkout(): void {
    const products = this.collectAllProducts();
    if (products.length === 0) {
      this.snackBar.open('Please select products first');
      return;
    }
    const dialogRef = this.dialog.open(CashPaymentDialogComponent, {
      data: {total: this.getTotal(), products}
    });

    const subscription = dialogRef.afterClosed().subscribe(change => {
        if (change) {
          this.snackBar.open(`Payment successful! Change is: ${change} EUR`);
          this.reloadProductViews();
        }
        subscription.unsubscribe();
      }
    );
  }

  getTotal(): number {
    return this.cateringTotal + this.donationTotal;
  }

  updateCateringTotals(products: SelectedProductModel): void {
    const totalsAndProducts = this.calculateTotalsAndUpdateProducts(products);
    this.caterings = totalsAndProducts.products;
    this.cateringTotal = totalsAndProducts.total;
  }

  updateDonationTotals(products: SelectedProductModel): void {
    const totalsAndProducts = this.calculateTotalsAndUpdateProducts(products);
    this.donatibles = totalsAndProducts.products;
    this.donationTotal = totalsAndProducts.total;
  }

  private emptyCaterings(): void {
    this.cateringTotal = 0;
    this.caterings = [];
  }

  private emptyDonations(): void {
    this.donationTotal = 0;
    this.donatibles = [];
  }

  private reloadProductViews(): void {
    this.emptyBasket();
    this.components.forEach(component => component.reloadAllProducts());
  }

  private collectAllProducts(): ProductLine[] {
    return [...this.donatibles, ...this.caterings];
  }

  private calculateTotalsAndUpdateProducts(selectedProducts: SelectedProductModel): { total: number, products: ProductLine[] } {
    let total = 0;
    const products: ProductLine[] = [];

    for (const key in selectedProducts) {
      if (selectedProducts.hasOwnProperty(key) && selectedProducts[key].length > 0) {
        const item = selectedProducts[key][0];
        const name = item.name;
        const price = item.price;
        const quantity = selectedProducts[key].length;
        const amount = price * quantity;
        const productCode = item.code;
        products.push({name, amount, price, quantity, productCode});
        total = total + amount;
      }
    }
    return {total, products};
  }
}

export interface ProductLine {

  name: string;
  quantity: number;
  price: number;
  amount: number;
  productCode: string;

}

