import React from 'react';
import '../css/popups.css'


export default class AddTransactionPopup extends React.Component {

    constructor(props) {
        super(props);
        
    this.cancelAddCategory = this.cancelAddTransaction.bind(this)
    this.addCategory = this.addTransaction.bind(this)
    }

    cancelAddTransaction() {
        var element = document.getElementById("add-transaction-popup");
        element.style.visibility = "hidden";
    }

    addTransaction() {
        // db stuff
    }

    render() {
        return(
            <div id="add-transaction-popup" className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-popup">
                        <h3>Add New Transaction</h3>
                        <div className="add-transaction-input-container">
                            <input id="categoryName" className="add-category-name-input add-input" placeholder="Category Name (e.g. Food)"></input>
                            <br></br>
                            <input id="desc" className="add-income-name-input add-income-input" placeholder="Description (e.g. Willys)"></input>
                            <br></br>
                            <input id="amount" className="add-income-amount-input add-income-input" placeholder="Amount (SEK)"></input>
                            <br></br>
                            <input id="date" type="date"></input>
                        </div>
                        <div className="confirm-buttons-container">
                            <button type="button" className="btn btn-cancel" onClick={this.cancelAddTransaction}>Cancel</button>
                            <button type="button" className="btn btn-success" onClick={this.addTransaction}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>


        )
    }
}