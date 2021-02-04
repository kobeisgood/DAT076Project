import React from "react";

import DashboardCard from "../components/DashboardCard";
import CircleDiagram from '../components/CircleDiagram'

export default class Dashboard extends React.Component{
    render() {
      return (
        <div class="container-fluid">
        <div class="d-flex ">
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

      )
    };
  }