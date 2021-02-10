import { React, useState } from 'react';
import  Graph from "./Graph.js";
import Form from "./Form.js";

const MainPage = () => {
    const [geoCode, setGeoCode] = useState(null);

    const getSelected = (selected) => {
        console.log(selected);
        setGeoCode(selected);
    } 

    if (geoCode !== null) {
        return(
            <>
                <div>
                    <Form getSelected={getSelected}/>
                    <Graph geoCode={geoCode} />     
                </div> 
            </>
        );
    } else {
        return (
            <>
                <div>
                    <Form getSelected={getSelected}/>
                </div>
            </>
        );
    }

}

export default MainPage