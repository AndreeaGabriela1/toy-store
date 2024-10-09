import { Component } from '@angular/core';
import { User } from '../../models/User';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css'
})
export class UserEditComponent {
  user: User = {
    id:0,
    firstName: '',
    lastName: '',
    username: '',
    email_address: '',
    imageurl: '',
    loginTime: new Date(),
    logoutTime: new Date()
  };


  editedUser: User ={
    id:0,
    firstName: '',
    lastName: '',
    username: '',
    email_address: '',
    imageurl: '',
    loginTime: new Date(),
    logoutTime: new Date()
  };
  constructor(private userService : UserService, private route:ActivatedRoute, private router: Router){}

  ngOnInit(): void {
    // Access the selected user from the shared service
    this.editedUser = this.userService.selectedUser ;

  }


  edit(user: User): void {

    this.userService.editUser(this.editedUser).subscribe(
      editedUser => {
        console.log(editedUser.firstName+" "+
        editedUser.lastName + " " + 
        editedUser.username +  "  " + 
        editedUser.id +  " " + 
        editedUser.birthdate);
        console.log('User editing successfully:', editedUser)
      },
      error => {
        console.error('Error editing user:', error);
      }
    );

  }

  editUser(user: User): void {
    // Call createUser method when the button is clicked
    this.edit(user);
    this.router.navigateByUrl('/user-delete');

  }

}
