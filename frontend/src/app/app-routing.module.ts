import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
import { CategoryEditComponent } from './categories/category-edit/category-edit.component';
import { CategoryDeleteComponent } from './categories/category-delete/category-delete.component';
import { LoggedFormComponent } from './components/logged-form/logged-form.component';
import { CartComponent } from './components/cart/cart.component';
import { FavProdComponent } from './components/fav-prod/fav-prod.component';
import { ReceiptComponent } from './components/receipt/receipt.component';
import { LoginActivityComponent } from './components/login-activity/login-activity.component';
import { AuthGuard } from './auth.guard';
import { ChatComponent } from './components/chat/chat.component';
import { ProductReviewComponent } from './components/product-review/product-review.component';
import { ProductDetailsComponent } from './products/product-details/product-details.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { FriendsComponent } from './components/friends/friends.component';
const routes: Routes = [
  //{ path: 'product-create', component: ProductCreateComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN', 'SELLER'] } },
  { path: 'product', component: ProductComponent, canActivate: [AuthGuard]},
  { path: 'product-create', component: ProductCreateComponent,canActivate: [AuthGuard] },
  { path: 'product-delete', component: ProductDeleteComponent },
  { path: 'product-edit', component: ProductEditComponent },
  { path: 'pet', component: PetComponent },
  { path: 'pet-create', component: PetCreateComponent },
  { path: 'pet-delete', component: PetDeleteComponent },
  { path: 'pet-edit', component: PetEditComponent,canActivate: [AuthGuard] },
  { path: 'user', component: UserComponent, canActivate: [AuthGuard]},
  { path: 'user-delete', component: UserDeleteComponent},
  { path: 'user-edit', component: UserEditComponent},
  { path: 'category', component: CategoryComponent},
  { path: 'category-delete', component: CategoryDeleteComponent},
  { path: 'category-edit', component: CategoryEditComponent},
  { path: 'home', component: HomeComponent },
  { path: 'logged/:username', component: LoggedFormComponent,canActivate: [AuthGuard] },
  { path: 'search/:searchTerm', component:HomeComponent},
  { path: 'cart-product', component: CartComponent},
  { path: 'receipt', component: ReceiptComponent},
  { path: 'fav-prod', component: FavProdComponent, canActivate: [AuthGuard]},
  { path: 'product-review', component: ProductReviewComponent},
  { path: 'login-activity', component: LoginActivityComponent},
  { path: 'chat/:userId/:roomId', component: ChatComponent},
  { path: 'product-details/:id/:username', component: ProductDetailsComponent },
  { path: 'forgot-password', component: ChangePasswordComponent },
  { path: 'friends/:userId', component: FriendsComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
