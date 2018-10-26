import { Member } from "./member.model";
import { Component, OnInit, ViewChild } from "@angular/core";
import { NgForm } from "@angular/forms";
import { UnitService } from "../user/unit.service";

@Component({
  selector: "app-adduser",
  templateUrl: "./adduser.component.html",
  styleUrls: ["./adduser.component.scss"]
})
export class AdduserComponent implements OnInit {
  @ViewChild("f")
  signupForm: NgForm;
  submitted = false;
  owners = ["Owner", "Tenet"];
  public blocks: Array<string> = [];
  public floors: Array<string> = [];
  public member: Member;
  public addMemberRes: any;
  constructor(private unitService: UnitService) {}

  optionsSelect: Array<any>;
  ngOnInit() {
    this.getUnitBlocks();
    this.getUnitFloors();
  }

  onSubmit() {
    //console.log(this.signupForm.value);
    this.submitted = true;
    this.member = new Member();
    this.member.firstname = this.signupForm.value.userData.firstName;
    this.member.lastname = this.signupForm.value.userData.lastName;
    this.member.block = this.signupForm.value.userData.selectedBlock;
    this.member.floor = this.signupForm.value.userData.selectedFloor;
    this.member.flatNo = this.member.floor + " " + this.member.block;
    this.member.isdCode = "+91";
    this.member.contactNumber = this.signupForm.value.userData.contactNumber;
    this.member.email = this.signupForm.value.userData.email;
    this.member.intercom = this.signupForm.value.userData.intercomNo;
    this.member.ownerShip = this.signupForm.value.userData.ownerShip;
    //console.log(this.member);
    this.signupForm.reset();
    this.addMemberRes = this.unitService.addMember(this.member);
    //console.log(this.addMemberRes);
  }
  getUnitBlocks() {
    this.unitService.getUnitBlocks().subscribe((res: string[]) => {
      this.blocks = res;
    });
  }

  getUnitFloors() {
    this.unitService.getUnitFloors().subscribe((res: string[]) => {
      this.floors = res;
    });
  }
}
