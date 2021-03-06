import React from "react";

import DashboardCard from "../components/DashboardCard";

export default class Dashboard2 extends React.Component {
    render() {
        return (
            <div class="container-fluid">
                <div class="dashboard-container">
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                    <div class="col-3 margin-top">
                        <DashboardCard month="Januari" year="2021" income="1000000" expense="2" savings="3" />
                    </div>
                </div>

            </div>

        )
    };
}