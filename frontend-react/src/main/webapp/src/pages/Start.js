import React from "react";

import '../css/start.css';
import { Link } from 'react-router-dom';

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
              console.log(response);
              if(response.id != null){
                // SAVE USER ID
                alert("Login success!");

                //GO TO DASHBOARD

              }
              
          });



        console.log('The email input was:', data.get('email'))
    }

    render() {
        return (

            <div className="flex-column d-flex align-items-center justify-content-center col-md-8 offset-md-2" style={{
                padding: "10px",
                width: "25rem"
            }}>
                <form onSubmit={this.handleSubmit}>
                    {/* TODO:   connect button to home page
                            connect this start page to db
                            make this the starting page
                            connect don't have an account yet 
                            add noValidate to form and create own error messages */}
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