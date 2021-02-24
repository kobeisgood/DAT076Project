import React from 'react';
import Chart from 'chart.js';
import '../css/graph.css'


export default class Graph extends React.Component {

chartRef1 = React.createRef();
chartRef2 = React.createRef();

    componentDidMount() {
        const barChartRef = this.chartRef1.current.getContext("2d");
        const doughnutChartRef = this.chartRef2.current.getContext("2d");

        // Horizontal bar code
        new Chart(barChartRef, {
            type: "horizontalBar",
            data: {
                //Bring in data
                labels: ["Hyra", "Mat", "Shopping"],
                datasets: [
                    {

                        label: " Kronor",
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
                text: 'Distribution av utgifter <månad, år>'
              },
              legend: {
                display: false
              }
            }
        });

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

  render() {
    return(

    <div>
        <div class='row'>

          <div class='col-1'>
            <p class="text-sorting" >2021</p>
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

          <div class='col-1'>
            <p class="text-sorting" >January</p>
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

          <div class='col-1'>
            <p class="text-sorting" > 21</p>
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

        <div class='row'>
          <div class='col-6 bar-top-buffer' style={{position: 'relative'}}>
            <canvas id="barChart" ref={this.chartRef1}></canvas>
         </div>
         <div class='col-6 donut-top-buffer' style={{position: 'relative'}}>
           <canvas id="doughnutChart" ref={this.chartRef2}></canvas>
        </div>
       </div>
    </div>
   )
 };

}
