import React from "react";
import {Button, Nav, Card} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

function SelectAnswer (props){
    return(
        <select className="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
            <option value={props.correct}>{props.correct}</option>
            <option value={props.incA}>{props.incA}</option>
            <option value={props.incB}>{props.incB}</option>
            <option value={props.incC}>{props.incC}</option>
        </select>
    )
}

export default SelectAnswer;
