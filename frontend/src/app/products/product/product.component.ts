import { Component } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../models/Product';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user/user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {
  products: Product[] = [];
  favProducts: Product[] = []; 
  username: string = '';

  isFavorite: boolean = false;

  constructor(private router: Router, private productService : ProductService, private userService: UserService, private route:ActivatedRoute){

  }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.username = params['user'];
    });

    console.log("in product.ts testing username routing:"+this.username);
    
    this.productService.getAllProducts().subscribe(
      products => {
         this.products = products;
      },
      error => {
        console.error('Error fetching products:', error);
      }
    );


    this.userService.getFavProducts(this.username).subscribe(
      products => { 
        this.favProducts = products;
        
        // set red heart on favourite products
        for (const prod of this.products) {
          if (this.favProducts.some(fav => prod.id === fav.id)) {
              prod.favourite = true;
          }
      }

      },
      error => {
        console.error('Error fetching fav products:', error);
      }
    );

  }


  toggleFavorite(product: Product, username: string) {
    product.favourite = !product.favourite;

    if(product.favourite == true){
      this.productService.addToFavourite(product.id, username).subscribe(
        user => {
          console.log("ADDED to Favorite succesfully");
        },
        error => {
          console.error('Error adding product to cart:', error);
        }
      );
    }
    else{
      this.productService.removeFromFavourite(product.id, username).subscribe(
        user => {
          console.log("Removed from Favorite succesfully");
        },
        error => {
          console.error('Error removing product to cart:', error);
        }
      );
    }
  }


  addToCart(product:Product, username: string): any{
    this.productService.addToCart(product.id, username).subscribe(
      user => {
        console.log("ADDED succesfully");
      },
      error => {
        console.error('Error adding product to cart:', error);
      }
    );
  }

  navigateToReviewForm(product: Product, username: string) {
    console.log("%%%%%%%%%% " + product.name + " -- "+ username);
    this.router.navigate(['/product-review'], { queryParams: { product: JSON.stringify(product), username: username } });
  }

}
