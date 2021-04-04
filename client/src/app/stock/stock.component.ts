import {Component, OnInit, ViewChild} from '@angular/core';
import {StockService} from './stock.service';
import {StockModel} from './stock.model';
import {Observable} from 'rxjs';
import {MatTable} from '@angular/material/table';
import {MatSnackBar} from '@angular/material/snack-bar';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html'
})
export class StockComponent implements OnInit {

  @ViewChild(MatTable)
  table: MatTable<StockDataModel>;

  inventory: StockDataModel[];
  displayedColumns: string[] = ['number', 'name', 'stock', 'actions'];

  constructor(private service: StockService, public snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.load().toPromise().then(inventory => {
      this.inventory = inventory;
    });
  }


  private load(): Observable<StockDataModel[]> {
    return this.service.loadInventory().pipe(map(models => models.map(model => this.convert(model))));
  }

  edit(product: StockModel, stock: number, index: number): void {
    this.service.update({productCode: product.productCode, stock}).then(value => {
      this.inventory[index] = this.convert(value);
      this.table.renderRows();
      this.snackBar.open(`Stock updated`);
    });
  }

  increaseStock(product: StockModel, addedStock: number, index: number): void {
    this.service.update({productCode: product.productCode, addedStock}).then(value => {
      this.inventory[index] = this.convert(value);
      this.table.renderRows();
      this.snackBar.open(`Added ${addedStock} ${product.name}`);
    });
  }

  private convert(model: StockModel): StockDataModel {
    return {
      productCode: model.productCode,
      name: model.name,
      stock: model.stock,
      showEditStock: false,
      showLoader: false
    };
  }
}

class StockDataModel {
  productCode: string;
  name: string;
  stock: number;
  showEditStock: boolean;
  showLoader: boolean;
}
