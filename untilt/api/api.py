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
@app.route('/summoner/<summonerName>/<champion>/<season>/<queue>/<preseason>')
def main(summonerName, champion, season, queue, preseason):
    champion = champion
    season = season
    queue = queue
    preseason = preseason
    print(champion)
    print(season)
    print(queue)
    print(preseason)
    print('start')
    summoner_data = get_summoner_data(summonerName)
    print(summoner_data)
    print('got summoner data')
    try:
        accountId = summoner_data['accountId']
    except:
        print('error')
        return
    match_history = get_match_history(summoner_data['accountId'])
    print('got match history')
    wins_by_time = {}
    wins = []
    for i in range(23):
        wins_by_time[i] = 0
    print('finished making dictionary')
    print('\n')
    print(match_history)

    for i in range(5):
        print('on iteration ' + str(i))
        gameId = match_history['matches'][i]['gameId']
        toDatabase = match_history['matches'][i]
        
        wins.append(get_winner_of_match(accountId, gameId))
    print('finished')
    winners = {'wins': wins}
    print(winners)
    return winners

def get_summoner_data(summoner):
    response = requests.get(summoner_name_url + summoner + '?api_key=' + API_KEY)
    return response.json()

def get_match_history(accountId):
    response = requests.get(match_history_url + accountId + '?api_key=' + API_KEY)
    matches = response.json()
    return matches

def get_winner_of_match(accountId, matchId):
    response = requests.get(match_url + str(matchId) + '?api_key=' + API_KEY)
    response = response.json()
    if response['teams'][0]['win'] == 'Win':
        team_100_wins = True
    else:
        team_100_wins = False
    for participants in response['participantIdentities']:
        if participants['player']['accountId'] == accountId:
            if participants['participantId'] <= 5 and team_100_wins:
                return True
            elif participants['participantId'] > 5 and not team_100_wins:
                return True
            else:
                return False
    return 'not found'

## Add results to database.
## ID key should be Summoner ID/Account ID.
## Then put MatchID as another entry
## Then Champion played in that match
## When that match was
## Season number
## Won or lost
## 
    