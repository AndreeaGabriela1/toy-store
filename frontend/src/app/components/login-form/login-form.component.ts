import { EventEmitter, Component, Output } from '@angular/core';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
  })
export class LoginFormComponent {

  @Output() onSubmitLoginEvent = new EventEmitter();
  @Output() onSubmitRegisterEvent = new EventEmitter();

	active: string = "login";
  firstName: string = "";
  lastName: string = "";
  password: string = "";
  email_address: string = "";
	username: string ="";

  constructor(private axiosService: AxiosService){}

  onLoginTab(): void {
		this.active = "login";
	}

	onRegisterTab(): void {
		this.active = "register";
    console.log("---------------email address: " + this.email_address);

	}

  onSubmitLogin(): void {
    this.onSubmitLoginEvent.emit({"username": this.username, "password": this.password});
  }

  onSubmitRegister(): void {
    this.onSubmitRegisterEvent.emit({"firstName": this.firstName, "lastName": this.lastName,"email_address": this.email_address, "username": this.username, "password": this.password});
  }

}