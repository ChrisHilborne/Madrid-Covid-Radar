import React from 'react';
import { Bar } from "react-chartjs-2";
import moment from 'moment';

const GraphUI = ( { healthWard } ) => {

    const toString = (date) => {
        const year = date[0];
        const month = date[1].length === 1 ? "0" + date[1] : date[1];
        const day = date[2].length === 1 ? "0" + date[2] : date[2];
        
        return year + "-" + month + "-" + day;
    };

    var dailyRecords = healthWard.dailyRecords;
    console.log(dailyRecords);

    const labels = () => { 
            const dates = dailyRecords.map(dailyRecord => { 
                return moment(toString(dailyRecord.date));
            });
            console.log(dates);
            return dates;
    };

    const figures = () => { 
        return dailyRecords.map(dailyRecord => dailyRecord.twoWeekRate);
    };


    var data = {
        labels: labels(),
        datasets: [{
            label: "Casos por 10,000",
            backgroundColor: "#444fe3",
            data: figures(),
            barThickness: "flex",
        }]
    };

    
    
    var options = {
        maintainAspectRatio: false,
        responsive: true,
        scales: {
            xAxes: [{
                type: "time",
                time: {
                    unit: 'day',
                    round: 'day',
                    displayFormats: {
                        day: 'MMM D'
                    }
                },
            gridLines: {
                color: "rgba(0, 0, 0, 0)",
                }
            }],
            yAxes: [{
                gridLines: {
                    color: "rgba(0, 0, 0, 0)",
                }   
            }]
        }
    };
    



    return (
        <>
            <div>
                <Bar
                    width={400}
                    height={400}
                    data={data}
                    options={options}
                />
            </div>
        </>
    );
    
}

export default GraphUI