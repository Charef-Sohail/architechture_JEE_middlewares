import { Component } from '@angular/core';

@Component({
  selector: 'app-products',
  imports: [],
  templateUrl: './products.html',
  styleUrl: './products.css',
  standalone: true
})
export class Products {

  products=[
    {id:1, name : "computer", price: 2300, selected: true},
    {id:2, name : "printer", price: 1200, selected: false},
    {id:3, name : "smartphone", price: 1100, selected: true}

  ]
}
