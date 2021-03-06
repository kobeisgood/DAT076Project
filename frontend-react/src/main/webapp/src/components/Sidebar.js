import React, {useState} from 'react';
import {Link} from "react-router-dom";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBars} from '@fortawesome/free-solid-svg-icons';
import {faTimes} from '@fortawesome/free-solid-svg-icons';

import { SidebarData } from './SidebarData';
import '../css/Sidebar.css';

function Sidebar() {
    const [sidebar, setSidebar] = useState(true)

    const showSidebar = () => setSidebar(true)

    return(
        <>
        <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
            <ul className="nav-menu-items" onClick={showSidebar}>
                <li className="navbar-toggle">
                <h1 class='logo'> CashIt </h1>
                 
                
                </li>
                {SidebarData.map((item, index) => {
                    return (
                        <li key={index} className={item.cName}>
                            <Link to={item.path}>
                                {item.icon}
                                <span>
                                    {item.title}
                                </span>
                            </Link>
                        </li>
                    )
                })}
            </ul>
        </nav>
        </>
    )
}

export default Sidebar; 