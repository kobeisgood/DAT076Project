import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import CircleDiagram from "./components/CircleDiagram";


ReactDOM.render(
  <div>
  <CircleDiagram money="500" income="10" expense="85" savings="5" /> 
  <CircleDiagram money="0" income="0" expense="95" savings="5" /> 
  <CircleDiagram money="5000" income="50" expense="25" savings="25" /> 
  <CircleDiagram money="10000" income="75" expense="15" savings="15" /> 
  </div>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
