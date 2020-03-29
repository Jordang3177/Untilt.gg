from flask import Flask
from api_key import ApiKey
import requests

app = Flask(__name__)
api = ApiKey()
API_KEY = api.getKey()
url = 'https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/'
@app.route('/summoner/<summonerName>')
def get_summoner_name(summonerName):
    r = requests.get('https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/' + summonerName + '?api_key=' + API_KEY)
    return r.json()
