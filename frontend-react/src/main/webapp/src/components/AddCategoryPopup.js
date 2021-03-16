import React from 'react';
import '../css/popups.css'
import { ChromePicker } from 'react-color'


export default class AddCategoryPopup extends React.Component {

    constructor(props) {
        super(props);

        this.cancelAddCategory = this.cancelAddCategory.bind(this)
        this.addCategory = this.addCategory.bind(this)
        this.onChangeType = this.onChangeType.bind(this)
    }

    state = {
        selectedType: null,
        color: "#000000"
    }

    cancelAddCategory() {
        var element = document.getElementById("add-category-popup");
        element.style.visibility = "hidden";

        // Make floatings button visible again
        document.getElementById("newCategoryFloat").style.visibility = "visible"
        document.getElementById("newTransactionFloat").style.visibility = "visible"
    }

    addCategory() {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                    categoryName: document.getElementById("categoryName").value,
                    color: this.state.color,
                    type: this.state.selectedType
                })
        };

        fetch('http://localhost:8080/frontend-react/api/category', requestOptions)
            .then(response => response.json())
            .then(transaction => {
                // parent.getDataFromAPI();

            });





        var element = document.getElementById("add-category-popup");
        element.style.visibility = "hidden";

        // Make floating buttons visible again 
        document.getElementById("newTransactionFloat").style.visibility = "visible"
        document.getElementById("newCategoryFloat").style.visibility = "visible"


    }

    onChangeType(event) {
        var selectedType = event.target.value;
        this.setState({ selectedType });
    }


    handleClick = () => {
        this.setState({ displayColorPicker: !this.state.displayColorPicker })
        console.log(this.state.color)
    };

    handleClose = () => {
        this.setState({ displayColorPicker: false })
    };

    handleChange = (color) => {
        this.setState({ color: color.hex })
    };

    render() {

        const popover = {
            position: 'absolute',
            zIndex: '2',
        }
        const cover = {
            position: 'fixed',
            top: '0px',
            right: '0px',
            bottom: '0px',
            left: '0px',
        }

        return (
            <div id="add-category-popup" className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-popup">
                        <h3>Add New Category</h3>
                        <br></br>
                        <div className="add-category-name-input-container">
                            <input id="categoryName" className="add-category-name-input add-input" placeholder="Category Name (e.g. Food)"></input>
                            <br></br>

                            <div className="category-circle-picker" style={{ backgroundColor: this.state.color }} onClick={this.handleClick}></div>
                            {this.state.displayColorPicker ? <div style={popover}>
                                <div style={cover} onClick={this.handleClose} />
                                <ChromePicker color={this.state.color} onChange={this.handleChange} />
                            </div> : null}

                            <br></br>
                            <div className="radio-btn-container" onChange={this.onChangeType}>
                                <input id="incomeType" type="radio" value="INCOME" name="categoryType" /> INCOME
                                <input id="expenseType" type="radio" value="EXPENSE" name="categoryType" /> EXPENSE
                                <input id="savingsType" type="radio" value="SAVINGS" name="categoryType" /> SAVINGS
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