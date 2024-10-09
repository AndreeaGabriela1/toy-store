import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/Product';
import { Receipt } from '../../models/Receipt';
import { Router } from '@angular/router';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  products: Product[] = [];

  receipt: Receipt = {
    firstName: '',
    lastName: '',
    username: '',
    date: ''
  };

  username: string = '';

  activateReceipt: boolean = false;
  activateBuy: boolean = true;
  
  constructor(private axiosService: AxiosService, private userService : UserService, private route:ActivatedRoute, private router: Router){}

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      this.username = params['user'];
    });

      this.userService.getCartProducts(this.username).subscribe(
      products => {
        this.products = products;
      },
      error => {
        console.error('Error fetching products:', error);
      }
    );



  }

  buyProducts(username: string):void{
      this.axiosService.request(
        "POST",
        "/buy-product",
        this.username).then(
        (response) => {
          console.log(" work!!!!!!!!!!!!")

            this.receipt = response.data;

            console.log(this.receipt.firstName);
            console.log(this.receipt.lastName);
            console.log(this.receipt.username);
            console.log(this.receipt.boughtProducts);
      
            this.receipt.boughtProducts?.forEach((item) => {
              console.log(item); // Access each element using item
          });
      

            this.activateBuy = false;
            this.activateReceipt = true;
    
        }).catch(
        (error) => {
            if (error.response.status === 401) {
                this.axiosService.setAuthToken(null);
            } else {
                this.products = error.response.code;
            }
    
        }
      );
  }

  redirectReceipt(receipt: Receipt): void {
    this.router.navigate(['/receipt'], { state: { receipt } });
    //this.router.navigate(['/receipt',this.receipt]);

    this.activateReceipt = false;
  }
    
}
