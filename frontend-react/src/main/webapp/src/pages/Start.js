import React from "react";

import '../css/start.css';

export default class Start extends React.Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        if (!event.target.checkValidity()) {
            this.setState({ displayErrors: true });
            return;
        }
        this.setState({ displayErrors: false });
        event.preventDefault();
        const data = new FormData(event.target);

        fetch('/api/users/login', {
            method: 'post',
            body: JSON.stringify({
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
                width: "20rem"
            }}>
                <form onSubmit={this.handleSubmit}>
                    <h4 style={{ textAlign: "center" }}><b>Log in</b></h4>

                    <label htmlFor="email"><b>Email</b></label>
                    <input type="email" id="email " name="email" placeholder="Enter email" required />

                    <label htmlFor="password"><b>Password</b></label>
                    <input type="password" name="password" placeholder="Enter password" required />
                    <button type="submit">Login</button>
                </form>
                <a href="#">Don't have an account yet?</a>
            </div>
        )
    };
}