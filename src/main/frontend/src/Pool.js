import React from 'react';
import clsx from 'clsx';
import './Pool.css';

export default function Pool({ player, placeholder, ...rest }) {
    return <div 
        className={clsx("Pool", `Pool_${player}`, {"Pool_placeholder": placeholder})} 
        {...rest} />;
}