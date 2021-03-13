import React from 'react';
import '../css/monthly.css';

export default class AddIncomePopup extends React.Component {


    constructor(props) {
        super(props);
        
        this.addIncome = this.addIncome.bind(this);
        this.cancelAddIncome = this.cancelAddIncome.bind(this);
    }

    state = {category:null};

    cancelAddIncome() {
        var element = document.getElementById("add-income-popup");
        element.style.visibility = "hidden";

        // Make floating buttons visible again 
        document.getElementById("newTransactionFloat").style.visibility = "visible"
        document.getElementById("newCategoryFloat").style.visibility = "visible"
    }

    // Adds transaction in a category card 
    addIncome() {

        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                description : document.getElementById("desc").value,
                amount : document.getElementById("amount").value, 
                category : document.getElementById("add-income-popup").getAttribute("category"), 
                ignore_monthly: false, // DEFAULT, NOT IMPLEMENTED FOR TRUE
                date: document.getElementById("transactionDate").value 
                })
        };

        document.getElementById("amount").value = null;
        document.getElementById("desc").value = null;
        document.getElementById("transactionDate").value = null;
        console.log(requestOptions.body);

        var parent = this.props.parent;

        fetch('http://localhost:8080/frontend-react/api/transactions', requestOptions)
            .then(response => response.json())
            .then(transaction => {
                parent.getDataFromAPI();
                
            });
            
        var element = document.getElementById("add-income-popup");
        element.style.visibility = "hidden";

        // Make floating buttons visible again 
        document.getElementById("newTransactionFloat").style.visibility = "visible"
        document.getElementById("newCategoryFloat").style.visibility = "visible"
    }

    render() {
        return(
            <div id="add-income-popup" className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-income-popup">
                        <h3>Add income to {this.state.category}</h3>
                        <div className="add-income-input-container">
                            <input id="desc" className="add-income-name-input add-income-input" placeholder="Description (e.g. Bought Groceries at Willys)"></input>
                            <br></br>
                            <input id="amount" className="add-income-amount-input add-income-input" placeholder="Amount (SEK)"></input>
                            <br></br>
                            <input id="transactionDate" type="date"></input>
                        </div>
                        <div className="confirm-buttons-container">
                            <button type="button" className="btn btn-cancel" onClick={this.cancelAddIncome}>Cancel</button>
                            <button type="button" className="btn btn-success" onClick={this.addIncome}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    };
}