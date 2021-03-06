import React from 'react';
import "../css/profile.css";
import CategorySetting from "../components/CategorySetting"

export default class Profile extends React.Component {

    async componentDidMount() {
        const url1 = "http://localhost:8080/frontend-react/api/users";
        const response1 = await fetch(url1);
        const data1 = await response1.json();

        const url2 = "http://localhost:8080/frontend-react/api/users/categories";
        const response2 = await fetch(url2);
        const data2 = await response2.json();

        this.setState({ categoriesData: data2, nameValue: data1.firstName, lastNameValue: data1.lastName, mailValue: data1.mail });
    }

    async updateInfo(){
        const url1 = "http://localhost:8080/frontend-react/api/users";
        const response1 = await fetch(url1);
        const data1 = await response1.json();


        this.setState({ nameValue: data1.firstName, lastNameValue: data1.lastName, mailValue: data1.mail });
    }

    constructor(props) {
        super(props);
        this.state = {
            editState: false,
            nameValue: '', // first name from db
            lastNameValue: '', // last name from db
            mailValue: '', // mail from db
            categoriesData: null,
            categories: null
        };
        this.updateInfo = this.updateInfo.bind(this);
        this.setEditState = this.setEditState.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.saveInfo = this.saveInfo.bind(this)
        this.getCategories = this.getCategories.bind(this)
    }




    setEditState() {
        this.setState({ editState: true })
        //console.log("edit state is now " + this.state.editState)
    }

    saveInfo() {

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                    mail: this.state.mailValue, 
                    firstName: this.state.nameValue,
                    lastName: this.state.lastNameValue
                })
        };

        console.log(requestOptions.body);

        fetch('http://localhost:8080/frontend-react/api/users', requestOptions)
            .then(response => response.json())
            .then(res => {
                if(res.id == null){
                    alert(res.error)
                    this.updateInfo();
                }else{
                    this.setState({ editState: false })
                }
            });


    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }


    getCategories() {
        if (this.state.categoriesData == null)
            return;

        var categories = [];
        for (let category of this.state.categoriesData) {
            categories.push(<CategorySetting data={category} />);
        }

        return categories;
    }

    render() {

        return (
            <div className="profile-page-container">
                <div className="profile-picture-container">
                    <img src="http://www.gstatic.com/tv/thumb/persons/711832/711832_v9_ba.jpg" alt="Profile"></img>
                </div>

                <div className="profile-edit-content-flexbox">
                    <div className="profile-card-container">
                        <div className="profile-title-container">
                            <h3>Account settings</h3>
                            <div className="content-splitter-line"></div>
                        </div>
                        <div className="profile-card-value-container">
                            <p className="profile-card-info-text">First name</p>
                            {this.state.editState === false ?
                                <p className="profile-card-value-text">{this.state.nameValue}</p> :
                                <input type="text" name="nameValue" value={this.state.nameValue} onChange={this.handleChange} />
                            }
                        </div>
                        <div className="profile-card-value-container">
                            <p className="profile-card-info-text">Last name</p>
                            {this.state.editState === false ?
                                <p className="profile-card-value-text">{this.state.lastNameValue}</p> :
                                <input type="text" name="lastNameValue" value={this.state.lastNameValue} onChange={this.handleChange} />
                            }
                        </div>
                        <div className="profile-card-value-container">
                            <p className="profile-card-info-text">Email address</p>
                            {this.state.editState === false ?
                                <p className="profile-card-value-text">{this.state.mailValue}</p> :
                                <input type="text" name="mailValue" value={this.state.mailValue} onChange={this.handleChange} />
                            }
                        </div>
                        <div className="edit-profile-button-container">
                            {this.state.editState === false ?
                                <button className="edit-profile-button" onClick={() => this.setEditState()}>Edit profile</button> :
                                <button className="edit-profile-button" onClick={() => this.saveInfo()}>Save</button>
                            }
                        </div>
                    </div>

                    <div className="profile-categories-container">
                        <div className="profile-title-container">
                            <h3>Categories</h3>
                            <div className="content-splitter-line"></div>
                            {this.getCategories()}
                        </div>
                    </div>
                </div>
            </div>
        )
    };
}