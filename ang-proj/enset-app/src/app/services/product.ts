import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Product {
  products = [
    { id: 1, name: 'computer', price: 2300, selected: true },
    { id: 2, name: 'printer', price: 1200, selected: false },
    { id: 3, name: 'smartphone', price: 1100, selected: true },
  ];
  constructor() {  }

  getAllProducts(){
    return this.products;
  }

  deleteProduct(product: any){
    this.products = this.products.filter((p:any)=>p.id != product.id);
  }

}
