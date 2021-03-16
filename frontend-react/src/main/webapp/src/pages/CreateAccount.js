import React from "react";
import { Link } from 'react-router-dom';

import '../css/start.css';

export default class CreateAccount extends React.Component {

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


        if(data.get("repeatpassword") !== data.get('password')){
            alert("PASSWORD MISSMATCH!");
            return;
        }

        const requestOptions = {
            method: 'POST',
            body: JSON.stringify({
                firstName: data.get('name'),
                lastName: data.get('surname'),
                mail: data.get('email'),
                password: data.get('password')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        };

        fetch('http://localhost:8080/frontend-react/api/users/',requestOptions)
        .then(response => response.json())
          .then(response => {
              console.log(response);
              if(response.id != null){
                for (var key of data.keys()) {
                    data.delete(key)
                    }
                    alert("Create account successful!")
                this.props.history.push("/frontend-react/start")
              }
              else{
                  alert(response.error);
              }
          });
    }

    render() {
        return (

            <div className="flex-column d-flex align-items-center justify-content-center col-md-8 offset-md-2" style={{
                padding: "10px",
                width: "30rem"
            }}>

                <form onSubmit={this.handleSubmit}>
                    <h4><b>Sign up</b></h4>
                    <hr></hr>

                        <div class="row">
                            <div class="col-md-6">
                            <label htmlFor="name"><b>First name</b></label>
                            <input type="name" id="name " name="name" placeholder="Enter first name" required />
                            </div>

                            <div class="col-md-6">
                            <label htmlFor="surname"><b>Surname</b></label>
                            <input type="name" id="surname " name="surname" placeholder="Enter surname" required />
                            </div>
                        </div>

                    <label htmlFor="email"><b>Email</b></label>
                    <input type="email" id="email " name="email" placeholder="Enter email" required />

                    <label htmlFor="password"><b>Password</b></label>
                    <input type="password" name="password" placeholder="Enter password" required />

                    <label htmlFor="repeatpassword"><b>Repeat Password</b></label>
                    <input type="password" name="repeatpassword" placeholder="Repeat password" required />
                    <button type="submit">Create Account</button>
                </form >
                <Link to="/frontend-react/start">
                    Already have an account?
                </Link>
            </div >
        )
    };
}