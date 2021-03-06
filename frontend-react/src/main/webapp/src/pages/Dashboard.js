import React from "react";

import DashboardCard from "../components/DashboardCard";

export default class Dashboard extends React.Component{
        
        
        
        state= {
            cards:null
        };
        
        async componentDidMount() {


        const url = "http://localhost:8080/frontend-react/api/users/1/dashboard";
        const response = await fetch(url);
        const data = await response.json();
        console.log(data);
        this.setState({cards:data});
    
        }
        
        

        createCards(){
          if(!this.state.cards)
            return;

          var ret = [];
          for(let asd of this.state.cards){
            ret.push(<div class="col-3 margin-top"><DashboardCard data={asd}/></div>);
            ret.push(<div class="col-3 margin-top"><DashboardCard data={asd}/></div>);
          }
          return ret;

        }
    render() {
      return (
        <div class="container-fluid">
          <div class="d-flex" id="slideshow">
          <div class="dashboard-container">
            {this.createCards()}

            </div>
          </div>
        </div>

      )
    };
  }