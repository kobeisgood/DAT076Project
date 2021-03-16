import React from 'react';
import '../css/monthlyFloatingButtons.css'

export default class MonthlyFloatingButtons extends React.Component {

    openAddCategoryPopup() {
        var element = document.getElementById("add-category-popup");
        // element.setAttribute("category",this.state.category);
        element.style.visibility = "visible";

        // Hide floating buttons
        document.getElementById("newCategoryFloat").style.visibility = "hidden"
        document.getElementById("newTransactionFloat").style.visibility = "hidden"

    }

    openAddTransactionPopup() {
        var element = document.getElementById("add-transaction-popup");
        // element.setAttribute("category",this.state.category);
        element.style.visibility = "visible";

        // Hide floating buttons
        document.getElementById("newCategoryFloat").style.visibility = "hidden"
        document.getElementById("newTransactionFloat").style.visibility = "hidden"
    }

    render() {
        return (
            <div>
                <button id="newTransactionFloat" className="add-transaction-popup-button add-popup-button" onClick={this.openAddTransactionPopup}>
                    Add New Transaction
                </button>

                <button id="newCategoryFloat" className="add-category-popup-button add-popup-button" onClick={this.openAddCategoryPopup}>
                    Add New Category
                </button>

            </div>

        )
    }



}