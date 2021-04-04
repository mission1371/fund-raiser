import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {MaterialDesignModule} from '../material-design.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CashPaymentDialogComponent} from './cash-payment-dialog/cash-payment-dialog.component';
import {PaymentService} from './payment.service';
import {HttpErrorInterceptor} from '../app-http-error.interceptor';


@NgModule({
  entryComponents: [CashPaymentDialogComponent],
  declarations: [
    CashPaymentDialogComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialDesignModule
  ],
  providers: [
    PaymentService,
    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true}
  ],
})
export class PaymentModule {
}

