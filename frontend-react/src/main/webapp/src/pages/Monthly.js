import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import DoughnutChartComponent from "../components/DoughnutChartComponent";

import '../css/monthly.css';

export default class Monthly extends React.Component {

  state= {
    result:null
  };

  async componentDidMount() {


    const url = "http://localhost:8080/frontend-react/api/users/1/dashboard";
    const response = await fetch(url);
    const data = await response.json();
    console.log(data);
    this.setState({result:data});

    }


    createChart(){
      if(!this.state.result)
        return;

      return <DoughnutChartComponent title="test" data={this.state.result[0].data} lables={this.state.result[0].lables} colors={this.state.result[0].colors}/>;

    }
    

render(){
    return(
    <div>
        <div class="row">
        <div class="col-1"></div>
            <h1>January 2021</h1>
        </div>
        <div class="row">
          <div class="col-1"></div>
            <div class="col-6">
            <ExpenseTable id="t1"/>
            <ExpenseTable id="t2"/>
            </div>
        <div class="col-5">
          {this.createChart()}
        </div>
    </div>
    </div>
)};

}
