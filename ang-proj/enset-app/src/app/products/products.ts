import { Component, OnInit } from '@angular/core';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-products',
  imports: [NgIf],
  templateUrl: './products.html',
  styleUrl: './products.css',
  standalone: true,
})
export class Products implements OnInit {
  products: Array<any> =[]; // products! ou products: any
  ngOnInit(): void {
    this.products = [
      { id: 1, name: 'computer', price: 2300, selected: true },
      { id: 2, name: 'printer', price: 1200, selected: false },
      { id: 3, name: 'smartphone', price: 1100, selected: true },
    ];
  }

  constructor() {}

  handleDelete(product: any) {
    let v = confirm('etes vous sur de vouloire supprimer?');
    if(v== true){
      this.products = this.products.filter((p:any)=>p.id != product.id);
    }
  }
}
