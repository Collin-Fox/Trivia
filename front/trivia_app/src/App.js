import logo from './logo.svg';
import './App.css';
import SelectAnswer from "./Components/SelectAnswer";
import React from "react";
import {useEffect} from "react";
import {useState} from "react";
import axios from "axios";
import {Button, Nav, Card} from 'react-bootstrap'
import Login from "./Login";
import 'bootstrap/dist/css/bootstrap.min.css'
import QuestionForm from "./QuestionForm";
import ReactDOM from "react-dom/client";
import {BrowserRouter, Routes, Route} from "react-router-dom";

function App() {
    return(

        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login></Login>}></Route>
                <Route path="/questions" element={<QuestionForm></QuestionForm>}></Route>
            </Routes>
        </BrowserRouter>

    )
}

export default App;
