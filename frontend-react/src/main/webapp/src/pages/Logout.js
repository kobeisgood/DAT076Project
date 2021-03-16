import React from "react";

export default class Logout extends React.Component {

    componentDidMount() {
        fetch('http://localhost:8080/frontend-react/api/users/logout')
            .then(response => response.json())
            .then(res => {
                this.props.handleLogOut();
                this.props.history.push("/frontend-react/start")
            });
    }


    render() {
        return (
            <div>Logging out...</div>
        )
    };
}