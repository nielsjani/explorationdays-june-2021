import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  constructor(  private route: ActivatedRoute) { }

  ngOnInit(): void {
    console.log('Detail of ' + this.route.snapshot.paramMap.get('id'))
  }

}
