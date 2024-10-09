import { Injectable } from '@angular/core';
import { Product } from '../../models/Product';
import { AxiosService } from '../../axios.service';
import { Observable } from 'rxjs';
import { User } from '../../models/User';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

    selectedProduct : Product = {
      id:0,
      name: '',
      description: '',
      price: 0,
      imageurl: ''
    };
  

    constructor(private axiosService: AxiosService) {}

    getAllProducts(): Observable<Product[]> {
      return new Observable<Product[]>(observer => {
        this.axiosService.request('GET', '/product', {})
          .then(response => {
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }

   

    createProduct(productData: Product): Observable<Product> {

      return new Observable<Product>(observer => {
        

        console.log("SERVICE$$$$$$$$$$$44"+productData.description);
        console.log("img:::::---> "+productData.imageurl);

        this.axiosService.request('POST', '/product-create', productData)
          .then(response => {
            console.log("MERge??????" + response.data);
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }

    

    deleteProduct(productData: any): Observable<Product> {
      return new Observable<Product>(observer => {
        this.axiosService.request('POST', '/product-delete', productData)
          .then(response => {
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }

    setProductToEdit(productData: Product):void{
      this.selectedProduct = productData;
    }

    editProduct(productData: any): Observable<Product> {
      return new Observable<Product>(observer => {
        this.axiosService.request('POST', '/product-edit', productData)
          .then(response => {
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }

    addToCart(productId: number, username: string): Observable<User> {
      return new Observable<User>(observer => {
        this.axiosService.request('POST', '/add-cart',  { productId: productId, username: username } ) 
          .then(response => {
            console.log("done");
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }

    addToFavourite(productId: number, username: string): Observable<User> {
      return new Observable<User>(observer => {
        console.log("Favorite !!!!!!!!!!" + productId);
        this.axiosService.request('POST', '/add-fav',  { productId: productId, username: username } ) 
          .then(response => {
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }


    removeFromFavourite(productId: number, username: string): Observable<User> {
      return new Observable<User>(observer => {
        console.log("Favorite Removed!!!!!!!!!!" + productId);
        this.axiosService.request('POST', '/remove-fav',  { productId: productId, username: username } ) 
          .then(response => {
            observer.next(response.data);
            observer.complete();
          })
          .catch(error => {
            observer.error(error);
            observer.complete();
          });
      });
    }
  }

