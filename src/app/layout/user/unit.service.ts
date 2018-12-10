import {Member} from './../adduser/member.model';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class UnitService {
    constructor(private httpClient: HttpClient) {
    }

    API_URL = 'http://localhost:8090';
    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
            'Access-Control-Allow-Headers':
                'Origin, X-Requested-With, Content-Type, Accept'
        })
    };

    getAll() {
        return this.httpClient.get(`${this.API_URL}/unit/`, this.httpOptions);
    }

    getUnitBlocks() {
        return this.httpClient.get(`${this.API_URL}/unit/blocks`, this.httpOptions);
    }

    getUnitFloors() {
        return this.httpClient.get(`${this.API_URL}/unit/floors`, this.httpOptions);
    }

    addMember(memeber: Member) {
        this.httpClient.post(`${this.API_URL}/member/`, memeber, this.httpOptions).subscribe(
            data => {
                console.log('POST Request is successful ', data);
            },
            error => {
                console.log('Error', error);
            }
        );
    }

    getUnitsByFloor(floorNo: string) {
        return this.httpClient.get(`${this.API_URL}/unitsByFloor?floorNo=` + floorNo, this.httpOptions);
    }
}
