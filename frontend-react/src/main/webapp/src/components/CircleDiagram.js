import React from "react";


export default class CircleDiagram extends React.Component {

    render(){
        var background;
        if(this.props.money > 0){
            background = "#C1FFCF";
        }
        else{
            background = "#FFA5A5";
        }

        

        const circleStyles = [
            {
                strokeDasharray: this.props.income + " 100",
                strokeDashoffset: "-0"
            },
            {
                strokeDasharray: this.props.expense + " 100",
                strokeDashoffset: -this.props.income
            },
            {
                strokeDasharray: this.props.savings + " 100",
                strokeDashoffset: -this.props.income-this.props.expense
            },
            {
                fill: background,
                stroke : background
            }
        ];


        return (
        <div class="circle_diagram">
                                
        <div class="left-to-spend"> <p>Left to spend:</p><p>{this.props.money} kr</p></div>
        
        <svg viewBox="0 0 64 63" class="pie">
        <circle r="25%" cx="50%" cy="50%" class="color-income" style={circleStyles[0]}>
        </circle>
        <circle r="25%" cx="50%" cy="50%" class="color-expense" style={circleStyles[1]}>
        </circle>
        <circle r="25%" cx="50%" cy="50%" class="color-saving" style={circleStyles[2]}>
        </circle>
        <circle r="20%" cx="50%" cy="50%" style={circleStyles[3]} >
        </circle>
        </svg>
        
                                    
    </div>);
    }
}