import React from 'react';
import "../css/profile.css";

export default class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          editState: false
        };
      }

      setEditState() {
          this.setState({editState: !this.state.editState})
          console.log("edit state is now " + !this.state.editState)
      }

    render() {

        return(
            <div className="profile-page-container">
                <div className="profile-card-container">
                    <div className="profile-pic"></div>
                    <div className="profile-info">
                        <div className="info-row">
                            <span className="left-info-item"> Name:</span>
                            <span className="right-info-item"> Marcus</span> {/*should be taken from DB*/}
                        </div>
                        <div className="info-row">
                            <span className="left-info-item">Last name:</span>
                            <span className="right-info-item">Axelsson</span> {/*should be taken from DB*/}
                        </div>
                        <div className="info-row">
                            <span className="left-info-item">Email:</span>
                            <span className="right-info-item">marcusaxelsson52@gmail.com</span> {/*should be taken from DB*/}
                        </div>
                    </div>
                    <div className="edit-container" onClick={() => this.setEditState()}>
                        <p>Edit profile</p>
                    </div>
                </div>
            </div> 
        )
    };
}