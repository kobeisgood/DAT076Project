import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import CircleDiagram from "./components/CircleDiagram";
import DashboardCard from "./components/DashboardCard";
import NavigationBar from "./components/NavigationBar";


ReactDOM.render(
  <div class="row">
    <div class="col-2">
      <NavigationBar/>
    </div>

      <div class="container-fluid col-10">
          <div class="d-flex " /*style="justify-content:center"*/>
          <div class="col-sm">
            <DashboardCard/>
          </div>
          <div class="col-sm">
            <DashboardCard/>
          </div>
          <div class="col-sm">
            <DashboardCard/>
          </div>
          <div class="col-sm">
            <DashboardCard/>
          </div>
          </div>
      </div>





  </div>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
