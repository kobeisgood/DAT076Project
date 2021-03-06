import React from 'react';
import Chart from 'chart.js';
import '../css/dashboard.css';
import '../main.css';

import { Link } from 'react-router-dom';


export default class DashBoardCard extends React.Component {

chartRef2 = React.createRef();

componentDidMount() {
  const doughnutChartRef = this.chartRef2.current.getContext("2d");

       
				
                         
                         
                         
        new Chart(doughnutChartRef, {
        type:"doughnut",
        data: {
          //Bring in data
          labels: this.props.data.labels,
          datasets: [
              {
                  label: ["Utgifter <månad, år>"],
                  data: this.props.data.data,
              }
          ]
        },

        options: {
          title: {
            display: false,
            text: 'Utgifter <månad, år> procentuellt'
          },
          legend: {
            display: false
          }
        },

        });
                                


 
}

monthToName(month){

  var months = ["Januari","Februari","Mars","April","Maj","Juni","Juli","Augusti","September","Oktober","November","December"];
  return months[month-1];

}

  render() {

    return(
      <div class="dashboard-card box-shadow">

        <p class="dashboard-card-name">{this.monthToName(this.props.data.month)} {this.props.data.year}</p>

        <canvas className="chartStyling" id="doughnutChart" ref={this.chartRef2}></canvas>

        <div class="row">
            <div class="col">
                <span class="dot color-income"></span> Income:
            </div>
            <div class="col">
              {this.props.data.summary.INCOME? this.props.data.summary.INCOME : 0} kr
            </div>
        </div>

        <div class="row">
            <div class="col">
                <span class="dot color-expense"></span> Expenses:
            </div>
            <div class="col">
            {this.props.data.summary.EXPENSE? this.props.data.summary.EXPENSE : 0} kr
            </div>
        </div>
        
        <div class="row">
            <div class="col">
                <span class="dot color-saving"></span> Savings:
            </div>
            <div class="col">
            {this.props.data.summary.SAVINGS? this.props.data.summary.SAVINGS : 0} kr
            </div>
        </div>
        <div className="buttonStyling">
          <Link className="cardButtonText cardButton" to="/monthly">
            Go to view
          </Link>
        </div>
      </div>
    );
  }




}
