import GridRowTemplate from './template'

const GridRow = ({j,actions,grid}) => {
    
    return (<GridRowTemplate j={j} actions={actions} grid={grid}/>)
}

export default GridRow;