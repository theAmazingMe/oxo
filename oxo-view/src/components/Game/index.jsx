import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom'
import GameTemplate from './template'
import {fetchGameStatus,createNewGame,placeSymbole,switchTurns , takeRevenge,resetScore} from './gameRequests'
import {createPlayers} from './playersRequests'

const { useStates } = require("rehookt")

const Game = () => {

  const navigate = useNavigate();
  const refresh = (id = 0) => navigate({
    search: '?gameId=' + id
  });

  const hooks = useStates(
    ["selectedGameId",0],["conclusion",{}],["status", null], ["pseudos",[{pseudo:""},{pseudo:""}]]
  );
  const { selectedGameId,status, pseudos, conclusion } = hooks;

  const onChangePseudo = (event,i) => {
    if(!/^.{0,15}$/g.test(event.target.value)){
      event.target.value=event.target.value.substring(0, 15)
    }
    pseudos.val[i] = event.target.value;
  }
  const onChangeSelectedGameId = (event) => {
    if(!/^[\d]{0,4}$/g.test(event.target.value)){
      event.target.value=event.target.value.substring(0, 4)
    }
    
    selectedGameId.set(event.target.value);
  }

  useEffect(() => {
    const fetchGame = async () => {
      const game = await fetchGameStatus()
      status.set(game)
      conclusion.set(game.conclusion);
    }

    fetchGame()
  },[]);

  const actions = {
    "placeSymbole": (i, j) => {
      placeSymbole (status,conclusion,i,j);
    },
    "joinGame": (event) => {
      event.preventDefault();
      refresh(selectedGameId.val);
      window.location.reload();
    },
    "createGame": (event) => {
      event.preventDefault();
      createNewGame(conclusion,status,refresh);
    },
    createPlayers : (event) => {
      event.preventDefault();
      createPlayers(status,pseudos,conclusion);
    },
    takeRevenge : (event) => {
      event.preventDefault();
      takeRevenge(status,conclusion);
    },
    resetScore : (event) => {
      event.preventDefault();
      resetScore(status);
    },
    switchTurns : (event) => {
      event.preventDefault();
      switchTurns(status);
    }
  }
  
  return (<GameTemplate actions={actions} hooks={hooks} onChangePseudo={onChangePseudo} onChangeSelectedGameId={onChangeSelectedGameId}/>)
}

export default Game;