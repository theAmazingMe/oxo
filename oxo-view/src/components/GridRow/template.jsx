import Square from '../Square';
const GridRowTemplate = ({j,actions,grid}) => {
    return (<div className="board-row">
        <Square i={0} j={j} actions={actions} grid={grid} />
        <Square i={1} j={j} actions={actions} grid={grid} />
        <Square i={2} j={j} actions={actions} grid={grid} />
    </div>)
}

export default GridRowTemplate;