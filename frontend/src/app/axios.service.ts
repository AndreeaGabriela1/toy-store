import { Injectable } from '@angular/core';
import axios from 'axios';
import { Receipt } from './models/Receipt';
@Injectable({
  providedIn: 'root'
})
export class AxiosService {

  
  constructor() {
    axios.defaults.baseURL = 'http://localhost:8080';
    axios.defaults.headers.post['Content-Type'] = 'application/json';
  }

  getAuthToken(): string | null {
   if (typeof window !== 'undefined') {
    return window.localStorage.getItem("auth_token");
  }
  return null;
  
  }

  setAuthToken(token: string | null): void {
    if (token !== null) {
      window.localStorage.setItem("auth_token", token);
    } else {
      window.localStorage.removeItem("auth_token");
    }
  }

  doLogout(): void {

    window.localStorage.removeItem("auth_token");
    window.localStorage.removeItem("user_roles");
    
  }

  request(method: string, url: string, data: any): Promise<any> {
      let headers: any = {};

      if (this.getAuthToken() !== null) {
          headers = {"Authorization": "Bearer " + this.getAuthToken()};
      }

      console.log("in AXIOS.service, user roles stored in axios.service:"+this.getUserRoles());

      return axios({
          method: method,
          url: url,
          data: data,
          headers: headers
      });
  }

  
  setUserRoles(roles: string[]): void {
    if (typeof window !== 'undefined') {
        if (roles !== null) {
            window.localStorage.setItem("user_roles", JSON.stringify(roles));
        } else {
            window.localStorage.removeItem("user_roles");
        }
    }
  }

  getUserRoles(): string[] {
      if (typeof window !== 'undefined') {
          const roles = window.localStorage.getItem("user_roles");
          return roles ? JSON.parse(roles) : [];
      } else {
          return [];
      }
  }

  isAuthenticated(): boolean {
    return (this.getAuthToken() !== null);
  }
}