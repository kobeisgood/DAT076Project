import React from 'react';
import "../css/profile.css";

export default class Profile extends React.Component {

    async componentDidMount() {
        const url = "http://localhost:8080/frontend-react/api/users/1";
        const response = await fetch(url);
        const data = await response.json();
        console.log(data);
        this.setState({nameValue: data.firstName, lastNameValue: data.lastName, mailValue: data.mail});
        }

    constructor(props) {
        super(props);
        this.state = {
          editState: false, 
          nameValue: '', // first name from db
          lastNameValue: '', // last name from db
          mailValue: '' // mail from db
        };
        this.setEditState = this.setEditState.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.saveInfo = this.saveInfo.bind(this)     
    }

      setEditState() {
          this.setState({editState: true})
          //console.log("edit state is now " + this.state.editState)
      }

      saveInfo() {
          this.setState({editState: false})
          
          const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                mail: this.state.mailValue,
                firstName: this.state.nameValue, 
                lastName: this.state.lastNameValue,
                password: 'qwe123',
                id: 1
                })
        };

          console.log(requestOptions.body);
          //console.log("edit state is now " + this.state.editState)

          fetch('http://localhost:8080/frontend-react/api/users', requestOptions)
          .then(response => response.json())
          .then(transaction => {
              console.log(transaction);
              // UPDATE USER INFO
              
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

    render() {

        return(
            <div className="profile-page-container">
                <div className="profile-card-container">
                    <div className="profile-pic"></div>
                    <div className="profile-info">
                        <div className="info-row">

                            {this.state.editState === false ? <span className="left-info-item"> Name:</span> : <span className="left-info-item"> New name: </span> }

                            {this.state.editState === false ? <span className="right-info-item"> {this.state.nameValue} </span> 
                            : <input type="text" name="nameValue" value={this.state.nameValue} onChange={this.handleChange}/> } 

                        </div>
                        <div className="info-row">
                            {this.state.editState === false ? <span className="left-info-item">Last name:</span> : <span className="left-info-item"> New last name: </span>}

                            {this.state.editState === false ? <span className="right-info-item">{this.state.lastNameValue}</span> 
                            : <input type="text" name="lastNameValue" value={this.state.lastNameValue} onChange={this.handleChange}/> }   {/*should be taken from DB*/}
                        </div>
                        <div className="info-row">
                            {this.state.editState === false ? <span className="left-info-item">Email:</span> : <span className="left-info-item"> New mail: </span> }

                            {this.state.editState === false ? <span className="right-info-item">{this.state.mailValue}</span> 
                            : <input type="text" name="mailValue" value={this.state.mailValue} onChange={this.handleChange}/>  } {/*should be taken from DB*/} 
                        </div> 
                    </div>
                    {this.state.editState === false ? < div className="edit-container" onClick={() => this.setEditState()}>
                        <p>Edit profile</p>
                    </div> : < div className="save-container" onClick={() => this.saveInfo()}>
                        <p>Save</p>
                    </div> }
                </div>
            </div> 
        )
    };
}