import React from 'react';

function ChampionDropdown(props) {
    return(
        <div>
            Champion:
            <select onChange={props.handleChange}>
                {props.values.map(d => <option key={d.id}
                    value={d.id}>
                    {d.champion}</option>)}
            </select>
        </div>
    )

}

export default ChampionDropdown;