import { Component } from '@angular/core';
import { User } from '../../models/User';
import { UserService } from '../../services/user/user.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrl: './user-delete.component.css'
})
export class UserDeleteComponent {
  users: User[] = [];

  constructor(private userService : UserService, private route:ActivatedRoute, private router:Router){

  }

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void{
    this.userService.getAllUsers().subscribe(
      users => {
        this.users = users;
      },
      error => {
        console.error('Error fetching users:', error);
      }
    );
  }

  deleteUser(user: User): void {

    this.userService.deleteUser(user).subscribe(
      () => {
        console.log('User deleted successfully');
        this.fetchUsers();
      },
      error => {
        console.error('Error deleting user:', error);
      }
    );
  }

  editUser(user: User):void{
    this.userService.setUserToEdit(user);
    this.router.navigate(['/user-edit', user]);
  }
}
