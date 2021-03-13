import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import AddIncomePopup from '../components/AddIncomePopup';
import AddCategoryPopup from '../components/AddCategoryPopup';
import AddTransactionPopup from '../components/AddTransactionPopup';
import DoughnutChartComponent from "../components/DoughnutChartComponent";
import MonthlyFloatingButtons from "../components/MonthlyFloatingButtons"

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

    const url1 = "http://localhost:8080/frontend-react/api/users/1/transactions/2021/3";
    const response1 = await fetch(url1);
    const transactionsData = await response1.json();

    const url2 = "http://localhost:8080/frontend-react/api/users/1/dashboard";
    const response2 = await fetch(url2);
    const chartData = await response2.json();


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
            expenseTables.push(<ExpenseTable parent={this} title={category.name} data={category.data}/>);
          }
      
          this.setState({expenseTables});

    }

    createChart(){
      
      if(!this.state.chart)
        return;
        var chartComponent = null;
        this.setState({chartComponent});

        for(let month of this.state.chart){
          if(month.month === 3 && month.year === 2021)
          chartComponent = <DoughnutChartComponent title="test" data={month.data} lables={month.lables} colors={month.colors}/>;
        }
        this.setState({chartComponent});
    }


  render() {
    return (
      <div>
        <div className="row">
          <div className="col-1"></div>
          <h1>January 2021</h1>
        </div>
        <div className="row">
          <div className="col-1"></div>
            <div className="col-6">
              {this.state.expenseTables}
            </div>
        <div className="col-5">
        {this.state.chartComponent}
        </div>
        <AddIncomePopup parent={this} />
        <AddCategoryPopup parent={this} />
        <AddTransactionPopup parent={this} />
        </div>
        <MonthlyFloatingButtons/>
        </div>
    )
  };

}
