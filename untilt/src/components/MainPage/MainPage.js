import React, { useState } from "react";
import classes from "./MainPage.module.css";
import data from "../../assets/champions.json";
import SummonerInput from "../InputForm/InputForm";
import SubmitButton from "../Button/Button";
import ChampionDropdown from "../Dropdowns/ChampionDropdown/ChampionDropdown";
import SeasonDropdown from "../Dropdowns/SeasonDropdown/SeasonDropdown";
import QueueDropdown from "../Dropdowns/QueueDropdown/QueueDropdown";
import PreseasonDropdown from "../Dropdowns/PreseasonDropdown/PreseasonDropdown";

function MainPage() {
  const [summonerName, setSummonerName] = useState("");
  const [championName, setChampionName] = useState("a");
  const [seasonNumber, setSeasonNumber] = useState(0);
  const [queueType, setQueueType] = useState("a");
  const [preseasonIncluded, setPreseasonIncluded] = useState(true);
  const [submitted, setSubmitted] = useState(false);
  const [champions] = useState(data.champions);
  const [seasons] = useState(["", 8, 9, 10]);
  const [queues] = useState(["", "Normal", "Ranked"])
  const [bools] = useState(["Include", "Don't Include"]);
  const handleSummonerChange = (e) => {
    e.persist();
    setSummonerName(e.target.value);
  };

  const handleChampionChange = (e) => {
    e.persist();
    setChampionName(champions[e.target.value].champion);
    console.log(champions[e.target.value].champion);
  };

  const handleSeasonChange = (e) => {
    e.persist();
    setSeasonNumber(e.target.value);
  };

  const handleQueueChange = (e) => {
    e.persist();
    setQueueType(e.target.value);
  };

  const handlePreseasonChange = (e) => {
    e.persist();
    console.log(e.target.value);
    if (e.target.value === "Include") {
      setPreseasonIncluded(true);
      console.log(preseasonIncluded);
    }
    else {
      setPreseasonIncluded(false);
      console.log(preseasonIncluded);
    }
  };

  const handleSubmit = (e) => {
    if (summonerName === "") {
      alert("Summoner Name is required!");
      return;
    }
    setSubmitted(true);
    console.log(
      `/summoner/${summonerName}/${championName}/${seasonNumber}/${queueType}/${preseasonIncluded}`
    );
    fetch(
      `/summoner/${summonerName}/${championName}/${seasonNumber}/${queueType}/${preseasonIncluded}`
    )
      .then((res) => res.json())
      .then((json) => {
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
        <ChampionDropdown values={champions} handleChange={handleChampionChange} />
        <SeasonDropdown values={seasons} handleChange={handleSeasonChange} />
        <QueueDropdown values={queues} handleChange={handleQueueChange} />
        <PreseasonDropdown values={bools} handleChange={handlePreseasonChange} />
      </div>
      <SubmitButton
        className={classes.Submit}
        handleSubmit={handleSubmit}
        value="Submit"
      />
    </div>
  );
}

export default MainPage;
