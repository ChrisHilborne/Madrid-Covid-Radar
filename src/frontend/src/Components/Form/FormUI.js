import React from 'react';
import Select from 'react-select';


const FormUI = ( {passUpSelected, options} ) => {

    const handleChange = (selected) => {
        passUpSelected(selected.value);
        }

    return (
        <>
            <div>
                <Select
                    options={options}
                    onChange={ (selected) => handleChange(selected)}
                    autoFocus
                    placeholder="Zona Basica de Salud"
                    isSearchable
                />
            </div>
        </>
    )
}

export default FormUI;