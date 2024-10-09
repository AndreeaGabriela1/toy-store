import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AxiosService } from './axios.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private axiosService: AxiosService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean 
  {
    const userRoles = this.axiosService.getUserRoles();
    console.log("SUNT IN AUTH GUARD!!!!" + userRoles);      //FOSTER                   userRoles.includes('SELLER')
    if ( userRoles.includes('CUSTOMER') ||  userRoles.includes("ADMIN")) 
    {
        console.log("AR TREBUI SA MEARGA AUTHORIZATION");
        return true;
    } 
    else 
    {
      console.log("NU MERGE");
      this.router.navigate(['/unauthorized']); 
      return false;
    }
  }
}
