import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter , Route, IndexRoute } from "react-router-dom";

import CircleDiagram from "./components/CircleDiagram";
import DashboardCard from "./components/DashboardCard";
import NavigationBar from "./components/NavigationBar";
import Dashboard from "./pages/Dashboard";
import Monthly from "./pages/Monthly";
import Graph from "./pages/Graph";
import Sidebar from './components/Sidebar';

ReactDOM.render(

<BrowserRouter>
  <div class="general-styling">
    <div class="row">
      <div class="col-2">
        <NavigationBar/>
      </div>
      <div class="col-10">
        <Route path="/dashboard" component={Dashboard}></Route>
        <Route path="/monthly" component={Monthly}></Route>
        <Route path="/graph" component={Graph}></Route>
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
