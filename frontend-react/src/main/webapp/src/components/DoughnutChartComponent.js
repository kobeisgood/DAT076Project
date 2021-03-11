import React from 'react';
import Chart from 'chart.js';
import '../css/dashboard.css';
import '../main.css';



export default class DashBoardCard extends React.Component {

  

  // title, labels[], data[], colors[]
constructor(props){
  super(props);
  this.props = props;
  this.createChart = this.createChart.bind(this);
}
  
chartRef = React.createRef();


createChart(){
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
            display: true,
            text: 'Utgifer'
          },
          legend: {
            display: true
          }
        },

        });

}
componentDidMount() {

  this.createChart();
  
}

componentDidUpdate() {

  this.createChart();
  
}

  render() {

    return(
 
        <canvas className="chartStyling" id="doughnutChart" ref={this.chartRef}></canvas>

    );
  }




}
