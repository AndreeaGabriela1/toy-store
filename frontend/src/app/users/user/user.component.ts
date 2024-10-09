import { Component } from '@angular/core';
import { User } from '../../models/User';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { AxiosService } from '../../axios.service';
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {
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

  constructor(private axiosService: AxiosService, private userService : UserService, private route:ActivatedRoute){

  }

  ngOnInit(): void {

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

  formatDate(dateArray: any): string {
    if (!dateArray) return '';

    // const date = new Date(dateArray[0], dateArray[1] , dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6]);

    //console.log("this is the date: --> " + dateArray[0] + " " + dateArray[1] + " " +dateArray[2]+ " " + dateArray[3]+ " " + dateArray[4]+ " " + dateArray[5]+ " " + dateArray[6])
    //console.log("this is ALSO the date: --> " + date);
    const year = dateArray[0];
    const month = dateArray[1];
    const day = dateArray[2];
    const hours = dateArray[3];
    const minutes = dateArray[4];

    // Construct formatted date string
    const formattedDate = `${hours}:${minutes}, ${day}/${month}/${year}`;

    return formattedDate;
  }

}
