from flask import Flask
from api_key import ApiKey
import requests
import time

app = Flask(__name__)
api = ApiKey()
API_KEY = api.getKey()
summoner_name_url = 'https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/'
match_url = 'https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/'
@app.route('/summoner/<summonerName>')
def get_summoner_name(summonerName):
    r = requests.get(summoner_name_url + summonerName + '?api_key=' + API_KEY)
    local_time = get_time_of_game(r.json()['accountId'])
    return local_time

def get_time_of_game(accountId):
    r = requests.get(match_url + accountId + '?api_key=' + API_KEY)
    epoch = r.json()
    return epoch
