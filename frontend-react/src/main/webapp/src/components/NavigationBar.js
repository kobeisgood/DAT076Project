import React from 'react';
import "../css/NavigationBar.css";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome } from '@fortawesome/free-solid-svg-icons';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { faChartBar } from '@fortawesome/free-solid-svg-icons';
import { faCog } from '@fortawesome/free-solid-svg-icons';




export default class NavigationBar extends React.Component {
  render() {
    return (
      <div class="navigationBar">
        <h1 class='logo'> CashIt </h1>
        <Link to="/dashboard">
          <FontAwesomeIcon icon={faHome} color='white' size='sm' />  Dashboard
        </Link>
        <Link to="/monthly">
          <FontAwesomeIcon icon={faCalendarAlt} color='white' size='sm' />  Monthly
        </Link>
        <Link to="/graph">
          <FontAwesomeIcon icon={faChartBar} color='white' size='sm' />  Graph
        </Link>
        <Link to="">
          <FontAwesomeIcon icon={faCog} color='white' size='sm' />  Settings
        </Link>
      </div>
    );
  }
}
