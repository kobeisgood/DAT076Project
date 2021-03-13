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
        return(
            <div>
                <div id="newTransactionFloat" className="float2" onClick={this.openAddTransactionPopup}>
                Add New Transaction
            </div>
            <div id="newCategoryFloat" className="float1" onClick={this.openAddCategoryPopup}>
                Add New Category
            </div>

            </div>
            
        )
    }



}