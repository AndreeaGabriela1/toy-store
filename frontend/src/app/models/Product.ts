export class Product{
    
    id!:number;
    name!:string;
    description?:string;
    imageurl?:string;
    tags?:string[];
    favourite?:boolean = false;
    stars?:number = 0;
    price!:number;
  
}