import React from 'react';
import Chart from 'chart.js';
import '../css/dashboard.css';
import '../main.css';

import { Link } from 'react-router-dom';


export default class DashBoardCard extends React.Component {

chartRef2 = React.createRef();

componentDidMount() {
  const doughnutChartRef = this.chartRef2.current.getContext("2d");

      fetch("http://localhost:8080/frontend-react/api/users/1/transactions/")
			.then(res => res.json())
			.then((json) => {
				
                                console.log(json);
				
                                
                                
			}).catch(console.log);

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
            data: [100, 222, 2222],
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

        <p class="dashboard-card-name">{this.props.month} {this.props.year}</p>

        <canvas className="chartStyling" id="doughnutChart" ref={this.chartRef2}></canvas>

        <div class="row">
            <div class="col">
                <span class="dot color-income"></span> Income:
            </div>
            <div class="col">
              {this.props.income} kr
            </div>
        </div>

        <div class="row">
            <div class="col">
                <span class="dot color-expense"></span> Expenses:
            </div>
            <div class="col">
            {this.props.expense} kr
            </div>
        </div>
        
        <div class="row">
            <div class="col">
                <span class="dot color-saving"></span> Savings:
            </div>
            <div class="col">
              {this.props.savings} kr
            </div>
        </div>
        <div className="buttonStyling">
          <Link className="cardButtonText cardButton" to="/frontend-react/monthly">
            Go to view
          </Link>
        </div>
      </div>
    );
  }




}
