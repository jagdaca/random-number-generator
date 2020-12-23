import { Component, OnInit } from '@angular/core';

import { ChartDataSets, ChartOptions } from 'chart.js';
import { Label } from 'ng2-charts';
import { NgbCalendar, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { RandomNumberService } from '../random-number.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  //holds details of the newly generated number
  temp: any = {};
  dateOfDeletedNumber: any;

  generatedNumbers: any[];
  numberId: number;
  chartCalendar: NgbDateStruct;

  chartData = new Array;
  //boolean check to make sure that the chart will wait for available data before rendering
  isDataProcessed: boolean;
  //for pagination
  p: number = 1;

  barChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      xAxes: [{
          stacked: true
      }],
      yAxes: [{
          stacked: true
      }]
    }
  };
  barChartLabels: Label[] = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
    { data: this.chartData, label: 'Number Count' }
  ];

  constructor(private randomNumberService: RandomNumberService, private calendar: NgbCalendar) { }

  ngOnInit(): void {
    this.isDataProcessed = false;
    this.chartCalendar = this.calendar.getToday();
    this.getGeneratedNumbers();
  }

  randomDate() {
    var start = new Date(+ new Date(2020, 11, 1));
    var end = new Date(+ new Date());
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  }

  getGeneratedNumbers() {
    this.randomNumberService.getNumbers().subscribe(data => {
      this.generatedNumbers = [];

      data.forEach( el => {
        el.timestamp = new Date(el.timestamp).toLocaleString();
      });

      this.generatedNumbers = data;
      if(this.generatedNumbers.length > 0) {
        this.numberId = this.generatedNumbers[0].id;
      }
      else {
        this.numberId = 0;
      }
      
      this.isDataProcessed = true;
      this.generateChart("init");
    });
  }

  generate(caller: string) {
    var ts;
    this.temp = {};

    if(this.generatedNumbers.length < 1) {
      this.numberId = 1;
    }
    else {
      this.numberId++
    }
    

    this.temp.id = this.numberId;
    this.temp.number = Math.floor((Math.random() * 10) + 1);
    ts = new Date(+ new Date());

    if(caller == "now") {
      this.temp.timestamp = ts;
    }
    else {
      this.temp.timestamp = this.randomDate();
    }

    this.randomNumberService.storeNumber(this.temp).subscribe(
      data => {
        if("success".localeCompare(data.message)) {
          this.temp.timestamp = new Date(this.temp.timestamp).toLocaleString();
          this.generatedNumbers.unshift(this.temp);
          this.generateChart("generate");
        }
      },
      err => {
        console.log("failed");
      }
    );
  }

  deleteNumber(id: number) {
    this.randomNumberService.deleteNumber(id.toString()).subscribe( 
      data => {
        if("success".localeCompare(data.message)) {
          var index = this.generatedNumbers.findIndex((obj => obj.id == id));

          this.temp.number = this.generatedNumbers[index].number;
          this.dateOfDeletedNumber = this.generatedNumbers[index].timestamp;

          this.generatedNumbers.splice(index, 1);

          this.generateChart("delete");
        }
      },
      err => {
        console.log("failed");
      }
    );
  }

  generateChart(caller: string) {
    let date = new Date(this.chartCalendar.year + "-" + this.chartCalendar.month + "-" + this.chartCalendar.day);

    if(caller == "init" || caller == "generate") {
      this.chartData = [0,0,0,0,0,0,0,0,0,0];

      this.generatedNumbers.forEach( num => {
        if((new Date(num.timestamp)).toDateString() == date.toDateString()) {
          this.chartData[num.number-1]++;  
        }
      });
    }
    else {
      //modify chart data if deleted data belongs in selected date
      if((new Date(this.dateOfDeletedNumber).toDateString() == date.toDateString())) {
        this.chartData[this.temp.number-1]--;
      }
    }
   
    this.barChartData[0].data = this.chartData;
    this.barChartData[0].data = this.barChartData[0].data.slice();
  }




}
