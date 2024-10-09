import { Component,  } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
@Component({
  selector: 'app-login-activity',
  templateUrl: './login-activity.component.html',
  styleUrl: './login-activity.component.css'
})
export class LoginActivityComponent {


  someUsers : User[]=[];
  startYear: number=0;
  startMonth: number=0;
  startDay: number=0;
  startHour: number=0;
  startMinute: number=0;
  startSecond: number=0;

  endYear: number=0;
  endMonth: number=0;
  endDay: number=0;
  endHour: number=0;
  endMinute: number=0;
  endSecond: number=0;


  startDate : Date = new Date();
  endDate : Date = new Date();
  
  constructor(private axiosService: AxiosService, private route: ActivatedRoute) { }

  
  ngOnInit(): void {
  }


  submitForm() {
    this.startDate = new Date(this.startYear, this.startMonth - 1, this.startDay, this.startHour, this.startMinute, this.startSecond);
    console.log('Selected Date:', this.startDate);
    this.loginActivity();
  }

  loginActivity(){
    console.log('aaSelected Date:', this.startDate);

    const dateString: string = this.startDate.toISOString();


    this.axiosService.request(
      "POST",
      "/login-activity",
      dateString).then(

      (response) => {          
          this.someUsers = response.data;
          console.log("_________________________________________");
          this.someUsers?.forEach((item) => {
            console.log("user:"+item); // Access each element using item
        });
  
      }).catch(
      (error) => {
          if (error.response.status === 401) {
              this.axiosService.setAuthToken(null);
          } else {
              this.someUsers = error.response.code;
          }
  
      });

  }

}