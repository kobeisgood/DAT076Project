import React from 'react';
import '../css/popups.css'


export default class AddCategoryPopup extends React.Component {

    constructor(props) {
        super(props);
        
    this.cancelAddCategory = this.cancelAddCategory.bind(this)
    this.addCategory = this.addCategory.bind(this)
    }

    cancelAddCategory() {
        var element = document.getElementById("add-category-popup");
        element.style.visibility = "hidden";
    }

    addCategory() {
        // db stuff
    }

    render() {
        return(
            <div id="add-category-popup" className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-popup">
                        <h3>Add New Category</h3>
                        <div className="add-category-name-input-container">
                            <input id="name" className="add-category-name-input add-input" placeholder="Category Name (e.g. Food)"></input>
                            <br></br>
                            <input id="color" className="add-category-color-input add-input" placeholder="Red"></input>
                            <br></br>
                            <div class="radio-btn-container"> 
                                <input id="incomeType" type="radio" value="INCOME" name="type" /> INCOME
                                <input id="expenseType" type="radio" value="EXPENSE" name="type" /> EXPENSE
                                <input id="savingsType" type="radio" value="SAVINGS" name="type" /> SAVINGS
                            </div>
                        </div>
                        <div className="confirm-buttons-container">
                            <button type="button" className="btn btn-cancel" onClick={this.cancelAddCategory}>Cancel</button>
                            <button type="button" className="btn btn-success" onClick={this.addCategory}>Confirm</button>
                        </div>
                    </div>
                </div>
            </div>


        )
    }
}