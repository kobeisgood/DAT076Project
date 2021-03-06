import React from "react";


export default class ExpenseTable extends React.Component{


    constructor(props) {
        super(props);
        this.state = { collapse: "collapse"+this.props.id,
                        dataTarget: "#collapse"+this.props.id,
                        accordion: "accordion"+this.props.id,
                        dataParent: "#accordion"+this.props.id
                    };
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
                                <div class="col-6">Income <i class="fa fa-chevron-down"></i> </div>
                                <div class="col-3"> Planned</div>
                                <div class="col-3"> Received</div>
                                
                            </div>
                        </div>
                        <div id={this.state.collapse} class="collapse" aria-labelledby={this.props.id} data-parent={this.state.dataParent} >
                            <div class="card-body" id="income">
                                <div class="row">
                                    <div class="col-6">Paycheck 1</div>
                                    <div class="col-3"> 2500 kr</div>
                                    <div class="col-3"> 0 kr</div>
                                    
                                </div>
                                <hr/>
                                <div class="row">
                                    <div class="col-6">Paycheck 1</div>
                                    <div class="col-3"> 2500 kr</div>
                                    <div class="col-3"> 0 kr</div>
                                    
                                </div>
                                <hr/>
                                <div class="row">
                                    <div class="col-6">Paycheck 1</div>
                                    <div class="col-3"> 2500 kr</div>
                                    <div class="col-3"> 0 kr</div>
                                    
                                </div>
                                <hr/>
                                <div class="row">
                                    <div class="col-6">Paycheck 1</div>
                                    <div class="col-3"> 2500 kr</div>
                                    <div class="col-3"> 0 kr</div>
                                </div>
                                
                                
                            </div>
                        </div>
                        
                        
                        
                        
                        
                    </div>
                    
                    
                    
                    <div class="row">
                        <div class="col-6">Income total: </div>
                        <div class="col-3">10000 kr</div>
                        <div class="col-3">0 kr</div>
                        
                    </div>  
                    <hr/>
                    <div class="row">
                        <div class="col"><button type="button" class="btn btn-success" onclick="addIncome()">Add income</button></div>
                        
                    </div>
                </div>
            </div>
        </div>
        
        
    </div>



      )};
  }