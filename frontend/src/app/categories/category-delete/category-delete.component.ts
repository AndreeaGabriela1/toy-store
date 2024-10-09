import { Component } from '@angular/core';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category/category.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-delete',
  templateUrl: './category-delete.component.html',
  styleUrl: './category-delete.component.css'
})
export class CategoryDeleteComponent {
  categories: Category[] = [];

  constructor(private categoryService : CategoryService, private route:ActivatedRoute, private router:Router){

  }

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories(): void{
    this.categoryService.getAllCategories().subscribe(
      categories => {
        this.categories = categories;
      },
      error => {
        console.error('Error fetching categories:', error);
      }
    );
  }

  deleteCategory(category: Category): void {

    this.categoryService.deleteCategory(category).subscribe(
      () => {
        console.log('Category deleted successfully');
        this.fetchCategories();
      },
      error => {
        console.error('Error deleting category:', error);
      }
    );
  }

  editCategory(category: Category):void{
    this.categoryService.setCategoryToEdit(category);
    this.router.navigate(['/category-edit', category]);
  }
}
