export class User{
    
    id!:number;
    firstName!:string;
    lastName!:string;
    username!:string;
    email_address!:string;
    birthdate?:object;
    roles?:string[];
    ownProducts?:object;
    saleProducts?:object;
    favorite?:object;
    cart_products?:object;
    imageurl!:string;
    loginTime!: Date;
    logoutTime!: Date;

}