import { React } from "react";
import { Bar } from "react-chartjs-2";

const Graph = () => {

    function toString(date) {
        const day = date[1];
        const month = date[2];
        const year = date[0];
        return day + "/" + month + "/" + year;
    }

    const district = require('../data/Madrid-Retiro.json');
    const dailyRecords = district.dailyRecords;
    console.log(dailyRecords[0].date.toString);
    return (
        <>
        <h1>Retiro</h1>
          <Bar
            width={400}
            height={400}
            data={{
                labels: dailyRecords.map(dailyRecord => toString(dailyRecord.date)),
                datasets: [{
                    label: "Cases Last Two Weeks",
                    hoverBackgroundColor: "red",
                    backgroundColor: "pink",
                    data: dailyRecords.map(dailyRecord => dailyRecord.twoWeekCases),
                    barThickness: "flex",
                }]
                  
            }}
            options={{
                maintainAspectRatio: false,
            }}
            
        />
        </>
    );

}

export default Graph