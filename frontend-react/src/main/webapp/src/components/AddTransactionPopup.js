import React from 'react';
import '../css/popups.css';


export default class AddTransactionPopup extends React.Component {

    constructor(props) {
        super(props);
        
    this.cancelAddCategory = this.cancelAddTransaction.bind(this)
    this.addTransaction = this.addTransaction.bind(this)
    this.getCategories = this.getCategories.bind(this)
    }


    state={
        categoryOptions:null
    };

    cancelAddTransaction() {
        var element = document.getElementById("add-transaction-popup");
        element.style.visibility = "hidden";

        // Make floatings button visible again
        document.getElementById("newCategoryFloat").style.visibility = "visible"
        document.getElementById("newTransactionFloat").style.visibility = "visible"
    }

    addTransaction() {

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                category: document.getElementById("newTransactionCategoryName").value, 
                description: document.getElementById("newTransactionDesc").value, 
                amount: document.getElementById("newTransactionAmount").value, 
                date: document.getElementById("newTransactionDate").value
                })
        };

      

        fetch('http://localhost:8080/frontend-react/api/transactions', requestOptions)
            .then(response => response.json())
            .then(transaction => {

                if(transaction.transactionId != null){
                    document.getElementById("categoryName").value = null;
                    document.getElementById("newTransactionDesc").value = null;
                    document.getElementById("newTransactionAmount").value = null;
                    document.getElementById("newTransactionDate").value = null;
                    this.props.parent.getDataFromAPI();
    
                    var element = document.getElementById("add-transaction-popup");
                    element.style.visibility = "hidden";
            
            
                    // Make floating buttons visible again 
                    document.getElementById("newTransactionFloat").style.visibility = "visible"
                    document.getElementById("newCategoryFloat").style.visibility = "visible"
                }
                else{
                    alert(transaction.error)
                }
            });

       
    }


    componentDidMount(){
        this.getCategories();
    }


    getCategories(){

        fetch('http://localhost:8080/frontend-react/api/users/categories')
        .then(response => response.json())
        .then(data => {
            var categoryOptions = [];
            for(let category of data){
                categoryOptions.push(<option value ={category.categoryName}  key={category.categoryName}>{category.categoryName}</option>);
            }
            this.setState({categoryOptions})

        });
    }

    render() {
        return(
            <div id="add-transaction-popup" className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-popup">
                        <h3>Add New Transaction</h3>
                        <br></br>
                        <div className="add-transaction-input-container">

                        <select id="newTransactionCategoryName" name="categoryName">
                            <option disabled select value> -- Select a Category -- </option>
                            {this.state.categoryOptions}
                        </select>
                            <br></br>
                            <input id="newTransactionDesc" type="text" className="add-income-name-input add-income-input" placeholder="Description (e.g. Willys)"></input>
                            <br></br>
                            <input id="newTransactionAmount" type="number" className="add-income-amount-input add-income-input" placeholder="Amount (SEK)"></input>
                            <br></br>
                            <input id="newTransactionDate" type="date"></input>
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