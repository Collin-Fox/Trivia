import React from "react";
import {Button} from "react-bootstrap";
import {Form} from "react-bootstrap";
import {useState} from "react";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from "axios";
function Login(){

    let navigate = useNavigate();

    const [ formData, setFormData ] = useState({
        gameID: 0,
        playerID: '',
        score: 0
    })
    function handleChange(e){
        const key = e.target.name;
        const value = e.target.value;
        setFormData({...formData, [key]: value});
        console.log(formData);
    }

    function handleSubmit(e){
        e.preventDefault();
        document.cookie = "username=" + formData["playerID"];
        document.cookie = "gameID=" + formData["gameID"];

        fetch(' https://b3f8-2600-387-f-6a19-00-5.ngrok-free.app/killMe',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        }).then(res => {
            setFormData({
                gameID: 0,
                playerID: '',
                score: 0
            })
        })

        navigate('/questions');


    }



    return(
        <Form id ="loginForm" onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="formGameID">
                <Form.Label>Game ID:</Form.Label>
                <Form.Control name ="gameID"type="text" placeholder="Enter Game ID" onChange={handleChange}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formName">
                <Form.Label>Name:</Form.Label>
                <Form.Control name="playerID" type="text" placeholder="Name" onChange={handleChange}/>
            </Form.Group>

            <Button variant="primary" type="submit">
                Submit
            </Button>
        </Form>
    )
}
export default Login