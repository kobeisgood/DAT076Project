// row of info for a transaction in the expense table class
// functionality for editing and deleting the transaction in the db

import React from "react";
import '../css/monthlyTransaction.css';
import { ChromePicker } from 'react-color'


export default class MonthlyTransaction extends React.Component {

  

    componentDidMount(){
        if(this.state.color == null)
            this.setState({color:this.props.data.color});
        console.log("HMM")
    }
    state = {
        displayColorPicker: false,
        color: null
      };
    
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
            <div className="category-setting-container">
                                {this.props.data.categoryName}{this.props.data.type}

              <div className="category-circle-picker" style={{backgroundColor: this.state.color}} onClick={ this.handleClick }></div>
              { this.state.displayColorPicker ? <div style={ popover }>
                <div style={ cover } onClick={ this.handleClose }/>
                <ChromePicker color={this.state.color} onChange={this.handleChange}  />
              </div> : null }
              
             
            </div>
          )
        

            


         
    
    
    }


}