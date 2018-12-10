import {Component, OnInit} from '@angular/core';
import {Unit} from '../user/unit.model';
import {UnitService} from '../user/unit.service';

// @ts-ignore
@Component({
    selector: 'app-memberincome',
    templateUrl: './memberincome.component.html',
    styleUrls: ['./memberincome.component.scss']
})
export class MemberIncomeComponent implements OnInit {

    public units: Array<Unit> = [];

    constructor(private unitService: UnitService) {
    }

    ngOnInit() {
        this.getMemberIncome('2nd');
    }

    getMemberIncome(floorNo: string) {
        this.unitService.getUnitsByFloor(floorNo).subscribe((res: any[]) => {
            this.units = res;
        });
    }

}
