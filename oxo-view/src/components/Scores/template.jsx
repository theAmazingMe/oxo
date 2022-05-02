const ScoresTemplate = ({ status }) => {
    const displayScores = () => {
        if (status && status.val) {
            if (/(FINISHED|DRAW|FAULTED)/.test(status.val.conclusion.type)) {
                const p1 = status.val.players[0];
                const p2 = status.val.players[1];

                return (
                    <div className="row">
                        <div className="col card">
                            <table className="table">
                                <thead>
                                    <tr><th>pseudo</th><th>victories</th><th>losses</th></tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th>{p1.pseudo}</th><td>{p1.wins}</td><td>{p1.loss}</td>
                                    </tr>
                                    <tr>
                                        <th>{p2.pseudo}</th><td>{p2.wins}</td><td>{p2.loss}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>
                );
            }
        }
    }
    return (<div>
        {displayScores()}
    </div>);
}

export default ScoresTemplate;
