import React, {useState} from 'react';
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faHome} from '@fortawesome/free-solid-svg-icons';
import {faCalendarAlt} from '@fortawesome/free-solid-svg-icons';
import {faChartBar} from '@fortawesome/free-solid-svg-icons';
import {faCog} from '@fortawesome/free-solid-svg-icons';
import { SidebarData } from './SidebarData';
import '../css/Sidebar.css';

function Sidebar() {
    const [sidebar, setSidebar] = useState(false)

    const showSidebar = () => setSidebar(!sidebar)

    return(
        <>
        <div class="navbar">
            <Link to="#" className="menu-bars">
                <FontAwesomeIcon icon={faCog} color='white' size='sm' onClick={showSidebar}/>
            </Link>    
        </div>
        <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
            <ul className="nav-menu-items" onClick={showSidebar}>
                <li className="navbar-toggle">
                <h1 class='logo'> CashIt </h1>
                <Link>
                  <FontAwesomeIcon icon={faHome} color='white' size='sm'/>
                </Link>
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