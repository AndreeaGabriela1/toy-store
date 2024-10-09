import { Component } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../models/Product';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-edit',
  templateUrl: './product-edit.component.html',
  styleUrl: './product-edit.component.css'
})
export class ProductEditComponent {
  product: Product = {
    id:0,
    name: '',
    description: '',
    price: 0,
    imageurl: ''
  };


  editedProduct: Product ={
    id: 0,
    name: '',
    description: '',
    price: 0,
    imageurl: ''
  }
  constructor(private productService : ProductService, private route:ActivatedRoute, private router: Router){}

  ngOnInit(): void {
    // Access the selected product from the shared service
    this.editedProduct = this.productService.selectedProduct ;

  }


  edit(product: Product): void {
    this.productService.editProduct(this.editedProduct).subscribe(
      editedProduct => {
        console.log('Product editing successfully:', editedProduct)

      },
      error => {
        console.error('Error editing product:', error);
      }
    );

  }

  editProduct(product: Product): void {
    this.edit(product);
    this.router.navigateByUrl('/product-delete');
  }


}
