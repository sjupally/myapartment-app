import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberincomeComponent } from './memberincome.component';

describe('MemberincomeComponent', () => {
  let component: MemberincomeComponent;
  let fixture: ComponentFixture<MemberincomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MemberincomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberincomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
