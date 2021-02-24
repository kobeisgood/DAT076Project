import React from 'react';
import CircleDiagram from './CircleDiagram'
import Chart from 'chart.js';
import '../main.css'


export default class DashBoardCard extends React.Component {

chartRef2 = React.createRef();

componentDidMount() {
  const doughnutChartRef = this.chartRef2.current.getContext("2d");

  // Doughnut chart code
  new Chart(doughnutChartRef, {
    type:"doughnut",
    data: {
      //Bring in data
      labels: ["Hyra", "Mat", "Shopping"],
      datasets: [
          {

              label: ["Utgifter <månad, år>"],
              backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f"],
              data: [5000, 3000, 3000],
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

  render() {

    return(
      <div class="dashboard-card box-shadow">

        <p class="dashboard-card-name"> <span>{this.props.month}</span> <span>{this.props.year}</span> </p>

        <canvas id="doughnutChart" ref={this.chartRef2}></canvas>

        <div class="row">
            <div class="col">
                <span class="dot color-income"></span>
                Income:
            </div>
            <div class="col">
              {this.props.income} kr
            </div>
        </div>

        <div class="row">
            <div class="col">
                <span class="dot color-expense"></span>
                Expenses:
            </div>
            <div class="col">
            {this.props.expense} kr
            </div>
        </div>
        
        <div class="row">
            <div class="col">
                <span class="dot color-saving"></span>
                Savings:
            </div>
            <div class="col">
              {this.props.savings} kr
            </div>
        </div>
      </div>
    );
  }




}
