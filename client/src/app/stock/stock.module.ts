import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {MaterialDesignModule} from '../material-design.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {StockComponent} from './stock.component';
import {StockService} from './stock.service';
import {StockRoutingModule} from './stock-routing.module';
import {HttpErrorInterceptor} from '../app-http-error.interceptor';


@NgModule({
  declarations: [
    StockComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    StockRoutingModule,
    MaterialDesignModule
  ],
  providers: [
    StockService,
    {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true}
  ],
})
export class StockModule {
}

