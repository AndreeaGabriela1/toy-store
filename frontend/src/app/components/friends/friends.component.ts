import { Component } from '@angular/core';
import { User } from '../../models/User';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AxiosService } from '../../axios.service';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrl: './friends.component.css'
})
export class FriendsComponent {

  userId : string = ''
  roomId : string = ''
  users: User[] = [];
  newUser: User = {
    id:0,
    firstName: '',
    lastName: '',
    username: '',
    email_address: '',
    imageurl: '',
    loginTime: new Date(),
    logoutTime: new Date()
  };

  constructor(private axiosService: AxiosService, private userService : UserService, private route:ActivatedRoute, private router:Router){

  }

  ngOnInit(): void {

    this.userId =  this.route.snapshot.params["userId"]; 

    console.log("in user.service, user roles stored in axios.service:" + this.axiosService.getUserRoles());

    this.axiosService.request(
      "GET",
      "/user",
      {}).then(
      (response) => {
        this.users = response.data;
      })
      .catch((error) => {
        console.error(error);
      });



  }

  status(loginTime: any, logoutTime: any): string{

    const loginYear = loginTime[0];
    const loginMonth = loginTime[1];
    const loginDay = loginTime[2];
    const loginHour = loginTime[3];
    const loginMinute = loginTime[4];
    const loginSecond = loginTime[5];

    const logoutYear = logoutTime[0];
    const logoutMonth = logoutTime[1];
    const logoutDay = logoutTime[2];
    const logoutHour = logoutTime[3];
    const logoutMinute = logoutTime[4];
    const logoutSecond = logoutTime[5];

    if (loginYear < logoutYear || 
        (loginYear === logoutYear && loginMonth < logoutMonth) || 
        (loginYear === logoutYear && loginMonth === logoutMonth && loginDay < logoutDay) || 
        (loginYear === logoutYear && loginMonth === logoutMonth && loginDay === logoutDay && loginHour < logoutHour) || 
        (loginYear === logoutYear && loginMonth === logoutMonth && loginDay === logoutDay && loginHour === logoutHour && loginMinute < logoutMinute) || 
        (loginYear === logoutYear && loginMonth === logoutMonth && loginDay === logoutDay && loginHour === logoutHour && loginMinute === logoutMinute && loginSecond < logoutSecond)) {
        return "⚪"; 
    } else {
        return "🟢"; 
    }
  
  }


  startChatting(anotherUserId: number ) {

    const anotherUserIdString = anotherUserId.toString();
   
    this.axiosService.request('POST', '/chatroom', {first_userId: this.userId	, second_userId: anotherUserId})
    .then(response => {
      this.roomId = response.data;
      this.router.navigate(['/chat', this.userId, this.roomId]);
    })
    .catch(error => {
      console.log("Error occurred while creating review:", error);
    });

  }

  

  // startChatting(){
  //   // this.router.navigate(['/chat', this.id1, this.roomId]);

  // }

}
