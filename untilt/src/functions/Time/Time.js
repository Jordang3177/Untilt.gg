import React, { useEffect, useState } from "react";

function Time(parentCallback) {
  const [currentTime, setCurrentTime] = useState(0);
  useEffect(() => {
    fetch("/time")
      .then(res => res.json())
      .then(data => {
        setCurrentTime(data.time);
      });
  }, []);
  function sendData() {
    parentCallback(currentTime);
  }
  return sendData();
}

export default Time;
