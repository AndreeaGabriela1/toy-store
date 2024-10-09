import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
@Component({
  selector: 'app-logged-form',
  templateUrl: './logged-form.component.html',
  styleUrl: './logged-form.component.css'
})
export class LoggedFormComponent {
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

  someUsers : User[]=[];

  username: string = '';

  constructor(private axiosService: AxiosService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {

        this.route.params.subscribe(params => {
          this.username = params['username'];
          console.log('Usernameu vietii:', this.username);
          console.log('token in logged::::', this.axiosService.getAuthToken());

        });

        const requestBody = { username: this.username };

        this.axiosService.request(
        "POST",
        "/logged",
        this.username).then(
        (response) => {
            this.loggedUser = response.data;
            console.log("logged firstname---->"+this.loggedUser.firstName);

          }).catch(
          (error) => {
              if (error.response.status === 401) {
                  this.axiosService.setAuthToken(null);
              } else {
                  this.loggedUser = error.response.code;
              }

        }
    );

  }

 
    showFields: boolean = false;
    showStartChat: boolean = false;
    roomId: string = '';
    id1: string = '';
    id2: string = '';

    toggleFields() {
      this.showFields = !this.showFields;
    }

    submitFields() {

      console.log('Id 1:', this.id1);
      console.log('Id 2:', this.id2);

      this.axiosService.request('POST', '/chatroom', {first_userId: this.id1	, second_userId: this.id2})
      .then(response => {
        console.log("%%%%%%%%%%%%%% Received response:", response.data); // roomId
        this.showStartChat = !this.showStartChat;
        this.roomId = response.data;
      })
      .catch(error => {
        console.log("Error occurred while creating review:", error);
      });
    }

    
 
    startChatting(){
      this.router.navigate(['/chat', this.id1, this.roomId]);

    }

}
