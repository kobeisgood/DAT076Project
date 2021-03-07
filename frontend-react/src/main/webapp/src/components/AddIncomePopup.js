import React from 'react';
import '../css/monthly.css';

export default class AddIncomePopup extends React.Component {
    cancelAddIncome() {
        var element = document.getElementById("add-income-popup");
        element.style.visibility = "hidden";
    }

    addIncome() {
        // do more cool stuff
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
                            <input className="add-income-name-input add-income-input" placeholder="Name (e.g. salary)"></input>
                            <br></br>
                            <input className="add-income-amount-input add-income-input" placeholder="Amount (SEK)"></input>
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