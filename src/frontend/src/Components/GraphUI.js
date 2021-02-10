import React from 'react';
import { Bar } from "react-chartjs-2";

const GraphUI = ( { healthWard } ) => {

    const toString = (date) => {
        const day = date[1];
        const month = date[2];
        const year = date[0];
        return day + "/" + month + "/" + year;
    };

    var dailyRecords = healthWard.dailyRecords;
    var labels = dailyRecords.map(dailyRecord => toString(dailyRecord.date));
    var figures = dailyRecords.map(dailyRecord => dailyRecord.totalCases);

    const data = () => { 
        var data = {
            labels: labels,
            datasets: [{
                label: "Cases Last Two Weeks",
                hoverBackgroundColor: "red",
                backgroundColor: "pink",
                data: figures,
                barThickness: "flex",
            }]
        };
        return data;
    };

    return (
        <>
            <div>
                <Bar
                    width={400}
                    height={400}
                    data={data}
                    options={{
                        maintainAspectRatio: false,
                        responsive: true,
                    }}
                />
            </div>
        </>
    );
    
}

export default GraphUI