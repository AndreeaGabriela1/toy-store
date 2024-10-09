import { Component } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../models/Product';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-delete',
  templateUrl: './product-delete.component.html',
  styleUrl: './product-delete.component.css'
})
export class ProductDeleteComponent {
  products: Product[] = [];

  constructor(private productService : ProductService, private route:ActivatedRoute, private router:Router){

  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts(): void{
    this.productService.getAllProducts().subscribe(
      products => {
        this.products = products;
      },
      error => {
        console.error('Error fetching products:', error);
      }
    );
  }

  deleteProduct(product: Product): void {

    this.productService.deleteProduct(product).subscribe(
      () => {
        console.log('Product deleted successfully');
        this.fetchProducts();
      },
      error => {
        console.error('Error deleting product:', error);
      }
    );
  }

  editProduct(product: Product):void{
    this.productService.setProductToEdit(product);
    this.router.navigate(['/product-edit', product]);
  }
}