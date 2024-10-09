import { Component } from '@angular/core';
import { Product } from '../../models/Product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductReview } from '../../models/ProductReview';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-product-review',
  templateUrl: './product-review.component.html',
  styleUrl: './product-review.component.css'
})
export class ProductReviewComponent {
  product: Product = {
    id:0,
    name: '',
    description: '',
    price: 0,
    imageurl: ''
  };

  review: ProductReview = new ProductReview();

  username: string = '';

  constructor(private router: Router, private axiosService: AxiosService, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.username = params['username'];
      this.product = JSON.parse(params['product']);
    });
  }

  createReview(reviewProduct: ProductReview) {
  
      console.log("Sending request to create review:", reviewProduct);
      this.axiosService.request('POST', '/review-create', reviewProduct)
        .then(response => {
          console.log("Received response:", response.data);
        })
        .catch(error => {
          console.log("Error occurred while creating review:", error);

        });
  }

  submitReview() {


    this.review.productId = this.product.id;
    this.review.username = this.username;
    this.createReview(this.review);
    console.log("user:::" + this.username);
    this.router.navigate(['/product'], { queryParams: { username: this.username } });

  }
}
