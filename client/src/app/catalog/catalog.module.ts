import {DEFAULT_CURRENCY_CODE, NgModule} from '@angular/core';
import {CatalogComponent} from './catalog.component';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {CatalogRoutingModule} from './catalog-routing.module';
import {MaterialDesignModule} from '../material-design.module';
import {ProductComponent} from '../product/product.component';
import {CatalogService} from './catalog.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ProductViewerComponent} from '../product-viewer/product-viewer.component';
import {PaymentModule} from '../payment/payment.module';
import {HttpErrorInterceptor} from '../app-http-error.interceptor';


@NgModule({
  declarations: [
    CatalogComponent,
    ProductComponent,
    ProductViewerComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    CatalogRoutingModule,
    PaymentModule,
    MaterialDesignModule
  ],
  providers: [
    CatalogService,
    {provide: DEFAULT_CURRENCY_CODE, useValue: 'EUR'},
    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true}
  ],
})
export class CatalogModule {
}

