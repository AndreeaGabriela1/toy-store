import { Component } from '@angular/core';
import { Category } from '../../models/Category';
import { CategoryService } from '../../services/category/category.service';
import { PetService } from '../../services/pet/pet.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent {
  
  categories : Category [] =  [];

  constructor(private categoryService : CategoryService, private route:ActivatedRoute){

  }

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe(
      categories => {
         this.categories = categories;
      },
      error => {
        console.error('Error fetching categories:', error);
      }
    );
  }

}
