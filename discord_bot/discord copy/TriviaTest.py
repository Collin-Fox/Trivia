import requests
import json
import pprint

url = "http://localhost:8080/game/start"


def startTrivia():
    response = requests.get(url)
    print(response.status_code)
    print(response.text)
    gameID = response.text
    response = requests.get("http://localhost:8080/q/game/" + response.text)
    print(response.text)
    link = requests.get("http://localhost:8080/game/link")

    print(response.text)
    print(gameID)
    questions = requests.get("http://localhost:8080/q/gameByID/" + gameID)
    jsonString = questions.json()

    return str(gameID), str(link.text), jsonString


def getScripts(jsonString: json):
    questionArray = []
    for j in jsonString:
        questionObject = {
            "q": j["q"],
            "a": j["correct"],
            "b": j["incA"],
            "c": j["incB"],
            "d": j["incC"]
        }

        questionArray.append(questionObject)


    return questionArray
