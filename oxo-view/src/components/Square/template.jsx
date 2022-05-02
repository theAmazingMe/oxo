
const SquareTemplate = ({i,j,actions,classes}) => {

    return (<button className={classes} onClick={ () => {actions.placeSymbole(i,j);}}></button>)
};


export default SquareTemplate;