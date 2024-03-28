from typing import Final
import os

import discord
from dotenv import load_dotenv
from discord import Intents, Client, Message
from responses import get_response, talk, picture, sports, speak
from TriviaTest import getScripts, startTrivia
import openai

from datetime import datetime, timedelta
import time
import nacl
import nextcord

QUESTION = "TEST"
ANSWER = "TEST"
# LOAD TOKEN
load_dotenv()
TOKEN: Final[str] = os.getenv("DISCORD_TOKEN")
CHAT: Final[str] = os.getenv("CHAT_TOKEN")
print(CHAT)

# SETUP OPENAI
openai.api_key = CHAT

# SET UP BOT
intents: Intents = Intents.default()
intents.message_content = True  # NOQA
client: Client = Client(intents=intents)


# MESSAGE FUNCTIONS
async def send_message(message: Message, user_message: str) -> None:
    if not user_message:
        print('Intents not enabled')
        return
    if is_chat := user_message[0] == '?':
        user_message = user_message[1:]
    if is_image := user_message[0] == '!':
        user_message = user_message[1:]
    try:
        # FILL OUT TRIVIA EVENT HANDLERS
        if user_message == '/TRIVIA':
            response = await playTrivia(message)
            pass
        elif user_message == '/TRIVIA STOP':
            pass
        elif user_message == '/sports':
            response: str = sports()
        elif user_message == "/speak":
            await talkToVoice(message, False)
            response: str = "OK"
        elif is_chat:
            response: str = talk(user_message)
        elif is_image:
            url = picture(user_message)
            await message.channel.send(file=discord.File('/Users/collinfox/PycharmProjects/discord/image.png'))
            response: str = 'here is your image'
        else:
            pass
        await message.channel.send(response)
    except Exception as e:
        print(e)


# HANDLE STARTUP
@client.event
async def on_ready() -> None:
    print(f'{client.user} is now running')


# HANDLE INCOMING MESSAGE
@client.event
async def on_message(message: Message) -> None:
    if message.author == client.user:
        return

    username: str = str(message.author)
    user_message: str = message.content
    channel: str = message.channel
    print(f'[{channel}] {username}: "{user_message}"')
    await send_message(message, user_message)


# HANDLE JOINING VOICE CHAT AND SPEAKING
async def talkToVoice(message: Message, chase):
    user = message.author
    if user.voice != None:

        print(user.voice.channel)
        try:
            vc = await user.voice.channel.connect()
        except:
            vc = client.voice_clients[0]
            print(vc)
        speak("Hello World", 0)
        if vc.is_playing():
            vc.stop()

        if chase:
            vc.play(discord.FFmpegOpusAudio(executable="/opt/homebrew/Cellar/ffmpeg/6.1.1_4/bin/ffmpeg",
                                            source="/Users/collinfox/PycharmProjects/discord/Chase McKellar 1st French horn piece.mp3"))
        else:
            vc.play(discord.FFmpegOpusAudio(executable="/opt/homebrew/Cellar/ffmpeg/6.1.1_4/bin/ffmpeg",
                                            source="/Users/collinfox/PycharmProjects/discord/speech.mp3"))


# Shows on the first call of trivia start, display rules
async def displayTriviaRules(message: Message) -> str:
    await message.channel.send("You have twenty seconds to answer. Answers must start with $")

    return "DONE"


# Shows the score of everyone participating in trivia
def displayScore() -> str:
    pass


# HANDLE JOINING VOICE CHAT AND SPEAKING
async def speakTrivia(message: Message, triviaScript, index):
    user = message.author
    path = "trivia" + str(index) + ".mp3"
    print(path)
    print("FROM AUDIO")

    if user.voice != None:

        print(user.voice.channel)
        try:
            vc = await user.voice.channel.connect()
        except:
            vc = client.voice_clients[0]
            print(vc)
        # speak(triviaScript)
        if vc.is_playing():
            while vc.is_playing():
                print("IM TALKING")
                time.sleep(1)

        vc.play(discord.FFmpegOpusAudio(executable="/opt/homebrew/Cellar/ffmpeg/6.1.1_4/bin/ffmpeg",
                                        source="/Users/collinfox/PycharmProjects/discord/" + path))
    return "DONE"


"""
TODO: Fix error where it only asks the last question
"""


async def playTrivia(message: Message):
    # ASK A QUESTION EVERY 20 SECONDS
    # Between the time the question is asked and the next question is asked listen for answers

    script = []
    gameID, link, jsonString = startTrivia()
    await message.channel.send("STARTING TRIVIA \nGAMEID = " + gameID + "\nLINK = " + link + "\nGenerating scripts...")
    questionArray = getScripts(jsonString)
    for t in questionArray:
        question, a, b, c, d = t["q"], t["a"], t["b"], t["c"], t["d"]

        questionScript = talk(question + ": " + a + ", " + b + ", " + c + ", " + d + "\n This is a trivia question. I "
                                                                                     "want you to write a script for "
                                                                                     "this as if you were a trivia "
                                                                                     "host. You should read the "
                                                                                     "question then read the answers, "
                                                                                     "do not reveal the correct "
                                                                                     "answer.")
        script.append(questionScript)

  #  for i in range(0, 5):
   #     print(script[i])
    #    speak(script[i], i)
     #   await speakTrivia(message, script[i], i)

    print(script[0])
    speak(script[0], 0)
    await speakTrivia(message, script[0], 0)

    #  print(script[1], 1)
    #  speak(script[1], 1)
    #  await speakTrivia(message, script[1], 1)

    return "DONE"


# ENTRY POINT
def main() -> None:
    client.run(token=TOKEN)


if __name__ == '__main__':
    main()
