from flask import Flask
from api_key import ApiKey
import requests
import time

app = Flask(__name__)
api = ApiKey()
API_KEY = api.getKey()
summoner_name_url = 'https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/'
match_history_url = 'https://na1.api.riotgames.com/lol/match/v4/matchlists/by-account/'
match_url = 'https://na1.api.riotgames.com/lol/match/v4/matches/'
@app.route('/summoner/<summonerName>')
def main(summonerName):
    summoner_data = get_summoner_data(summonerName)
    match_history = get_match_history(summoner_data.json()['accountId'])
    wins_by_time = {}
    for i in range(23):
        wins_by_time[i] = 0
    return match_history

def get_summoner_data(summoner):
    response = requests.get(summoner_name_url + summoner + '?api_key=' + API_KEY)
    return response

def get_match_history(accountId):
    response = requests.get(match_history_url + accountId + '?api_key=' + API_KEY)
    epoch_time = response.json()
    return epoch_time