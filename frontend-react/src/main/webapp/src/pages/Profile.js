import React from 'react';
import "../css/profile.css";

export default class Profile extends React.Component {
    render() {
        return(
            <div className="profile-page-container">
                <div className="profile-card-container">
                    <div className="profile-pic"></div>
                    <div className="profile-info">
                        <div className="info-row">
                            <span>Name:</span>
                            <span>Marcus</span>
                        </div>
                        <div className="info-row">
                            <span>Last name:</span>
                            <span>Axelsson</span>
                        </div>
                        <div className="info-row">
                            <span>Email:</span>
                            <span>marcusaxelsson52@gmail.com</span>
                        </div>
                    </div>
                    <div className="edit-container">
                        <p className="edit-button-text">Edit profile</p>
                    </div>
                </div>
            </div> 
        )
    };
}