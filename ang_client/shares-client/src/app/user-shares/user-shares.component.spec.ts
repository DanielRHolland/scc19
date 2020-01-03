import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSharesComponent } from './user-shares.component';

describe('UserSharesComponent', () => {
  let component: UserSharesComponent;
  let fixture: ComponentFixture<UserSharesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserSharesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSharesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
