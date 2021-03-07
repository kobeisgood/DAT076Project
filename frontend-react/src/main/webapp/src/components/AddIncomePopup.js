import React from 'react';
import { ResponsiveEmbed } from 'react-bootstrap';
import '../css/monthly.css';

export default class AddIncomePopup extends React.Component {

    cancelAddIncome() {
        var element = document.getElementById("add-income-popup");
        element.style.visibility = "hidden";
    }

    addIncome() {

        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                description : document.getElementById("desc").value,
                amount : document.getElementById("amount").value, 
                type : 'INCOME', // TODO: ADD RADIO BUTTONS?
                user : 1, // TODO: GET FROM LOGIN
                category : 'Mat', // TODO: GET FROM FORM/DIV
                ignore_monthly: false, // DEFAULT, NOT IMPLEMENTED FOR TRUE
                date: null // TODO: ADD INPUT FIELD
                })
        };

        console.log(requestOptions.body);

        
        fetch('http://localhost:8080/frontend-react/api/transactions', requestOptions)
            .then(response => response.json())
            .then(transaction => {
                console.log(transaction);
                // ADD TRANSACTION TO LIST AND UPDATE CHART
                
            });
            

       


        var element = document.getElementById("add-income-popup");
        element.style.visibility = "hidden";
    }

    render() {
        return(
            <div id="add-income-popup" className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-income-popup">
                        <h3>Add income</h3>
                        <div className="add-income-input-container">
                            <input id="desc" className="add-income-name-input add-income-input" placeholder="Name (e.g. salary)"></input>
                            <br></br>
                            <input id="amount" className="add-income-amount-input add-income-input" placeholder="Amount (SEK)"></input>
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