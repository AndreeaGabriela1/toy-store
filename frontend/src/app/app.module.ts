import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ButtonsComponent } from './components/buttons/buttons.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { WelcomeContentComponent } from './welcome-content/welcome-content.component';
import { AuthContentComponent } from './components/auth-content/auth-content.component';
import { ContentComponent } from './components/content/content.component';

import { AxiosService } from './axios.service';
import { ProductComponent } from './products/product/product.component';
import { HomeComponent } from './components/home/home.component';
import { ProductCreateComponent } from './products/product-create/product-create.component';
import { ProductDeleteComponent } from './products/product-delete/product-delete.component';
import { ProductEditComponent } from './products/product-edit/product-edit.component';
import { PetComponent } from './pets/pet/pet.component';
import { PetCreateComponent } from './pets/pet-create/pet-create.component';
import { PetDeleteComponent } from './pets/pet-delete/pet-delete.component';
import { PetEditComponent } from './pets/pet-edit/pet-edit.component';
import { UserComponent } from './users/user/user.component';
import { UserDeleteComponent } from './users/user-delete/user-delete.component';
import { UserEditComponent } from './users/user-edit/user-edit.component';
import { CategoryComponent } from './categories/category/category.component';
import { CategoryDeleteComponent } from './categories/category-delete/category-delete.component';
import { CategoryEditComponent } from './categories/category-edit/category-edit.component';
import { LoggedFormComponent } from './components/logged-form/logged-form.component';
import { SearchComponent } from './components/search/search.component';
import { CartComponent } from './components/cart/cart.component';
import { ReceiptComponent } from './components/receipt/receipt.component';
import {MatIconModule} from '@angular/material/icon';
import { FavProdComponent } from './components/fav-prod/fav-prod.component';
import { LoginActivityComponent } from './components/login-activity/login-activity.component';
import { ChatComponent } from './components/chat/chat.component';
import { ProductReviewComponent } from './components/product-review/product-review.component';
import { ProductDetailsComponent } from './products/product-details/product-details.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { FriendsComponent } from './components/friends/friends.component';

@NgModule({
  declarations: [
    AppComponent,
    ButtonsComponent,
    HeaderComponent,
    LoginFormComponent,
    WelcomeContentComponent,
    AuthContentComponent,
    ContentComponent,
    ProductComponent,
    HomeComponent,
    ProductCreateComponent,
    ProductDeleteComponent,
    ProductEditComponent,
    PetComponent,
    PetCreateComponent,
    PetDeleteComponent,
    PetEditComponent,
    UserComponent,
    UserDeleteComponent,
    UserEditComponent,
    CategoryComponent,
    CategoryDeleteComponent,
    CategoryEditComponent,
    LoggedFormComponent,
    SearchComponent,
    CartComponent,
    ReceiptComponent,
    FavProdComponent,
    LoginActivityComponent,
    ChatComponent,
    ProductReviewComponent,
    ProductDetailsComponent,
    ChangePasswordComponent,
    FriendsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MatIconModule,
  ],
  providers: [AxiosService],
  bootstrap: [AppComponent]
})
export class AppModule { }