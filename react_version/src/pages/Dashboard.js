import React from "react";

import DashboardCard from "../components/DashboardCard";
import CircleDiagram from '../components/CircleDiagram'

export default class Dashboard extends React.Component{
    render() {
      return (
        <div class="container-fluid">
          <div class="d-flex">
            <div class="col-sm">
              <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3"/>
            </div>
            <div class="col-sm">
              <DashboardCard month="February" year="2021" income="4" expense="5" savings="6"/>
            </div>
            <div class="col-sm">
              <DashboardCard month="March" year="2021" income="7" expense="8" savings="9"/>
            </div>
            <div class="col-sm">
              <DashboardCard month="April" year="2021" income="10" expense="11" savings="12"/>
            </div>
          </div>
        </div>

      )
    };
  }