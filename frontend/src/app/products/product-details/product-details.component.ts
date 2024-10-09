import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../models/Product';
import { ProductReview } from '../../models/ProductReview';
import { AxiosService } from '../../axios.service';
import { ProductService } from '../../services/product/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css'
})
export class ProductDetailsComponent {


  productId: number=0;
  product: Product= new Product();
  reviews: ProductReview[] = []; 
  username: string='';

  constructor(private productService: ProductService, private axiosService: AxiosService, private router: Router, private activatedRoute: ActivatedRoute){}
  
  ngOnInit(): void{
    this.activatedRoute.params.subscribe(params =>{
      this.productId = +params['id'];
      this.username = params['username'];
      console.log("aici am ajuns!!!" + this.productId);
    });

    this.setProductDetails();
    this.setReviews();

  }

  setProductDetails(){


    this.axiosService.request('POST', '/product-id', this.productId)
      .then(response => {
        console.log("Received response: PRODUCT from id", response.data);
        this.product = response.data;
      })
      .catch(error => {
        console.log("Error occurred while creating review:", error);

      });

  }
  setReviews(){

    this.axiosService.request('POST', '/review-id', this.productId)
    .then(response => {
      console.log("Received response: PRODUCT from id", response.data);
      this.reviews = response.data;
    })
    .catch(error => {
      console.log("Error occurred while creating review:", error);

    });
undefined
  }

  getStarIcons(stars: number | undefined) : string {
    if (stars === undefined) return ''; 
    let starIcons = '';
    for (let i = 1; i <= 5; i++) {
      if (i <= stars) {
        starIcons += '★'; 
      } else {
        starIcons += '☆'; 
      }
    }
    return starIcons;
  }

  addToCart(product:Product, username: string): any{
    console.log("!!!!!!!!!!!----> test here:" + product + " *** " + this.username);

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
    console.log("$$$$$ " + product.name + " -- "+ username);
    this.router.navigate(['/product-review'], { queryParams: { product: JSON.stringify(product), username: username } });
  }

}
