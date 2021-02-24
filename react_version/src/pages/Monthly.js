import React from "react";
import ExpenseTable from "../components/ExpenseTable";
import CircleDiagram from "../components/CircleDiagram";

import '../monthly.css';

export default class Monthly extends React.Component {

render(){
    return(
    <div>
        <div class="row">
            <h1>January 2021</h1>
        </div>
        <div class="row">
            <div class="col-6">
            <ExpenseTable id="t1"/>
            <ExpenseTable id="t2"/>
            </div>
        <div class="col-6">
            <CircleDiagram money="5000" income="33" expense="34" savings="33"/>
        </div>
    </div>
    </div>
)};

}