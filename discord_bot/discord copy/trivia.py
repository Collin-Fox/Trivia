import requests
from random import choice
from difflib import SequenceMatcher

"""
    How the JSON is formatted 
    "category": "music",
    "question": "Who Was The Midnight Rider",
    "answer": "Paul Davidson"
"""

'''
A way to ask questions 

A way to check right answer or how similar the answer is 

A way to keep track of our scores 

A way to start the game 

A way to end the game 
'''

MIN_ANSWER_THRESHOLD = 0.9


# GENERATE A QUESTION OF RANDOM TOPIC
def generate_question():
    category = choice(
        ['artliterature', 'language', 'sciencenature', 'general', 'fooddrink', 'peopleplaces', 'geography',
         'historyholidays', 'entertainment', 'toysgames', 'music', 'mathematics', 'sportsleisure'])
    category = 'sportsleisure'
    api_url = 'https://api.api-ninjas.com/v1/trivia?category={}'.format(category)

    response = requests.get(api_url, headers={'X-Api-Key': '8TsTxqxFQXRzfR25mtdzaDuY8aqbcPYwxpRF4GBd'})

    if response.status_code == requests.codes.ok:
        json = response.json()[0]
        question = json['question']
        answer = json['answer']
        print(json['category'])
        print(json['question'])
        print(json['answer'])
        return question, answer


    else:
        print("Error:", response.status_code, response.text)
        return "TRIVIA ERROR"


# ASSIGNS A SIMILARITY BETWEEN TRIVIA ANSWER AND HUMAN RESPONSE
def answer_similarity(actual: str, response: str) -> float:
    actual, response = actual.lower(), response.lower()
    return SequenceMatcher(None, actual, response).ratio()


generate_question()
x = answer_similarity("a diamond stud", "a mesh stud")
print(x)
