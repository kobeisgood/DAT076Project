import React from "react";
import MonthlyTransaction from "./MonthlyTransaction";


export default class ExpenseTable extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            collapse: "collapse" + this.props.id,
            dataTarget: "#collapse" + this.props.id,
            accordion: "accordion" + this.props.id,
            dataParent: "#accordion" + this.props.id,
            categoryCards:null
        };
    }

    async componentDidMount() {

        const url = "http://localhost:8080/frontend-react/api/users/1/transactions";
        const response = await fetch(url);
        const data = await response.json();
        console.log(data);
        this.setState({categoryCards:data});
    
        }

    openAddIncome() {
        var element = document.getElementById("add-income-popup");
        element.style.visibility = "visible";
    }

    createCategoryCards(){
        if(!this.state.categoryCards)
          return;

        var ret = [];
        for(let card of this.state.categoryCards){
          ret.push(<MonthlyTransaction data={card}/>);
        }
        return ret;

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
                                        <div class="col-6">*categoryName* {/* should be name of category prop */} <i class="fa fa-chevron-down"></i> </div>
                                        <div class="col-3"> Amount</div>

                                    </div>

                                </div>

                                <div id={this.state.collapse} class="collapse" aria-labelledby={this.props.id} data-parent={this.state.dataParent} >
                                    <div class="card-body" id="income">
                                        {this.createCategoryCards()}

                                    </div>
                                </div>





                            </div>



                            <div class="row">
                                <div class="col-6">Income total: </div>
                                <div class="col-3">10000 kr</div>
                                <div class="col-3">0 kr</div>

                            </div>
                            <hr />
                            <div class="row">
                                <div class="col"><button type="button" class="btn btn-success" onClick={this.openAddIncome}>Add income</button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    };
}