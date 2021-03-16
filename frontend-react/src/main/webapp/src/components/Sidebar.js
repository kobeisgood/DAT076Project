import React from 'react';
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignOutAlt, faTachometerAlt } from '@fortawesome/free-solid-svg-icons';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons';
import { faChartBar } from '@fortawesome/free-solid-svg-icons';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import { faHome } from '@fortawesome/free-solid-svg-icons';
import '../css/Sidebar.css';
import {isLoggedIn} from '../pages/Start'


export default class Sidebar extends React.Component {

    constructor(props) {
        super(props);
        this.update = this.update.bind(this);
        this.state = {
            isAuthenticated: false,
            SidebarData: []
        };
        
    }
 
 async componentDidMount(){
    this.update()
}

componentDidUpdate(prevProps, prevState){
   if (this.props.update && !prevProps.update){
       this.update();
   }
}


// Works but is illegal according to Matti
/*
 componentDidUpdate(prevProps, prevState) {
     if (prevState.SidebarData[0].title !== this.state.SideBarData[0].title)
     if (prevState.SidebarData !== this.state.SidebarData) {
         this.update();
     }
 }*/
  

async update() {

    const loggedIn = await isLoggedIn();
    this.setState({isAuthenticated: loggedIn})
    console.log(loggedIn);
    var SidebarData = this.state.SidebarData;
    if(loggedIn){ // logged in
        this.setState({ SidebarData : [
            {
                title: "Dashboard",
                path: "/frontend-react/dashboard",
                icon: <FontAwesomeIcon icon={faTachometerAlt} color='white' size='lg'/>,
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
                title: "Profile",
                path: "/frontend-react/profile",
                icon: <FontAwesomeIcon icon={faUserCircle} color='white' size='lg'/>,
                cName: "nav-text"
            },
            {
                title: "Sign out",
                path: "/frontend-react/logout",
                icon: <FontAwesomeIcon icon={faSignOutAlt} color='white' size='lg'/>,
                cName: "nav-text"
            }
        ]});
    }
    else{
        this.setState({SidebarData : [
            
            {
                title: "Start",
                path: "/frontend-react/start",
                icon: <FontAwesomeIcon icon={faHome} color='white' size='lg'/>,
                cName: "nav-text"
            }
        ]});
        
    }

    
    console.log(SidebarData);

    render() {
        return (
            <nav className={'nav-menu'}>
                <h1 className='logo'> CashIT </h1>
                <ul className="nav-menu-items">


 render() {

    return(
        <nav className={'nav-menu'}>
            <h1 className='logo'> CashIT </h1>
            <ul className="nav-menu-items">
                 
                {this.state.SidebarData.map((item, index) => {
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
