import React, {useState} from 'react';
import {Link} from "react-router-dom";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBars} from '@fortawesome/free-solid-svg-icons';
import {faTimes} from '@fortawesome/free-solid-svg-icons';

import { SidebarData } from './SidebarData';
import '../css/Sidebar.css';

function Sidebar() {
    const [sidebar, setSidebar] = useState(false)

    const showSidebar = () => setSidebar(!sidebar)

    return(
        <>
        <div class="navbar">
            <Link to="#" className="menu-bars">
                <FontAwesomeIcon icon={faBars} color='black' size='lg' onClick={showSidebar} className="change-hamburger-color"/>
            </Link>    
        </div>
        <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
            <ul className="nav-menu-items" onClick={showSidebar}>
                <li className="navbar-toggle">
                <h1 class='logo'> CashIt <span> <Link>
                  <FontAwesomeIcon icon={faTimes} color='white' size='sm' className="change-close-color"/>
                </Link>  </span></h1>
                 
                
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