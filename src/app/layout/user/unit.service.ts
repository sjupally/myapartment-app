import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: "root"
})
export class UnitService {
  constructor(private httpClient: HttpClient) {}
  API_URL = "http://localhost:8080";
  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      "Access-Control-Allow-Headers":
        "Origin, X-Requested-With, Content-Type, Accept"
    })
  };

  getAll() {
    return this.httpClient.get(
      `${this.API_URL}/unit/`,
      this.httpOptions
    );
  }
}
