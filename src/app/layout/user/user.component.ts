import { UnitService } from "./unit.service";
import { Component, OnInit } from "@angular/core";
import { routerTransition } from "../../router.animations";
import { Unit } from "./unit.model";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"],
  animations: [routerTransition()]
})
export class UserComponent implements OnInit {
  public units: Array<Unit> = [];
  searchText = '';
  constructor(private unitService: UnitService) {}

  ngOnInit() {
    this.getUnits();
  }

  getUnits() {
    this.unitService.getAll().subscribe((res: any[]) => {
      this.units = res;
    });
  }  
}
