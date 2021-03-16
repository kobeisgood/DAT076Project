// row of info for a transaction in the expense table class
// functionality for editing and deleting the transaction in the db

import React from "react";
import '../css/category-setting.css';
import { ChromePicker } from 'react-color';


export default class CategorySetting extends React.Component {

	componentDidMount() {
		if (this.state.color == null)
			this.setState({ color: this.props.data.color });
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
				<div className="category-setting-type-text">{this.props.data.type}</div>
				<div className="category-row-flexbox">
					<div className="category-setting-name-text">{this.props.data.categoryName}</div>

					<div className="category-circle-picker" style={{ backgroundColor: this.state.color }} >
						
					</div>
				</div>
			</div>
		)
	}
}