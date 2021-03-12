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
            result: null,
            descriptionValue: this.props.data.description,
            amountValue: this.props.data.amount
        };

        this.deleteTransaction = this.deleteTransaction.bind(this);
        this.editTransaction = this.editTransaction.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.saveTransaction = this.saveTransaction.bind(this);
    }

    editTransaction() {
        this.setState({editState:true});
        console.log(this.state.editState)
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
        [name]: value
         });
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

    saveTransaction() {
        this.setState({editState:false});

        const requestOptions = {
            method: 'PUT', 
            headers: { 'Content-Type': 'application/json' }, 
            body: JSON.stringify(
                {
                amount: Math.abs(this.state.amountValue),
                description: this.state.descriptionValue,
                id: this.props.data.transactionId, 
                date: this.props.data.date
                })
        };

        console.log(requestOptions.body);
          //console.log("edit state is now " + this.state.editState)

          fetch('http://localhost:8080/frontend-react/api/transactions', requestOptions)
          .then(response => response.json())
          .then(transaction => {
              console.log(transaction);
              this.props.parent.getDataFromAPI();
              
          });

    }

    render() {
        return(

            <div> 
                <div className="row">

                {this.state.editState === false ? <div className="col-6">{this.props.data.description}</div> : 
               <div className="col-6">
                   <p> <b>Edit transaction description:</b></p>
                   <input  type="text" name="descriptionValue" value={this.state.descriptionValue} onChange={this.handleChange}/> 
                </div> }

                {this.state.editState === false ? <div className="col-4"> {this.props.data.amount}</div> :
                     <div className="col-4">
                         <p> <b>Edit transaction amount:</b></p>
                         <input  type="text" name="amountValue" value={Math.abs(this.state.amountValue)} onChange={this.handleChange}/>
                     </div>
                }

                    <div className="col-2"> 
                    {this.state.editState === false ? <div className="button-container">
                            
                            <div className="edit-button" onClick={this.editTransaction}>
                                <FontAwesomeIcon icon={faEdit} color='white' size='lg'/>
                            </div>
                            <div className="delete-button" onClick={this.deleteTransaction}>
                                <FontAwesomeIcon icon={faTrashAlt} color='white' size='lg'/>
                            </div>
                        
                         </div> : 
                            <div className="save-button" onClick={this.saveTransaction}>
                                Save
                            </div>
                         }
                    
                    </div>

                </div>
                <hr />
             </div>


        ) 
    
    
    }


}