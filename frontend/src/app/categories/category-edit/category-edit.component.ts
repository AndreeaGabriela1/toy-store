import { Component } from '@angular/core';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category/category.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrl: './category-edit.component.css'
})
export class CategoryEditComponent {
  category: Category = {
    id:0,
    name: '',
    imageUrl: ''
  };


  editedCategory: Category ={
    id: 0,
    name: '',
    imageUrl: ''
  }
  constructor(private categoryService : CategoryService, private route:ActivatedRoute, private router: Router){}

  ngOnInit(): void {
    // Access the selected category from the shared service
    this.editedCategory = this.categoryService.selectedCategory ;

  }


  edit(category: Category): void {
    this.categoryService.editCategory(this.editedCategory).subscribe(
      editedCategory => {
        console.log('Category editing successfully:', editedCategory)

      },
      error => {
        console.error('Error editing category:', error);
      }
    );

  }

  editCategory(category: Category): void {
    // Call createCategory method when the button is clicked
    this.edit(category);
    this.router.navigateByUrl('/category-delete');

  }

}
