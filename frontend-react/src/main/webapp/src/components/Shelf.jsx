import React from "react";
import "./Shelf.css"
import BootstrapTable from "react-bootstrap-table-next";

export default class Shelf extends React.Component {
	constructor(props) {
		super(props);
		this.state = {users: []};

		this.columns = [{
			dataField: "id",
			text: "User id"
		},{
			dataField: "mail",
			text: "Mail"
		},{
			dataField: "budgets",
			text: "Budgets"
		}];
	}

	componentDidMount() {
		fetch("http://localhost:8080/frontend-react/api/users")
			.then(res => res.json())
			.then((data) => {
				
                                console.log(data);
				this.setState({users: data})
			}).catch(console.log);
	}

	render() {
		return (
			<BootstrapTable keyField="id" data={this.state.users} columns={this.columns} />
		);
	}
}