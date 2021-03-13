import React from "react";
import MonthlyTransaction from "./MonthlyTransaction";
import '../css/monthly.css'


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

        // Hide floating buttons 
        document.getElementById("newTransactionFloat").style.visibility = "hidden"
        document.getElementById("newCategoryFloat").style.visibility = "hidden"
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
            <div className="row monthly-card no-margin box-shadow">
                <div className="card w-100">
                    <div className="card-body no-padding">
                        <div className="accordion" id={this.state.accordion}>
                            <div className="card">
                                <div className="card-header collapsed" id={this.props.id} data-toggle="collapse" data-target={this.state.dataTarget} aria-expanded="false">

                                    <div className="row">
                                        <div className="col-4"><b>{this.props.title}</b>  <i className="fa fa-chevron-down"></i> </div>
                                        <div className="col-2"> Amount</div>
                                        <div className="col-2"> Date</div>
                                        <div className="col-2 ml-auto"> Edit/Delete a Transaction</div>
                                    </div>

                                </div>

                                <div id={this.state.collapse} className="collapse" aria-labelledby={this.props.id} data-parent={this.state.dataParent} >
                                    <div className="card-body" id="income">
                                        {this.state.categoryCards}

                                    </div>
                                </div>





                            </div>



                            <div className="row pad">
                                <div className="col-4">{this.props.title} total: </div>
                                <div className="col-2">{this.state.sum} kr</div>

                            </div>
                            <hr />
                            <div className="row">
                                <div className="col"><button type="button" className="btn btn-success" onClick={this.openAddIncome}>Add new transaction</button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    };
}