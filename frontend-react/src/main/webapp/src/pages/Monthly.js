import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import AddIncomePopup from '../components/AddIncomePopup';
import DoughnutChartComponent from "../components/DoughnutChartComponent";

import '../css/monthly.css';

export default class Monthly extends React.Component {

  state= {
    chart:null,
    transactions: null
  };

  async componentDidMount() {


    const url1 = "http://localhost:8080/frontend-react/api/users/1/transactions/2021/3";
    const response1 = await fetch(url1);
    const transactionsData = await response1.json();

    const url2 = "http://localhost:8080/frontend-react/api/users/1/dashboard";
    const response2 = await fetch(url2);
    const chartData = await response2.json();
    console.log(chartData);


    this.setState({
      chart:chartData,
    transactions:transactionsData});

    }

    createExpenseTables(){
      if(!this.state.transactions)
        return;

      var ret = [];
          for(let category of this.state.transactions){
            console.log("ADD EXPENSE TABLE");
            ret.push(<ExpenseTable title={category.name} data={category.data}/>);
          }
      
          return ret;

    }

    createChart(){
      if(!this.state.chart)
        return;
        for(let month of this.state.chart){
          if(month.month === 3 && month.year === 2021)
          console.log(month);
            return <DoughnutChartComponent title="test" data={month.data} lables={month.lables} colors={month.colors}/>;
        }
    }

  render() {
    return (
      <div>
        <div class="row">
          <div class="col-1"></div>
          <h1>January 2021</h1>
        </div>
        <div class="row">
          <div class="col-1"></div>
            <div class="col-6">{this.createExpenseTables()}
            </div>
        <div class="col-5">
        {this.createChart()}
        </div>
        <AddIncomePopup  />
        </div>
        </div>
    )
  };

}
