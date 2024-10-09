import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
import { Observable } from 'rxjs';
import { Product } from '../../models/Product';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  selectedUser:  User = {
    id:0,
    firstName: '',
    lastName: '',
    username: '',
    email_address: '',
    imageurl: '',
    loginTime: new Date(),
    logoutTime: new Date()
  };


  constructor(private axiosService: AxiosService) {}

  getAllUsers(): Observable<User[]> {
    return new Observable<User[]>(observer => {
      this.axiosService.request('GET', '/user', {})
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

  getCartProducts(username: string): Observable<Product[]> {
    return new Observable<Product[]>(observer => {
      this.axiosService.request('POST', '/cart-product', username)
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


  getFavProducts(username: string): Observable<Product[]> {
    return new Observable<Product[]>(observer => {
      this.axiosService.request('POST', '/fav-prod', username)
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

  createUser(userData: any): Observable<User> {
    return new Observable<User>(observer => {
      this.axiosService.request('POST', '/user-create', userData)
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

  deleteUser(userData: any): Observable<User> {
    console.log("AAAAAAAAAAAA: " + userData.id);
    console.log("AAAAAAAAAAAA: " + userData.lastName);
    console.log("AAAAAAAAAAAA: " + userData.firstName);
    console.log("AAAAAAAAAAAA: " + userData.email_address);
    console.log("AAAAAAAAAAAA: " + userData.username);
    console.log("AAAAAAAAAAAA: " + userData.birthdate);
    return new Observable<User>(observer => {
      this.axiosService.request('POST', '/user-delete', userData)
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

  setUserToEdit(userData: User):void{
    this.selectedUser = userData;
  }
  // editUser(userData: any): Observable<User> {
  //   return new Observable<User>(observer => {
  //     this.axiosService.request('POST', '/user-edit', userData)
  //       .then(response => {
  //         observer.next(response.data);
  //         observer.complete();
  //       })
  //       .catch(error => {
  //         observer.error(error);
  //         observer.complete();
  //       });
  //   });
  // }}
  editUser(userData: User): Observable<User> {
    return new Observable<User>(observer => {
      this.axiosService.request('POST', '/user-edit', userData)
        .then(response => {
          observer.next(response.data);
          observer.complete();
        })
        .catch(error => {
          observer.error(error);
          observer.complete();
        });
    });
  }}