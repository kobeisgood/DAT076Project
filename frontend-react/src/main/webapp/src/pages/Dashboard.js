import React from "react";
import DashboardCard from "../components/DashboardCard";

export default class Dashboard extends React.Component {
	state = {
		cards: null
	};

	async componentDidMount() {
		const url = "http://localhost:8080/frontend-react/api/users/dashboard";
		const response = await fetch(url);
		var data = await response.json();
		console.log(data);
		if(data.error){
			alert("Error loading data")
			data = []
		}
		this.setState({ cards: data });
	}

	createCards() {
		if (!this.state.cards)
			return;

		var ret = [];
		for (let card of this.state.cards) {
			ret.push(<div class="dashboard-card-wrapper"><DashboardCard handleDashboardCardClick={this.props.handleDashboardClick} data={card} /></div>);
			
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