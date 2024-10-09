import { Injectable } from '@angular/core';
import { Category } from '../../models/Category';
import { AxiosService } from '../../axios.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  selectedCategory : Category = {
    id:0,
    name: '',
    imageUrl:''
  };

  

  constructor(private axiosService: AxiosService) {}

  getAllCategories(): Observable<Category[]> {
    return new Observable<Category[]>(observer => {
      this.axiosService.request('GET', '/category', {})
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

  createCategory(categoriesData: any): Observable<Category> {
    return new Observable<Category>(observer => {
      this.axiosService.request('POST', '/category-create', categoriesData)
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

  deleteCategory(categoriesData: any): Observable<Category> {
    return new Observable<Category>(observer => {
      this.axiosService.request('POST', '/category-delete', categoriesData)
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

  setCategoryToEdit(categoriesData: Category):void{
    this.selectedCategory = categoriesData;
  }
  editCategory(categoriesData: any): Observable<Category> {
    return new Observable<Category>(observer => {
      this.axiosService.request('POST', '/category-edit', categoriesData)
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
