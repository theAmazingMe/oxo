import React , { Component } from 'react';

import SquareTemplate from './template';

const Square = ({i,j,actions,grid}) => {

    let classes="square"

    if(grid[j][i]){
        classes+=" "+(grid[j][i]+"-signed").toLowerCase();
    }


    return (<SquareTemplate classes={classes} i={i} j={j} actions={actions}/>);
}

export default Square;