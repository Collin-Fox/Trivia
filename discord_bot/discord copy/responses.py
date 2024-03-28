import urllib.request
from random import choice, randint
import requests
import openai
from dotenv import load_dotenv
import os
import datetime
from pathlib import Path
from PIL import Image

load_dotenv()
OPEN_AI: str = os.getenv("CHAT_TOKEN")
openai.api_key = OPEN_AI


def talk(user_input: str) -> str:
    response = openai.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[
            {"role": "user", "content": user_input}
        ]
    )
    return response.choices[0].message.content


def picture(user_input: str):
    response = openai.images.generate(
        model="dall-e-3",
        prompt=user_input,
        size="1024x1024",
        quality="standard",
        n=1,
    )
    image = response.data[0].url
    urllib.request.urlretrieve(image, "/Users/collinfox/PycharmProjects/discord/image.png")
    return image


def get_response(user_input: str) -> str:
    lowered: str = user_input.lower()
    return 'TEST'



def sports():
    response = requests.get("https://api.sportsdata.io/v3/cbb/stats/json/BoxScores/" + str(
        datetime.date.today()) + "?key=8795543ee8af490584d6054d93fe78bd")
    print(datetime.date.today())
    retString = ""
    for json in response.json():
        json = json["Game"]
        gameString: str = json["HomeTeam"] + ": " + str(json["HomeTeamScore"]) + " vs " + json["AwayTeam"] + ": " + \
                          str(json["AwayTeamScore"]) + "\n" + str(json["Period"]) + " " + str(
            json["TimeRemainingMinutes"]) + ":" + str(json["TimeRemainingSeconds"])
        retString += gameString + "\n\n"

    return retString


def speak(user_input: str, index):
    path = "trivia" + str(index) + ".mp3"
    speech_file_path = Path(__file__).parent / path
    response = openai.audio.speech.create(
        model="tts-1-hd",
        voice="onyx",
        input=user_input
    )

    response.stream_to_file(speech_file_path)


