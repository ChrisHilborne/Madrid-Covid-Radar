import { React, useState, useEffect } from "react";
import GraphUI from './GraphUI.js';
import axios from 'axios';

const Graph = (geoCode) => {
    const [healthWard, setHealthWard] = useState(null);

    const url = 'http://covidradarmadrid-env.eba-wbgad2ub.eu-south-1.elasticbeanstalk.com/api/geocode/';

    useEffect(() => {
        const data = (geoCode) => {
        axios.get(url.concat(geoCode.geoCode))
            .then( response => {
                setHealthWard(response.data);
            })
            .catch( error => console.error(`Error: ${error}`))
        };
        data(geoCode);
    }, [geoCode]);

    if (healthWard !== null) {
        return (
            <GraphUI healthWard={healthWard} />
        );
    } else {
        return (
            <div></div>
        );
    }
}

export default Graph