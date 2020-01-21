import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectIpComponent } from './select-ip.component';

describe('SelectIpComponent', () => {
  let component: SelectIpComponent;
  let fixture: ComponentFixture<SelectIpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectIpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectIpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
