import React from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faHome} from '@fortawesome/free-solid-svg-icons';
import {faCalendarAlt} from '@fortawesome/free-solid-svg-icons';
import {faChartBar} from '@fortawesome/free-solid-svg-icons';
import {faCog} from '@fortawesome/free-solid-svg-icons';

export const SidebarData = [
    {
        title: "Dashboard",
        path: "/frontend-react/dashboard",
        icon: <FontAwesomeIcon icon={faHome} color='white' size='lg'/>,
        cName: "nav-text"
    },
    {
        title: "Monthly",
        path: "/frontend-react/monthly",
        icon: <FontAwesomeIcon icon={faCalendarAlt} color='white' size='lg'/>,
        cName: "nav-text"
    },
    {
        title: "Graph",
        path: "/frontend-react/graph",
        icon: <FontAwesomeIcon icon={faChartBar} color='white' size='lg'/>,
        cName: "nav-text"
    },
    {
        title: "Settings",
        path: "/frontend-react/settings",
        icon: <FontAwesomeIcon icon={faCog} color='white' size='lg'/>,
        cName: "nav-text"
    },
    {
        title: "Start",
        path: "/frontend-react/start",
        icon: <FontAwesomeIcon icon={faCog} color='white' size='lg'/>,
        cName: "nav-text"
    }
]