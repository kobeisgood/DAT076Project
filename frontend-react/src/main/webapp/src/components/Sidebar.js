import React, {useState} from 'react';
import {Link} from "react-router-dom";


import { SidebarData } from './SidebarData';
import '../css/Sidebar.css';

function Sidebar() {
    return(
        /*
        <nav className={"nav-menu"}>
            <ul className="nav-menu-items">
                
            </ul>
        </nav>*/
        <nav className={'nav-menu'}>
            <h1 class='logo'> CashIT </h1>
            <ul className="nav-menu-items">
                 
                {SidebarData.map((item, index) => {
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

export default Sidebar; 