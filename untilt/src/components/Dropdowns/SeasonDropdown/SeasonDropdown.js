import React from 'react';

function SeasonDropdown(props) {
    return(
        <div>
            Champion:
            <select onChange={props.handleChange}>
                {props.values.map(d => <option key={d}>
                    {d}</option>)}
            </select>
        </div>
    )

}

export default SeasonDropdown;