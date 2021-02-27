import React from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faHome} from '@fortawesome/free-solid-svg-icons';
import {faCalendarAlt} from '@fortawesome/free-solid-svg-icons';
import {faChartBar} from '@fortawesome/free-solid-svg-icons';
import {faCog} from '@fortawesome/free-solid-svg-icons';

export const SidebarData = [
    {
        title: "Dashboard",
        path: "/dashboard",
        icon: <FontAwesomeIcon icon={faHome} color='white' size='sm'/>,
        cName: "nav-text"
    },
    {
        title: "Monthly",
        path: "/monthly",
        icon: <FontAwesomeIcon icon={faCalendarAlt} color='white' size='sm'/>,
        cName: "nav-text"
    },
    {
        title: "Graph",
        path: "/graph",
        icon: <FontAwesomeIcon icon={faChartBar} color='white' size='sm'/>,
        cName: "nav-text"
    },
    {
        title: "Settings",
        path: "/settings",
        icon: <FontAwesomeIcon icon={faCog} color='white' size='sm'/>,
        cName: "nav-text"
    }
]