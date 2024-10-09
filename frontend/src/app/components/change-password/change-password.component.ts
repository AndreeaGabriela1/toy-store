import { Component } from '@angular/core';
import { MinimalUser } from '../../models/MinimalUser';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  
  minimalUser: MinimalUser = {
    firstName: '',
    lastName: '',
    username: '',
    email_address: '',
    password: ''
  }

  repeat_password: string = '';

  constructor(private router: Router,private axiosService: AxiosService){}

  isFormValid() {
    return this.minimalUser.firstName && this.minimalUser.lastName && this.minimalUser.email_address && this.minimalUser.username && this.minimalUser.password 
    && this.repeat_password && this.minimalUser.password === this.repeat_password;
  }

  onSubmit() {
    if (!this.isFormValid()) {
      console.log('Please fill in all fields.');
      return;
    }

    console.log('Form submitted:', {
      firstName: this.minimalUser.firstName,
      lastName: this.minimalUser.lastName,
      email_address: this.minimalUser.email_address,
      username: this.minimalUser.username,
      password: this.minimalUser.password,
      repeat_password: this.repeat_password
    });



    this.axiosService.request('POST', '/forgot-password', this.minimalUser)
      .then(response => {
        console.log("Received response:", response.data);
      })
      .catch(error => {
        console.log("Error occurred while creating review:", error);

      });

    this.router.navigate(['/home']);
  }
}
