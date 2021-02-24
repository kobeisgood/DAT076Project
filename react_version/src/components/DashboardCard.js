import React from 'react';
import CircleDiagram from './CircleDiagram'


export default class DashBoardCard extends React.Component {

  render() {

    return(
      <div class="dashboard-card box-shadow-no-money-left">
      <p class="dashboard-card-name"> <span>{this.props.month}</span> <span>{this.props.year}</span> </p>
      <CircleDiagram money="500" income="20" expense="75" savings="5" />
      <div class="row">


          <div class="col">
              <span class="dot color-income"></span>
              Income:
          </div>
          <div class="col">
          {this.props.income} kr
          </div>
      </div>
      <div class="row">


          <div class="col">
              <span class="dot color-expense"></span>
              Expenses:
          </div>
          <div class="col">
          {this.props.expense} kr
          </div>
      </div>
      <div class="row">

          <div class="col">
              <span class="dot color-saving"></span>
              Savings:
          </div>
          <div class="col">
          {this.props.savings} kr
          </div>
      </div>
      </div>
    );
  }




}
