import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/Product';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product/product.service';
@Component({
  selector: 'app-fav-prod',
  templateUrl: './fav-prod.component.html',
  styleUrl: './fav-prod.component.css'
})
export class FavProdComponent {
  products: Product[] = [];
  username: string = '';

  constructor(private productService : ProductService,private axiosService: AxiosService, private userService : UserService, private route:ActivatedRoute, private router: Router){}

  
  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.username = params['user'];
    });

      this.userService.getFavProducts(this.username).subscribe(
      products => {
        this.products = products;
      },
      error => {
        console.error('Error fetching products:', error);
      }
    );

  }
  

  removeFromFavourite(product: Product, username: string) {
    product.favourite = !product.favourite;

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

