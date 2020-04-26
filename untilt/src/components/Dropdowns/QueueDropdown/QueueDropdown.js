import React from 'react';

function QueueDropdown(props) {
    return(
        <div>
            Queue:
            <select onChange={props.handleChange}>
                {props.values.map(d => <option key={d}>
                    {d}</option>)}
            </select>
        </div>
    )

}

export default QueueDropdown;