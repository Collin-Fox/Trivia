import React, {useEffect, useState} from "react";
import axios from "axios";
import SelectAnswer from "./Components/SelectAnswer";
import {Button} from "react-bootstrap";

function QuestionForm() {
//Get a list of all the questions in the thing
    const [questions, setQuestions] = useState([]);

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
        console.log(parts)
        return parts
    }

    useEffect(() => {
        let id = getCookie("gameID");
        console.log("TEET")
        console.log("SHIT" + id)
        axios.get(' https://b3f8-2600-387-f-6a19-00-5.ngrok-free.app/q/gameByID/'+ id,{
            headers: {"ngrok-skip-browser-warning": "true"}
        })
            .then(response =>{
                setQuestions(response.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    return (
        <div className="App">
            <h1>Welcome To Trivia!!!</h1>

            <ul>
                {questions.map(question => (
                    <SelectAnswer correct={question.correct} incA={question.incA} incB={question.incB} incC={question.incC} key={question.questionID}/>
                ))}
            </ul>
            <Button>Submit</Button>
        </div>
    );
}
export default QuestionForm;