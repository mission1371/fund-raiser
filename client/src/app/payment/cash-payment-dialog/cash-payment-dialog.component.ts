import {Component, Inject, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, Validators} from '@angular/forms';
import {PaymentService} from '../payment.service';
import {CashPaymentResponseModel} from './cash-payment-response.model';
import {MatInput} from '@angular/material/input';
import {ProductLine} from '../../catalog/catalog.component';

@Component({
  selector: 'app-cash-payment-dialog',
  templateUrl: './cash-payment-dialog.component.html'
})
export class CashPaymentDialogComponent {

  @ViewChild(MatInput, {static: true}) inputComponent: MatInput;

  control: FormControl = new FormControl('', [
    Validators.required,
    Validators.min(this.data.total),
    Validators.pattern(/^\d*[.,]?\d{0,2}$/),
  ]);

  constructor(
    public dialogRef: MatDialogRef<CashPaymentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CashPaymentDialogData,
    private service: PaymentService
  ) {
  }

  pay(): void {
    if (this.control.valid) {
      this.service.pay({
        amount: this.control.value,
        productCodes: this.extractProductCodes(this.data.products)
      })
        .then(value => this.handleSuccessfulPayment(value))
        .catch(reason => this.handleFailedPayment(reason));
    } else {
      this.control.markAsTouched({onlySelf: true});
      this.inputComponent.focus();
    }
  }

  close(): void {
    this.dialogRef.close(null);
  }

  private handleSuccessfulPayment(response: CashPaymentResponseModel): void {
    this.dialogRef.close(response.change);
  }

  private handleFailedPayment(reason): void {

  }

  private extractProductCodes(products: ProductLine[]): string[] {
    const codes: string[] = [];
    products.forEach(product => {
      for (let i = 0; i < product.quantity; i++) {
        codes.push(product.productCode);
      }
    });
    return codes;
  }

}

export interface CashPaymentDialogData {
  total: number;
  products: ProductLine[];
}
