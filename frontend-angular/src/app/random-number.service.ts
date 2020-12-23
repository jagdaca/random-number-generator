import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RandomNumberService {

  constructor(private httpClient: HttpClient) { }

  private AUTH_API = "http://localhost:8080/api";

  storeNumber(number: any): Observable<Object>{
    return this.httpClient.post(`${this.AUTH_API}/store`, number);
  }

  getNumbers(): Observable<any>{
    return this.httpClient.get<any[]>(`${this.AUTH_API}/numbers`);
  }

  deleteNumber(id: string): Observable<any>{
    return this.httpClient.delete<any[]>(`${this.AUTH_API}/numbers/${id}`);
  }
}
