from flask import Flask
import requests

app = Flask(__name__)

api_key = 'RGAPI-31e61257-c9a7-4588-907d-acc167ccc544'
url = 'https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/'
@app.route('/summoner/<summonerName>')
def get_summoner_name(summonerName):
    r = requests.get('https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/' + summonerName + '?api_key=RGAPI-31e61257-c9a7-4588-907d-acc167ccc544')
    return r.json()
