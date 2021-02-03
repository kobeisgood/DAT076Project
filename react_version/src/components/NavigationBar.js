import React from 'react';
import "../css/NavigationBar.css";


export default class NavigationBar extends React.Component {
    render() {
        return (
            <div className="navigationBar textStyling">
                <a>
                    Dashboard
                </a>
                <a>
                    Monthly
                </a>
                <a>
                    Graph
                </a>
                <a>
                    Settings
                </a>
            </div>
            

        );
    }
}