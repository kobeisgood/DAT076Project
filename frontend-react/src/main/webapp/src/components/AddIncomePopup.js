import { faFileAudio } from '@fortawesome/free-solid-svg-icons';
import React from 'react';
import '../css/monthly.css';

export default class AddIncomePopup extends React.Component {
    render() {
        return(
            <div className="full-page-container">
                <div className="flexbox-container">
                    <div className="add-income-popup">
                        <h3>Add income</h3>
                        <div className="add-income-input-container">
                            <input className="add-income-name-input" placeholder="Name (e.g: salary)"></input>
                            <br></br>
                            <input className="add-income-amount-input" placeholder="Amount"></input>
                        </div>
                    </div>
                </div>
            </div>
        )
    };
}