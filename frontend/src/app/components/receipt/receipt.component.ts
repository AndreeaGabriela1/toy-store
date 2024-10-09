import { Component, OnInit } from '@angular/core';
import { Receipt } from '../../models/Receipt';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-receipt',
  templateUrl: './receipt.component.html',
  styleUrls: ['./receipt.component.css']
})
export class ReceiptComponent implements OnInit {

  receipt: Receipt = {
    firstName: '',
    lastName: '',
    username: '',
    boughtProducts: [],
    date: ''
  };

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    if (typeof window !== 'undefined' && window.history && window.history.state) {
      const navigation = window.history.state;
      if (navigation && navigation.receipt) {
        this.receipt = navigation.receipt;
        console.log(this.receipt.firstName);
        console.log(this.receipt.lastName);
        console.log(this.receipt.username);
        console.log(this.receipt.boughtProducts);
        if (this.receipt.boughtProducts) {
          this.receipt.boughtProducts.forEach((item) => {
            console.log(item); // Access each element using item
          });
        }
        console.log("date:" + this.receipt.date);
      }
    }
  }
}
