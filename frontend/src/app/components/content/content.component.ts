import { Component } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { User } from '../../models/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent {
	componentToShow: string = "welcome";

	loggedUser: User = {
		id:0,
		firstName: '',
		lastName: '',
		username: '',
		email_address: '',
		imageurl: '',
		loginTime: new Date(),
		logoutTime: new Date()
	  };

	username:string='';
	
	constructor(private axiosService: AxiosService, private router: Router) {

	 }

	showComponent(componentToShow: string): void {
    this.componentToShow = componentToShow;
  }

	onLogin(input: any): void {
		this.axiosService.request(
		    "POST",
		    "/login",
		    {
		        username: input.username,
		        password: input.password
		    }).then(
		    response => {
				console.log("parola:"+input.password);
				console.log("token:"+response.data.token);
				console.log("roles:"+response.data.roles);

		        this.axiosService.setAuthToken(response.data.token);
				this.axiosService.setUserRoles(response.data.roles); //added

		        this.componentToShow = "logged";
				this.username = response.data.username;
				console.log("@@@@@@@@@@@@@@@@@@@@@@@@: "+this.username + " --- "+response.data.username);

				this.loggedUser = response.data;
				this.router.navigate(['/logged',this.username]);
				
				// this.axiosService.setAuthToken(response.data.token);
		        // this.componentToShow = "messages";

		    }).catch(
		    error => {
		        this.axiosService.setAuthToken(null);
		        this.componentToShow = "welcome";
		    }
		);

	}

	onRegister(input: any): void {

		this.axiosService.request(
		    "POST",
		    "/register",
		    {
		        firstName: input.firstName,
		        lastName: input.lastName,
				email_address: input.email_address,
				username: input.username,
		        password: input.password
		    }).then(
		    response => {
		        this.axiosService.setAuthToken(response.data.token);
		        this.componentToShow = "messages";
		    }).catch(
		    error => {
		        this.axiosService.setAuthToken(null);
		        this.componentToShow = "welcome";
		    }
		);
	}

}