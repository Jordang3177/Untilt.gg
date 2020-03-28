import React, { useState } from "react";

const MainPage = () => {
  const [summonerName, setSummonerName] = useState("");
  const [returnedValue, setReturnedValue] = useState("");

  const handleChange = e => {
    e.persist();
    setSummonerName(e.target.value);
  };

  const handleSubmit = e => {
    e.preventDefault();
    fetch(`/summoner/${summonerName}`)
      .then(res => res.json())
      .then(json => setReturnedValue(json.accountId));
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
      <p>The summoner name is {returnedValue}</p>
    </div>
  );
};

export default MainPage;
