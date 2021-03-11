import React from 'react';
import '../css/monthlyFloatingButtons.css'

export default class MonthlyFloatingButtons extends React.Component {

    openAddCategoryPopup() {
        var element = document.getElementById("add-category-popup");
         // element.setAttribute("category",this.state.category);
          element.style.visibility = "visible";
      }

      openAddTransactionPopup() {
        var element = document.getElementById("add-transaction-popup");
         // element.setAttribute("category",this.state.category);
          element.style.visibility = "visible";
      }

    render() {
        return(
            <div>
                <div class="float2" onClick={this.openAddTransactionPopup}>
                Add New Transaction
            </div>
            <div class="float1" onClick={this.openAddCategoryPopup}>
                Add New Category
            </div>

            </div>
            
        )
    }



}