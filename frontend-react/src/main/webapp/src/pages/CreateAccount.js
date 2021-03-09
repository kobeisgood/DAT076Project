import React from "react";

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

        fetch('/api/users/login', {
            method: 'post',
            body: JSON.stringify({
                firstname: data.get('firstname'),
                surname: data.get('surname'),
                email: data.get('email'),
                password: data.get('password')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        console.log('The email input was:', data.get('email'))
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
                <a href="/frontend-react/sign-up"></a>
            </div >
        )
    };
}