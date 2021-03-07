import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter , Route } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Monthly from "./pages/Monthly";
import Graph from "./pages/Graph";
import Sidebar from './components/Sidebar';
import Profile from './pages/Profile';

ReactDOM.render(

<BrowserRouter>
  <div class="general-styling">
    <div class="row">
      <div class="col-2">
        <Sidebar/>
      </div>
      <div class="col-10">
        <Route path="/frontend-react/dashboard" component={Dashboard}></Route>
        <Route path="/frontend-react/monthly" component={Monthly}></Route>
        <Route path="/frontend-react/graph" component={Graph}></Route>
        <Route path="/frontend-react/profile" component={Profile}></Route>
      </div>
    </div>
  </div>
</BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
