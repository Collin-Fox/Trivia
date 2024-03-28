# SQL METHODS TO CREATE AND GET AND DESTROY DATABASE
import json


def wrt(players, score):
    test = {
        "name": players,
        "score": score
    }
    obj = json.dumps(test, indent=2)
    with open("triviascores.json", "w") as outfile:
        outfile.write(obj)
