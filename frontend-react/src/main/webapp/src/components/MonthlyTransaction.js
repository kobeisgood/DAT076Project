// row of info for a transaction in the expense table class
// functionality for editing and deleting the transaction in the db

import React from "react";
import '../css/monthlyTransaction.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faEdit} from '@fortawesome/free-solid-svg-icons';
import {faTrashAlt} from '@fortawesome/free-solid-svg-icons';


export default class MonthlyTransaction extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            editState: false,
            result: null
        };

        this.deleteTransaction = this.deleteTransaction.bind(this);
    }

  

    deleteTransaction() {
        
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        };

        console.log("BODY" + requestOptions.body);
            console.log(this.props.data.transactionId);
          fetch('http://localhost:8080/frontend-react/api/transactions/'+this.props.data.transactionId, requestOptions)
          .then(response => response.json())
          .then(transaction => {
              this.props.parent.getDataFromAPI();
              
          });
    }

    render() {
        return(

            <div> 
                <div class="row">

                    <div class="col-6">{this.props.data.description}</div>
                    <div class="col-4"> {this.props.data.amount}</div>
                    <div class="col-2"> 
                        <div class="edit-button">
                            <FontAwesomeIcon icon={faEdit} color='white' size='sm'/>
                        </div>
                        <div class="delete-button" onClick={this.deleteTransaction}>
                            <FontAwesomeIcon icon={faTrashAlt} color='white' size='sm'/>
                        </div>
                    
                    </div>

                </div>
                <hr />
             </div>


        ) 
    
    
    }


}