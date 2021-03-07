import React from 'react';
import Chart from 'chart.js';
import '../css/dashboard.css';
import '../main.css';



export default class DashBoardCard extends React.Component {

  

  // title, labels[], data[], colors[]
constructor(props){
  super();
  this.props = props;
}
  
chartRef = React.createRef();

componentDidMount() {
  const doughnutChartRef = this.chartRef.current.getContext("2d");

 
   
        new Chart(doughnutChartRef, {
        type:"doughnut",
        data: {
          //Bring in data
          labels: this.props.lables,
          datasets: [
              {
                label: [this.props.title],
                backgroundColor: this.props.colors,
                data: this.props.data,
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
 
        <canvas className="chartStyling" id="doughnutChart" ref={this.chartRef}></canvas>

    );
  }




}
