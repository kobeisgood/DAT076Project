// Component for entire app

import React from "react";

import { BrowserRouter, Route, Switch } from "react-router-dom";

import Dashboard from "../pages/Dashboard";
import Monthly from "../pages/Monthly";
import Graph from "../pages/Graph";
import Start from "../pages/Start";
import Sidebar from '../components/Sidebar';
import CreateAccount from '../pages/CreateAccount';
import Profile from '../pages/Profile';
import Logout from '../pages/Logout';

export default class CashIt extends React.Component {

    constructor(props) {
        super(props);
        this.checkSession = this.checkSession.bind(this);
        this.setMonthlyView = this.setMonthlyView.bind(this);
    }

    state = {
        loggedIn: false,
        month: new Date().getMonth() + 1,
        year: new Date().getFullYear()
    }

    componentDidMount() {
        fetch('http://localhost:8080/frontend-react/api/users/session')
            .then(response => response.json())
            .then(loggedIn => {
                this.setState({ loggedIn })
            });
    }



    checkSession() {
        fetch('http://localhost:8080/frontend-react/api/users/session')
            .then(response => response.json())
            .then(loggedIn => {
                console.log("LOGGEDIN: " + loggedIn)
                this.setState({ loggedIn })

            });
    }

    setMonthlyView(month, year) {
        this.setState({ month, year })
    }

    render() {
        return (
            <BrowserRouter>
                <div className="general-styling">
                    <div className="sidebar-container">
                        <Sidebar loggedIn={this.state.loggedIn} />
                    </div>
                    <div className="content-container">
                        <Switch>
                            <Route exact path="/frontend-react/dashboard" component={(props) => <Dashboard handleDashboardClick={this.setMonthlyView} {...props} />}></Route>
                            <Route path="/frontend-react/monthly" component={(props) => <Monthly month={this.state.month} year={this.state.year} {...props} />}></Route>
                            <Route path="/frontend-react/graph" component={Graph}></Route>
                            <Route path="/frontend-react/start" component={(props) => <Start handleLogIn={this.checkSession} {...props} />}></Route>
                            <Route path="/frontend-react/sign-up" component={CreateAccount}></Route>
                            <Route path="/frontend-react/profile" component={Profile}></Route>
                            <Route path="/frontend-react/logout" component={(props) => <Logout handleLogOut={this.checkSession} {...props} />}></Route>
                        </Switch>
                    </div>
                </div>
            </BrowserRouter>)
    };
}