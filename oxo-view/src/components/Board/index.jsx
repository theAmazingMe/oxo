import React from 'react'
import BoardTemplate from './template'

const Board = ({ actions, status }) => {

    const grid = status.val.grid;

    return (
        <div>
            <BoardTemplate actions={actions} grid={grid} />
        </div>
    );
}

export default Board;