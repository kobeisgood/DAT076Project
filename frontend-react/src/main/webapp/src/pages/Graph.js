import React from 'react';
import Chart from 'chart.js';
import '../css/graph.css'


export default class Graph extends React.Component {

	constructor(props) {
		super(props);

		this.fetchData = this.fetchData.bind(this);
	}

	state = {
		data: null
	};

	max(arr) {
		var max = 0;
		Math.max(arr);
		console.log(max);
		return max;
	}

	// Reference to bar chart
	chartRef1 = React.createRef();
	// Reference to doughnut chart
	chartRef2 = React.createRef();

	componentDidUpdate() {
		if (this.state.data == null)
			return;

		const barChartRef = this.chartRef1.current.getContext("2d");
		const doughnutChartRef = this.chartRef2.current.getContext("2d");

		// Horizontal bar code
		new Chart(barChartRef, {
			type: "horizontalBar",
			data: {
				//Bring in data
				labels: this.state.data.lables,
				datasets: [
					{
						label: " Kronor",
						backgroundColor: this.state.data.colors,

						data: this.state.data.data,
					}
				]
			},
			options: {
				responsive: true,
				scales: {
					xAxes: [{
						ticks: {
							beginAtZero: true,
						}
					}]
				},
				title: {
					display: false,
					text: 'Distribution av utgifter <månad, år>'
				},
				legend: {
					display: false
				}
			}
		});

		// Doughnut chart code
		new Chart(doughnutChartRef, {
			type: "doughnut",
			data: {
				//Bring in data
				labels: this.state.data.lables,
				datasets: [
					{
						label: ["Utgifter <månad, år>"],
						backgroundColor: this.state.data.colors,
						data: this.state.data.data,
					}
				]
			},
			options: {
				title: {
					display: false,
					text: 'Utgifter <månad, år> procentuellt'
				}
			},
		});
	}

	fetchData() {
		var from = document.getElementById("from").value;
		var to = document.getElementById("to").value;
		console.log()
		fetch('http://localhost:8080/frontend-react/api/users/transactions/between/' + from + '/' + to)
			.then(response => response.json())
			.then(data => {
				console.log(data);
				this.setState({ data });
				console.log(this.state.data);
			});
	}

	render() {
		return (

			<div>
				<div class="page-header">
					<h1> Choose Between Two Dates to See Your Expenses, Incomes and Savings for That Time Period </h1>
					<br></br>
					<h2> Pick The "From" and "To" Dates and Then Press "See Statistics" To Get an Overview </h2>
				</div>

				<div class="date-container">
					<div class="date-picker-container">
						<p>From: </p> <input id="from" type="date"></input>
					</div>
					<div class="date-picker-container">
						<p>To: </p> <input id="to" type="date"></input>
					</div>
				</div>

				<div class="button-row-container">
					<button class="see-data-button" onClick={this.fetchData}> See Statistics </button>
				</div>

				<div class='row'>
					<div class="col-1"></div>
					<div class='col-6 bar-top-buffer' style={{ position: 'relative' }}>
						<canvas id="barChart" ref={this.chartRef1}></canvas>
					</div>
					<div class='col-5 donut-top-buffer' style={{ position: 'relative' }}>
						<canvas id="doughnutChart" ref={this.chartRef2}></canvas>
					</div>
				</div>

			</div>
		)
	};

}
