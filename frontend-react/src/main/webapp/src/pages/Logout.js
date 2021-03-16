import React from "react";


export default class Logout extends React.Component {



    componentDidMount(){

        console.log("MOUNT LOGOUT")
        fetch('http://localhost:8080/frontend-react/api/users/logout')
        .then(response => response.json())
        .then(res => {
            console.log("RESPONSE");
            this.props.handleLogOut();
            this.props.history.push("/frontend-react/start")

        });


    }
  

    render() {
        
        return(
            <div>Logging out...</div>
        )
    };
}