import requestTheApi from '../../helpers/request';


const createPlayers = (status,pseudos,conclusion) => {
    const listOfpseudos = pseudos.val || [];
    requestTheApi(
      '/api/oxo/players',
      'POST',
      { 
        "gameId": status.val.gameId,
        "pseudos" : listOfpseudos ,
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