import React from 'react';
import { Route, Redirect} from 'react-router-dom';
import { isLoggedIn } from '../pages/Start';


export const ProtectedRoute = ({component: Component, ...rest}) => {
    if(isLoggedIn) {
    return (
        <Route {...rest} render = {
            (props) => {
                    return <Component {...props} />
                
            }
        }/>

       
        )
    }
    else {
        return(
            <Redirect to="/frontend-react/start"/>
        )
    }
}