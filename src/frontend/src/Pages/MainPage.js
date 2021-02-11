import { React, useState } from 'react';
import  Graph from "../Components/Graph/Graph.js";
import Form from "../Components/Form/Form.js";

const MainPage = () => {
    const [geoCode, setGeoCode] = useState(null);

    const getSelected = (selected) => { 
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