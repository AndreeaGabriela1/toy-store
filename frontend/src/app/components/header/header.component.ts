import { Component, Input } from '@angular/core';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
	@Input() pageTitle!: string;
	@Input() logoSrc!: string;
  @Input() loggedUser: User | null = null;  



  roles: string[] = [];
  authenticated: boolean = false

  constructor(private router: Router, private axiosService: AxiosService){}

  
  ngOnInit(): void {
    this.roles = this.axiosService.getUserRoles();
    this.authenticated  = this.axiosService.isAuthenticated();
  }

  logoutRequest() {

  console.log("Sending request to logout:", this.loggedUser);
  
  this.axiosService.request('POST', '/loggedout', this.loggedUser)
    .then(response => {
      console.log("Received response:", response.data);
    })
    .catch(error => {
      console.log("Error occurred while creating review:", error);

    });
    
   this.axiosService.doLogout();

   this.router.navigate(['/home']); 
  }


}