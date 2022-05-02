import requestTheApi from '../../helpers/request';
const fetchGameStatus = async () => {
  const queryString = window.location.search;
  const gameId = new URLSearchParams(queryString).get("gameId");
  const id = gameId | 0;

  const res = await fetch('http://localhost:8080/api/oxo/' + id, {
    method: "GET",
    mode: "cors",
    headers: { 'Content-Type': 'application/json' }
  })
  const data = await res.json()

  return data
}

const placeSymbole = (status, conclusion, i, j) => {
  requestTheApi(
    'http://localhost:8080/api/oxo',
    'POST',
    {
      "gameId": status.val.gameId,
      "action": "PLAY",
      "move": {
        "column": i,
        "line": j
      }
    },
    (data) => {
      if (data.conclusion.type === "FAULTED") {
        conclusion.set(data.conclusion)
      } else {
        status.set(data);
      }
      conclusion.set(data.conclusion);
    })
}

const createNewGame = (conclusion, status, refresh) => {
  requestTheApi(
    'http://localhost:8080/api/oxo',
    'POST',
    { "action": "INIT" },
    (data) => {
      conclusion.set(data.conclusion)
      status.set(data);
      refresh(data.gameId);
    })
}

const takeRevenge = (status, conclusion) => {
  requestTheApi(
    'http://localhost:8080/api/oxo',
    'POST',
    {
      "gameId": status.val.gameId,
      "action": "REVENGE"
    },
    (data) => {
      status.set(data);
      conclusion.set(data.conclusion);
    })
}

const switchTurns = (status) => {
  requestTheApi(
    'http://localhost:8080/api/oxo',
    'POST',
    {
      "gameId": status.val.gameId,
      "action": "SWITCH_TURNS"
    },
    (data) => {
      status.set(data);
    })
}
const resetScore = (status) => {
  requestTheApi(
    'http://localhost:8080/api/oxo',
    'POST',
    {
      "gameId": status.val.gameId,
      "action": "RESET_SCORE"
    },
    (data) => {
      status.set(data);
    })
}

export { createNewGame, fetchGameStatus, placeSymbole, switchTurns, takeRevenge, resetScore }