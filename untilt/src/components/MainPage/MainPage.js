import React, { useState } from "react";
import classes from "./MainPage.module.css";
import data from "../../assets/champions.json";
import SummonerInput from "../InputForm/InputForm";
import SeasonInput from "../InputForm/InputForm";
import QueueInput from "../InputForm/InputForm";
import PreaseasonInput from "../InputForm/InputForm";
import SubmitButton from "../Button/Button";
import Dropdown from "../Dropdown/Dropdown";

function MainPage() {
  const [summonerName, setSummonerName] = useState("");
  const [championName, setChampionName] = useState("");
  const [seasonNumber, setSeasonNumber] = useState(0);
  const [queueType, setQueueType] = useState("");
  const [
    preaseasonIncluded,
    setPreseasonIncluded
  ] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const [champions] = useState(data.champions)
  const handleSummonerChange = e => {
    e.persist();
    setSummonerName(e.target.value);
  };

  const handleChampionChange = e => {
    e.persist();
    setChampionName(champions[e.target.value].champion);
    console.log(champions[e.target.value].champion);
  };

  const handleSeasonChange = e => {
    e.persist();
    setSeasonNumber(e.target.value);
  };

  const handleQueueChange = e => {
    e.persist();
    setQueueType(e.target.value);
  };

  const handlePreseasonChange = e => {
    e.persist();
    setPreseasonIncluded(true);
  };

  const handleSubmit = e => {
    if (summonerName === "") {
      alert("Summoner Name is required!");
      return;
    }
    setSubmitted(true);
    console.log(
      `/summoner/${summonerName}/${championName}/${seasonNumber}/${queueType}/${preaseasonIncluded}`
    );
    fetch(
      `/summoner/${summonerName}/${championName}/${seasonNumber}/${queueType}/${preaseasonIncluded}`
    )
      .then(res => res.json())
      .then(json => {
        console.log(json);
      });
  };

  if (submitted) {
    console.log(submitted);
  }

  return (
    <div>
      <h1 className={classes.Title}>Untilt.gg</h1>
      <div className={classes.InputFields}>
        <SummonerInput
    label="Summoner Name: "
    type="text"
    value={summonerName}
    handleChange={handleSummonerChange}
    />
    <Dropdown
    values={champions}
    handleChange={handleChampionChange}/>
        <SeasonInput
    label="Season: "
    type="number"
    value={seasonNumber}
    handleChange={handleSeasonChange}
    />
        <QueueInput
    label="Queue: "
    type="text"
    value={queueType}
    handleChange={handleQueueChange}
    />
        <PreaseasonInput
    label="Preaseason: "
    type="text"
    value={preaseasonIncluded}
    handleChange={handlePreseasonChange}
    />
        <SubmitButton
    handleSubmit={handleSubmit}
    value="Submit"
    />
      </div>
    </div>
  );
}

export default MainPage;
