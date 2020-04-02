import React from "react";
import classes from "./InputForm.module.css";

function SummonerInputForm(props) {
  /*   const [returnedValue, setReturnedValue] = useState("");
  const [time, setTime] = useState(
    new Date("July 1, 1978 02:30:00")
  ); */

  return (
    <div className={props.class}>
      <label>{props.label}</label>
      <input
        type={props.type}
        value={props.summonerName}
        onChange={props.handleChange}
      />
      {/* <p>The summoner level is {returnedValue}</p>
      <p>
        The time is {time.toGMTString()}
        <br></br>
        {time.toLocaleString()}
      </p> */}
    </div>
  );
}

export default SummonerInputForm;
