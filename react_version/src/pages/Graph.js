import React from 'react';
import Chart from 'chart.js';
import '../main.css'


export default class Graph extends React.Component {

chartRef1 = React.createRef();
chartRef2 = React.createRef();

    componentDidMount() {
        const barChartRef = this.chartRef1.current.getContext("2d");
        const doughnutChartRef = this.chartRef2.current.getContext("2d");


        new Chart(barChartRef, {
            type: "horizontalBar",
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
              responsive: true,
              scales: {
                  xAxes: [{
                      ticks: {
                          beginAtZero: true,
                          max:7000 //TODO take data array, find max value, max of graph should be max value + constant
                          }
                   }]
               },
              title: {
                display: true,
                text: 'Utgifter <månad, år>'
              }
            }
        });

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

        });

      }

  render() {
    return(

    <div>
        <div class='row'>

          <div class='col-2'>
            <p>2021</p>
            <div class="dropdown">
              <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Välj år
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
              </div>
          </div>

          <div class='col-2'>
            <p>January</p>
            <div class="dropdown">
              <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Välj månad
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
              </div>
          </div>

          <div class='col-2'>
            <p>21</p>
            <div class="dropdown">
              <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Välj dag
              </button>
              <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
              </div>
          </div>
        </div>

        <div class='row top-buffer'>
          <div class='col-6' style={{position: 'relative'}}>
            <canvas id="barChart" ref={this.chartRef1}></canvas>
         </div>
         <div class='col-6' style={{position: 'relative'}}>
           <canvas id="doughnutChart" ref={this.chartRef2}></canvas>
        </div>
       </div>
    </div>
   )
 };

}
