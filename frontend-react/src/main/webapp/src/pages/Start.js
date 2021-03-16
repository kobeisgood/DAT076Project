import React from "react";

import '../css/start.css';
import { Link } from 'react-router-dom';

export async function isLoggedIn() {
    const url = "http://localhost:8080/frontend-react/api/users/session";
    const response = await fetch(url);
    const data = await response.json();
    return data
}

export default class Start extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

   
 
     handleSubmit(event) {
        if (!event.target.checkValidity()) {
            return;
        }
        event.preventDefault();
        const data = new FormData(event.target);


        const requestOptions = {
            method: 'POST',
            body: JSON.stringify({
                mail: data.get('email'),
                password: data.get('password')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        };


        fetch('http://localhost:8080/frontend-react/api/users/login',requestOptions)
        .then(response => response.json())
          .then(response => {
              if(response.id != null){
                console.log(response)
                alert("Login success!");
                console.log(isLoggedIn())
                if(isLoggedIn() ) {
                    console.log(this.props)
                    console.log(this.props.sidebar.props = {update: true})
                    this.props.sidebar.props = {update: true}
                   // this.props.sidebar.update();
                    this.props.history.push("/frontend-react/dashboard")
                }

              }           
          });
    }

    render() {
        return (

            <div className="flex-column d-flex align-items-center justify-content-center col-md-8 offset-md-2" style={{
                padding: "10px",
                width: "25rem"
            }}>
                <form onSubmit={this.handleSubmit}>
                    {/* TODO:
                            make this the starting page
                    */}
                    <h4><b>Log in</b></h4>

                    <label htmlFor="email"><b>Email</b></label>
                    <input type="email" id="email " name="email" placeholder="Enter email" required />

                    <label htmlFor="password"><b>Password</b></label>
                    <input type="password" name="password" placeholder="Enter password" required />
                    <button type="submit">Login</button>
                </form>
                <Link to="/frontend-react/sign-up">
                    Don't have an account yet?
                </Link>
            </div>
        )
    };
}