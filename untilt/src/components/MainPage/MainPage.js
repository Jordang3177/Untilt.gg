import React, { useState } from "react";

function MainPage() {
  const [summonerName, setSummonerName] = useState("");
  const [returnedValue, setReturnedValue] = useState("");
  const [time, setTime] = useState(
    new Date("July 1, 1978 02:30:00")
  );

  const handleChange = e => {
    e.persist();
    setSummonerName(e.target.value);
  };

  const handleSubmit = e => {
    e.preventDefault();
    fetch(`/summoner/${summonerName}`)
      .then(res => res.json())
      .then(json => {
        setReturnedValue(json.matches[0].timestamp);
        setTime(new Date(json.matches[0].timestamp));
      });
  };

  return (
    <div className="SummonerForm">
      <form onSubmit={handleSubmit}>
        <label htmlFor="summonerName">Summoner Name:</label>
        <input
          type="text"
          value={summonerName}
          onChange={handleChange}
        />

        <input type="submit" />
      </form>
      <p>The summoner level is {returnedValue}</p>
      <p>
        The time is {time.toGMTString()}
        <br></br>
        {time.toLocaleString()}
      </p>
    </div>
  );
}

export default MainPage;
