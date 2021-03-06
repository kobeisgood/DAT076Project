import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import AddIncomePopup from '../components/AddIncomePopup';
import AddCategoryPopup from '../components/AddCategoryPopup';
import AddTransactionPopup from '../components/AddTransactionPopup';
import DoughnutChartComponent from "../components/DoughnutChartComponent";
import MonthlyFloatingButtons from "../components/MonthlyFloatingButtons"

import '../css/monthly.css';

export default class Monthly extends React.Component {

  state = {
    chart: null,
    transactions: null,
    chartComponent: null,
    expenseTables: null
  };

  componentDidMount() {

    this.getDataFromAPI();

  }

  async getDataFromAPI() {

    // Fetching transaction data
    const url1 = "http://localhost:8080/frontend-react/api/users/transactions/" + this.props.year + "/" + this.props.month;
    const response1 = await fetch(url1);
    const transactionsData = await response1.json();

    // Fetching data from the dashboard
    const url2 = "http://localhost:8080/frontend-react/api/users/dashboard";
    const response2 = await fetch(url2);
    const chartData = await response2.json();


    this.setState({
      chart: chartData,
      transactions: transactionsData
    }
    );

    this.createChart();
    this.createExpenseTables();
  }

  createExpenseTables() {
    if (!this.state.transactions)
      return;

    var expenseTables = [];
    this.setState({ expenseTables });

    for (let category of this.state.transactions) {
      expenseTables.push(<ExpenseTable parent={this} title={category.name} data={category.data} />);
    }

    this.setState({ expenseTables });

  }

  createChart() {

    if (!this.state.chart)
      return;
    var chartComponent = null;
    this.setState({ chartComponent });

    for (let month of this.state.chart) {
      if (month.month === this.props.month && month.year === this.props.year)
        chartComponent = <DoughnutChartComponent title="test" data={month.data} lables={month.lables} colors={month.colors} />;
    }
    this.setState({ chartComponent });
  }

  monthToName(month) {

    var months = ["Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"];
    return months[month - 1];

  }

  render() {
    return (
      <div>
        <div className="row">
          <div className="col-1"></div>
          <h1 id="monthlyMonth">{this.monthToName(this.props.month)} {this.props.year}</h1>
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
        <MonthlyFloatingButtons />
      </div>
    )
  };

}
