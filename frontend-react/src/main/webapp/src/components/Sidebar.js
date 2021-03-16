import React from 'react';
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignOutAlt, faTachometerAlt } from '@fortawesome/free-solid-svg-icons';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { faChartBar } from '@fortawesome/free-solid-svg-icons';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import { faHome } from '@fortawesome/free-solid-svg-icons';
import '../css/Sidebar.css';


export default class Sidebar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loggedIn:
                [
                    {
                        title: "Dashboard",
                        path: "/frontend-react/dashboard",
                        icon: <FontAwesomeIcon icon={faTachometerAlt} color='white' size='lg' />,
                        cName: "nav-text"
                    },
                    {
                        title: "Monthly",
                        path: "/frontend-react/monthly",
                        icon: <FontAwesomeIcon icon={faCalendarAlt} color='white' size='lg' />,
                        cName: "nav-text"
                    },
                    {
                        title: "Graph",
                        path: "/frontend-react/graph",
                        icon: <FontAwesomeIcon icon={faChartBar} color='white' size='lg' />,
                        cName: "nav-text"
                    },
                    {
                        title: "Profile",
                        path: "/frontend-react/profile",
                        icon: <FontAwesomeIcon icon={faUserCircle} color='white' size='lg' />,
                        cName: "nav-text"
                    },
                    {
                        title: "Sign out",
                        path: "/frontend-react/logout",
                        icon: <FontAwesomeIcon icon={faSignOutAlt} color='white' size='lg' />,
                        cName: "nav-text"
                    }
                ],
            loggedOut: [

                {
                    title: "Start",
                    path: "/frontend-react/start",
                    icon: <FontAwesomeIcon icon={faHome} color='white' size='lg' />,
                    cName: "nav-text"
                }
            ]
        };

    }

    render() {

        var sidebarData;
        if (this.props.loggedIn) {
            sidebarData = this.state.loggedIn
        }
        else {
            sidebarData = this.state.loggedOut
        }

        return (
            <nav className={'nav-menu'}>
                <h1 className='logo'> CashIT </h1>
                <ul className="nav-menu-items">

                    {sidebarData.map((item, index) => {
                        return (
                            <li key={index} className={item.cName}>
                                <Link to={item.path}>
                                    {item.icon}
                                    <span className="nav-menu-item-title">
                                        {item.title}
                                    </span>
                                </Link>
                            </li>
                        )
                    })}
                </ul>
            </nav>

        )
    }
}
