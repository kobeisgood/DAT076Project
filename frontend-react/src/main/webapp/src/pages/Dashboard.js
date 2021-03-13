import React from "react";
import DashboardCard from "../components/DashboardCard";

export default class Dashboard extends React.Component {
	state = {
		cards: null
	};

	async componentDidMount() {
		const url = "http://localhost:8080/frontend-react/api/users/dashboard";
		const response = await fetch(url);
		const data = await response.json();
		console.log(data);
		this.setState({ cards: data });
	}

	createCards() {
		if (!this.state.cards)
			return;

		var ret = [];
		for (let card of this.state.cards) {
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard data={card} /></div>);
		}
		return ret;
	}

	render() {
		return (
			<div className="dashboard-container">
				<div className="dashboard-flexbox">
					{this.createCards()}
				</div>
			</div>
      	)
	};
}