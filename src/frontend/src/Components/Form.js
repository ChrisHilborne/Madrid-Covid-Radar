import axios from 'axios';
import { React, useState, useEffect } from 'react';
import Select from 'react-select';



const Form = () => {
    const [options, setOptions] = useState({});
    const [selectedOption, setSelectedOption] = useState('');

    useEffect(() => {
        getOptions();
    }, []);

    const urlOptions = 'http://covidradarmadrid-env.eba-wbgad2ub.eu-south-1.elasticbeanstalk.com/api/names+geocodes';

    const getOptions = () => {
        axios.get(urlOptions)
        .then((response) => {
            const allOptions = response.data;
            console.log(allOptions);
            setOptions(allOptions);
        })
        .catch(error => console.error(`Error: ${error}`));
    }

    return(
        <div>
            <h1>HELLO!</h1>
        </div>
    );
    
}

export default Form

