import React from 'react';
import "../css/profile.css";
import CategorySetting from "../components/CategorySetting"

export default class Profile extends React.Component {

    async componentDidMount() {
        const url1 = "http://localhost:8080/frontend-react/api/users/1";
        const response1 = await fetch(url1);
        const data1 = await response1.json();
        console.log(data1);


        const url2 = "http://localhost:8080/frontend-react/api/users/1/categories";
        const response2 = await fetch(url2);
        const data2 = await response2.json();
        console.log("CAT123");
        console.log(data2);
        this.setState({categoriesData:data2, nameValue: data1.firstName, lastNameValue: data1.lastName, mailValue: data1.mail});

        console.log("WTF");
        console.log(this.state.categoriesData);



        }

    constructor(props) {
        super(props);
        this.state = {
          editState: false, 
          nameValue: '', // first name from db
          lastNameValue: '', // last name from db
          mailValue: '', // mail from db
          categoriesData : null,
          categories: null
        };
        this.setEditState = this.setEditState.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.saveInfo = this.saveInfo.bind(this)  
        this.getCategories = this.getCategories.bind(this)  
           
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
                mail: this.state.mailValue, // CHECK MAIL!?
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


      getCategories(){


        if(this.state.categoriesData == null)
            return;

        var categories = [];

        for(let category of this.state.categoriesData){
            categories.push(<CategorySetting data = {category}/>);
        }

       return  categories;
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
                            : <input type="mail" name="mailValue" value={this.state.mailValue} onChange={this.handleChange}/>  } {/*should be taken from DB*/} 
                        </div> 
                    </div>
                    {this.state.editState === false ? < div className="edit-container" onClick={() => this.setEditState()}>
                        <p>Edit profile</p>
                    </div> : < div className="save-container" onClick={() => this.saveInfo()}>
                        <p>Save</p>
                    </div> }
                </div>

                <div className="profile-categories-container">

                    {this.getCategories()}
                </div>

            </div> 
        )
    };
}