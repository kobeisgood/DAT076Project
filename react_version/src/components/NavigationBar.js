import React from 'react';
import "../css/NavigationBar.css";
import {Link} from "react-router-dom";

export default class NavigationBar extends React.Component {
    render() {
        return (
            <div className="navigationBar textStyling">
                <Link to="/dashboard">
                    Dashboard
                </Link>
                <Link to="/monthly">
                    Monthly
                </Link>
                <Link to="">
                    Graph
                </Link>
                <Link to="">
                    Settings
                </Link>
            </div>
            

        );
    }
}