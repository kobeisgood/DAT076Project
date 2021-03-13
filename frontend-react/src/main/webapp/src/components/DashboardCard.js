import React from 'react';
import Chart from 'chart.js';
import '../main.css';
import '../css/dashboard.css';

import { Link } from 'react-router-dom';


export default class DashBoardCard extends React.Component {



	constructor(props) {
		super();
		this.props = props;
		if (!this.props.data.summary.INCOME)
			this.props.data.summary.INCOME = Math.floor(Math.random() * 2500);  ///////////////////// TESTING! Should be 0!
		if (!this.props.data.summary.EXPENSE)
			this.props.data.summary.EXPENSE = Math.floor(Math.random() * 2500); ///////////////////// TESTING! Should be 0!
		if (!this.props.data.summary.SAVINGS)
			this.props.data.summary.SAVINGS = Math.floor(Math.random() * 2500); ///////////////////// TESTING! Should be 0!
	}

	chartRef2 = React.createRef();

	componentDidMount() {
		const doughnutChartRef = this.chartRef2.current.getContext("2d");



		new Chart(doughnutChartRef, {
			type: "doughnut",
			data: {
				//Bring in data
				labels: ["INCOME", "EXPENSE", "SAVINGS"],
				datasets: [
					{
						label: ["Utgifter <månad, år>"],
						backgroundColor: ["#5cff80", "#ff7777", "#8f85ff"],
						data: [this.props.data.summary.INCOME, this.props.data.summary.EXPENSE, this.props.data.summary.SAVINGS],
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

	sum() {
		return this.props.data.summary.INCOME - this.props.data.summary.EXPENSE + this.props.data.summary.SAVINGS;
	}

	monthToName(month) {

		var months = ["Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"];
		return months[month - 1];

	}



	render() {

		return (
			<div className="dashboard-card-container box-shadow">
				<div className="dashboard-card-flexbox">
					<p class="dashboard-card-name">{this.monthToName(this.props.data.month)} {this.props.data.year}</p>
					<canvas className="chartStyling" id="doughnutChart" ref={this.chartRef2}></canvas>
					<p class={this.sum() > 0 ? "color-money-left sum-text" : "color-no-money-left sum-text"}>{this.sum()}</p>
					<Link className="dashboard-card-button hover-transition" to="/frontend-react/monthly">Go to view</Link>
				</div>
			</div>
		);
	}




}