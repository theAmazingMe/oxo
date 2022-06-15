import requestTheApi from '../../helpers/request';

const createPlayers = (status,pseudos,conclusion) => {
    requestTheApi(
      'http://localhost:8080/api/oxo/players',
      'POST',
      { 
        "gameId": status.val.gameId,
        "pseudos": pseudos.val,
      },
      (data) => {
        if (data.conclusion.type === "INVALID") {
          conclusion.set(data.conclusion)
        } else {
          status.set(data);
        }
        conclusion.set(data.conclusion);
      })
  }
  
  export {createPlayers}