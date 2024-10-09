import { Product } from "./Product";
import { User } from "./User";

export class ProductReview{
    
    title!:string;
    description?:string;
    stars?:number = 0;
    productId!:number;
    username!:String;

}