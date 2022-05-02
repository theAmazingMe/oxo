import GridRow from '../GridRow';
const BoardTemplate = ({ message , actions , grid }) => {

    const createLines = () => {
        return [0, 1, 2].map(j => {
            return (
                <GridRow key={j} j={j} actions={actions} grid={grid}/>
            )
        });
    }

    return (
        <div>
            {createLines()}
            <div className="status">{message}</div>
        </div>
    );
}

export default BoardTemplate;