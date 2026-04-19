import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Product {
  constructor(private http: HttpClient) {

  }

  getAllProducts():Observable<any>{
    return this.http.get("http://localhost:8083/products");
  }

  deleteProduct(product: any){
    return this.http.delete("http://localhost:8083/products/"+product.id);
  }

}
