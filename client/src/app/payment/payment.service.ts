import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CashPaymentResponseModel} from './cash-payment-dialog/cash-payment-response.model';
import {CashPaymentRequestModel} from './cash-payment-dialog/cash-payment-request.model';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) {
  }

  pay(request: CashPaymentRequestModel): Promise<CashPaymentResponseModel> {
    return this.http.post<CashPaymentResponseModel>('/api/payment/cash', request).toPromise();
  }
}
