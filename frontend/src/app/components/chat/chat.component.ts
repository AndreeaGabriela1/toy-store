import { Component, ElementRef, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatMessage } from '../../models/ChatMessage';
import { ChatService } from '../../services/chat/chat.service';
import { User } from '../../models/User';
import { AxiosService } from '../../axios.service';
@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  messageInput: string=''
  // user: User = {
  //   id:0,
  //   firstName: '',
  //   lastName: '',
  //   username: '',
  //   email_address: '',
  //   imageurl: '',
  //   loginTime: new Date(),
  //   logoutTime: new Date()
  // };
  userId: number=0;
  roomId: string='';
  messageList: any[] = [];
 

  messageListTest: string[] = [];
  
  constructor(private axiosService: AxiosService,private chatService: ChatService,
    private route: ActivatedRoute
    ){}

  async ngOnInit(): Promise<void> {

    this.roomId = this.route.snapshot.params["roomId"];
    this.userId =  this.route.snapshot.params["userId"]; 

    await this.chatService.joinRoom(this.roomId);
    this.listenerMessage();
   await this.getOldMessages(); 
   console.log("Old messages:", this.messageListTest);
   this.setOldMessages();

  }



  async getOldMessages(): Promise<void> {
    try {
      let userIdString: string = this.userId.toString();
      const response = await this.axiosService.request("POST", "/user-messages", { id: userIdString });
      this.messageListTest = response.data;
      console.log("Old messages received:", this.messageListTest);
    } catch (error) {
      console.error("Error fetching old messages:", error);
    }
  }


   setOldMessages(){


    const messageList = this.messageListTest;

    console.log("!!!!!!!!!!!!!!!!!!!!! a  yooo message list:  "  + messageList)

    messageList.forEach((message) => {

      const chatMessage = {
        message: message,
        user: this.userId,
        roomId: this.roomId
      }as ChatMessage
  
      this.chatService.sendMessage(chatMessage);

    });
  }



  sendMessage() {
    
    const chatMessage = {
      message: this.messageInput,
      user: this.userId,
      roomId: this.roomId
    }as ChatMessage

    this.chatService.sendMessage(chatMessage);
    this.chatService.saveMessage(chatMessage)
    this.messageInput='';

  }

  listenerMessage() {


    console.log("hello from listenerMessage!")

    this.chatService.getMessageSubject().subscribe((messages: any) => {
      this.messageList = messages.map((item: ChatMessage)=> ({
        ...item,
        message_side: item.user === this.userId ? 'sender': 'receiver'
      }))
    });
  }


  // listenerMessage() {
  //   console.log("hello from listenerMessage!");
  
  //   this.chatService.getMessageSubject().subscribe((messages: any) => {
  //     this.messageList = messages.map((item: ChatMessage) => {
  //       const messageSide = item.user === this.userId ? 'sender' : 'receiver';
  //       return {
  //         ...item,
  //         message_side: messageSide
  //       };
  //     });
  //   });
  // }
}