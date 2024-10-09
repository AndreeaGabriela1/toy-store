import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavProdComponent } from './fav-prod.component';

describe('FavProdComponent', () => {
  let component: FavProdComponent;
  let fixture: ComponentFixture<FavProdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FavProdComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FavProdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
