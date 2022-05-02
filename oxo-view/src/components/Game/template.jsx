
import Board from '../Board';
import Scores from '../Scores';

const GameTemplate = ({ hooks,actions, onChangePseudo, onChangeSelectedGameId }) => {

    const {status,conclusion} = hooks;

    const invalidStatus = !status || !status.val;
    const nonExistantGameStatus = status && status.val && status.val.conclusion.type === "NON_EXISTENT";
    const gameConcluded = status && status.val && /(FINISHED|DRAW|FAULTED)/.test(status.val.conclusion.type);

    const initNewGameTemplate = () => {
        if(invalidStatus||nonExistantGameStatus||gameConcluded){
            return (
                <><div className="col card">
                    <form onSubmit={(event) => { actions.joinGame(event) }}>
                        <div className="input-group mb-3">
                            <input type="number" className="form-control" name="game-id" id="game-id" placeholder="Game id" onChange={onChangeSelectedGameId} />
                            <input className="btn btn-primary input-group-text" type="submit" value="Join" />
                        </div>
                    </form>
                    <div className="input-group mb-3">
                        <button className="btn btn-primary" onClick={(event) => { actions.createGame(event) }}> Create a new game </button>
                    </div>
                </div>
                </>
            )
        }
    }

    const gameFoundHint = () => {
        if (invalidStatus || nonExistantGameStatus) {
            return (
                <div className="row">
                    <div className="col card game-not-found">
                        <h4> No game joined or created yet </h4>
                    </div>
                </div>)
        } else {
            return (
                <div className="row">
                    <div className="col card game-found">
                        <h4> Game ID : {status.val.gameId} </h4>
                    </div>
                </div>)
        }
    }

    const displayResetButtons = () => {
        if (gameConcluded) {
            return (<div className="col card">
                <button className='btn btn-primary' onClick={(event) => actions.takeRevenge(event)}>reset</button><br />
                <button className='btn btn-primary' onClick={(event) => actions.resetScore(event)}>reset scores</button>
            </div>)
        } else if (status.val && status.val.players && /(ONGOING)/.test(status.val.conclusion.type) && status.val.turnCount === 0) {
            const players = status.val.players;
            return (<div className="col card">

                <span>Before doing the first move.</span><br />
                <button className='btn btn-primary' onClick={(event) => actions.switchTurns(event)}>switch turns</button>
                <hr />
                <span>{players[0].pseudo} plays with the <b>X's</b> </span>
                <span>{players[1].pseudo} plays with the <b>O's</b></span>
            </div>);
        }
    }

    const templateGameFound = () => {
        if (!(invalidStatus || nonExistantGameStatus)) {
            if (!status.val.players || !status.val.players.length) {

                return (
                    <div className="col card">
                        <form onSubmit={event => { actions.createPlayers(event) }}>
                            <div className="input-group mb-3">
                                <span className="input-group-text">p1</span>
                                <input maxLength='15' type="text" className='form-control' placeholder="Pseudo" name="player1" id="player1" onChange={event => onChangePseudo(event, 0)}></input>
                            </div>

                            <div className="input-group mb-3">
                                <span className="input-group-text">p2</span>
                                <input maxLength='15' type="text" className='form-control' placeholder="Pseudo" name="player2" id="player2" onChange={event => onChangePseudo(event, 1)}></input>
                            </div>

                            <input className='btn btn-primary' type="submit" value="Validate" />
                        </form>
                    </div>)
            } else {
                return (
                    <div id="game-container" className="col card">
                        <div className="game">
                            <div className="game-board">
                                <Board actions={actions} status={status} />
                            </div>
                        </div>
                    </div>);
            }
        }
    }

    const displayMessageTurns = () => {
        if (status && status.val && status.val.players) {
            const turns = status.val.turnCount;
            const players = status.val.players;
            const i = turns % 2;

            let message = ` It is up to ${players[i].pseudo} (${['X', 'O'][i]}) now.`
            if (status.val.conclusion.type === "ONGOING" && !turns) {
                return `${status.val.conclusion.message}.${message}`
            }
            return message;
        }
    }

    const displayMessages = () => {
        const invalidStatus = !status || !status.val;
        const nonExistantGameStatus = status && status.val && status.val.conclusion.type === "NON_EXISTENT";

        if (!(invalidStatus || nonExistantGameStatus)) {
            
            if (!status.val.players || !status.val.players.length) {
                return conclusion.val.type === "INVALID"? 
                (<span>{conclusion.val.message}</span>):(<span> Who are the contesters ? </span>)
            }
        }
        if (status && status.val) {
            if (conclusion.type === "FINISHED") {
                return `${conclusion.val.message}.`
            }
            else if (conclusion.type === "DRAW") {
                return `${conclusion.val.message}.`
            }
            else if (conclusion.type === "ONGOING") {
                return displayMessageTurns();
            }
            return `${conclusion.val.message}.`;
        }
    }

    return (
        <>
            <div className="container">
                {gameFoundHint()}
            </div>
            <div className="container">
                <div id="actions">
                    <div className="row">
                        {initNewGameTemplate()}
                        {displayResetButtons()}
                    </div>
                </div>
            </div>
            <div className="container">
                <div className="row">

                    {templateGameFound()}

                    <div className="col">
                        <div className="row">
                            <div id="feedbacks" className="col card">
                                <span>{displayMessages()}</span>
                            </div>
                        </div>
                        <Scores status={status} />
                    </div>
                </div>
            </div>
        </>
    );
}

export default GameTemplate;