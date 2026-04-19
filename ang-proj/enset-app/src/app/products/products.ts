import { Component, OnInit } from '@angular/core';
import { NgIf } from '@angular/common';
import { Product } from '../services/product';

@Component({
  selector: 'app-products',
  imports: [NgIf],
  templateUrl: './products.html',
  styleUrl: './products.css',
  standalone: true,
})
export class Products implements OnInit {
  products: Array<any> =[]; // products! ou products: any

  constructor( private productService : Product) {
  }
  ngOnInit(): void {
this.getAllProducts();
  }
  getAllProducts(){
    this.productService.getAllProducts().subscribe({
      next: resp=>{
        this.products = resp;
      },
      error: err => {
        console.log(err);
      }
    });
  }

  handleDelete(product: any) {
    let v = confirm('etes vous sur de vouloire supprimer?');
    if(v== true){
     this.productService.deleteProduct(product).subscribe({
       next: resp=>{
         this.getAllProducts();
       },
       error: err => {
         console.log(err);
       }
     });

    }
  }
}
