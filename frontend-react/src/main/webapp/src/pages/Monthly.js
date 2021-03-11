import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import AddIncomePopup from '../components/AddIncomePopup';
import DoughnutChartComponent from "../components/DoughnutChartComponent";

import '../css/monthly.css';

export default class Monthly extends React.Component {

  state= {
    chart:null,
    transactions: null,
    chartComponent:null,
    expenseTables:null
  };

  componentDidMount(){

    this.getDataFromAPI();

  }

  async getDataFromAPI() {

console.log("GET DATA API");
    const url1 = "http://localhost:8080/frontend-react/api/users/1/transactions/2021/3";
    const response1 = await fetch(url1);
    const transactionsData = await response1.json();

    const url2 = "http://localhost:8080/frontend-react/api/users/1/dashboard";
    const response2 = await fetch(url2);
    const chartData = await response2.json();
    console.log(chartData);


    this.setState({
      chart:chartData,
      transactions:transactionsData}
      );

      this.createChart();
      this.createExpenseTables();
    }

    createExpenseTables(){
      if(!this.state.transactions)
        return;

      var expenseTables = [];
      this.setState({expenseTables});

      for(let category of this.state.transactions){
            console.log("ADD EXPENSE TABLE");
            expenseTables.push(<ExpenseTable parent={this} title={category.name} data={category.data}/>);
          }
      
          console.log("SETTING EXPENSE COMPONENTS");
          console.log(expenseTables);
          this.setState({expenseTables});

    }

    createChart(){
      
      if(!this.state.chart)
        return;
        var chartComponent = null;
        this.setState({chartComponent});

        for(let month of this.state.chart){
          if(month.month === 3 && month.year === 2021)
          console.log(month);
          chartComponent = <DoughnutChartComponent title="test" data={month.data} lables={month.lables} colors={month.colors}/>;
        }
        console.log("SETTING CHART COMPONENT");
        console.log(chartComponent);
        this.setState({chartComponent});
        console.log(this.state.chartComponent);
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
            <div class="col-6">{this.state.expenseTables}
            </div>
        <div class="col-5">
        {this.state.chartComponent}
        </div>
        <AddIncomePopup parent={this} />
        </div>
        </div>
    )
  };

}
