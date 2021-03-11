import React from "react";
import MonthlyTransaction from "./MonthlyTransaction";


export default class ExpenseTable extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            collapse: "collapse" + this.props.title,
            dataTarget: "#collapse" + this.props.title,
            accordion: "accordion" + this.props.title,
            dataParent: "#accordion" + this.props.title,
            categoryCards:null,
            category:this.props.title
        };

        this.openAddIncome = this.openAddIncome.bind(this);
    }

    async componentDidMount() {

        this.createCategoryCards();

        }

    
        

    openAddIncome() {
        var element = document.getElementById("add-income-popup");
        element.setAttribute("category",this.state.category);
        element.style.visibility = "visible";
    }

    createCategoryCards(){
        if(!this.props.data)
          return;

        var categoryCards = [];
        var sum = 0;
        for(let transaction of this.props.data){
            sum += transaction.amount;
            categoryCards.push(<MonthlyTransaction parent={this.props.parent} data={transaction}/>);
        }

        this.setState({sum,categoryCards});



      }

    render() {
        return (
            <div class="row monthly-card no-margin box-shadow">
                <div class="card w-100">
                    <div class="card-body no-padding">
                        <div class="accordion" id={this.state.accordion}>
                            <div class="card">
                                <div class="card-header collapsed" id={this.props.id} data-toggle="collapse" data-target={this.state.dataTarget} aria-expanded="false">

                                    <div class="row">
                                        <div class="col-6"> {this.props.title} <i class="fa fa-chevron-down"></i> </div>
                                        <div class="col-3"> Amount</div>
                                        <div class="col-3"> Edit/Delete a Transaction</div>
                                    </div>

                                </div>

                                <div id={this.state.collapse} class="collapse" aria-labelledby={this.props.id} data-parent={this.state.dataParent} >
                                    <div class="card-body" id="income">
                                        {this.state.categoryCards}

                                    </div>
                                </div>





                            </div>



                            <div class="row">
                                <div class="col-6">{this.props.title} total: </div>
                                <div class="col-3">{this.state.sum} kr</div>

                            </div>
                            <hr />
                            <div class="row">
                                <div class="col"><button type="button" class="btn btn-success" onClick={this.openAddIncome}>Add new transaction</button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    };
}