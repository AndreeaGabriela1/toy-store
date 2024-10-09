import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import  SockJS from 'sockjs-client';
import { ChatMessage } from '../../models/ChatMessage';
import { BehaviorSubject } from 'rxjs';
import { AxiosService } from '../../axios.service';
@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: any
  private messageSubject: BehaviorSubject<ChatMessage[]> = new BehaviorSubject<ChatMessage[]>([]);

  constructor(private axiosService: AxiosService) { 
    this.initConnenctionSocket();
  }

  initConnenctionSocket() {
    const url = 'http://localhost:8080/chat-socket'; // Use http instead of ws
    const socket = new SockJS(url);
    this.stompClient = Stomp.over(socket);
  }

  // joinRoom(roomId: string) {
  //   this.stompClient.connect({}, ()=>{
  //     this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
  //       const messageContent = JSON.parse(messages.body);
  //       const currentMessage = this.messageSubject.getValue();
  //       currentMessage.push(messageContent);

  //       this.messageSubject.next(currentMessage);

  //     })
  //   })
  // }

  joinRoom(roomId: string): Promise<void> {

    console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + roomId);

    return new Promise<void>((resolve, reject) => {
      this.stompClient.connect({}, () => {
        this.stompClient.subscribe(`/topic/${roomId}`, (messages: any) => {
          const messageContent = JSON.parse(messages.body);
          const currentMessage = this.messageSubject.getValue();
          currentMessage.push(messageContent);
          this.messageSubject.next(currentMessage);
        });
        resolve(); // Resolve the promise once connected
      });
    });
  }


  saveMessage(chatMessage: ChatMessage){
      this.axiosService.request('POST', '/save-message', chatMessage)
      .then(response => {
        console.log("%%%%%%%%%%%%%% send message to backend"); 
    
      })
      .catch(error => {
        console.log("Error occurred while creating review:", error);
      });
  }

  sendMessage(chatMessage: ChatMessage) {





    console.log("chat.service :::::::: " + chatMessage.message + " -- roomId " + chatMessage.roomId);
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.send(`/app/chat/${chatMessage.roomId}`, {}, JSON.stringify(chatMessage));
    } else {
      console.error('STOMP client is not connected.');
    }
  }

  getMessageSubject(){
    return this.messageSubject.asObservable();
  }
  
}
