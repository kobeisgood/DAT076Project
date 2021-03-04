import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import Chart from 'chart.js';

import '../css/monthly.css';

export default class Monthly extends React.Component {

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
          display: true,
          text: 'Utgifter <månad, år> procentuellt'
        }
      },

    });

}

render(){
    return(
    <div>
        <div class="row">
            <h1>January 2021</h1>
        </div>
        <div class="row">
            <div class="col-6">
            <ExpenseTable id="t1"/>
            <ExpenseTable id="t2"/>
            </div>
        <div class="col-6">
           <canvas id="doughnutChart" ref={this.chartRef2}></canvas>
        </div>
    </div>
    </div>
)};

}
