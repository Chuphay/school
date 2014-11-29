
critics={'Lisa Rose': {'Lady in the Water': 2.5, 'Snakes on a Plane': 3.5,

 'Just My Luck': 3.0, 'Superman Returns': 3.5, 'You, Me and Dupree': 2.5,

 'The Night Listener': 3.0},

'Gene Seymour': {'Lady in the Water': 3.0, 'Snakes on a Plane': 3.5,

 'Just My Luck': 1.5, 'Superman Returns': 5.0, 'The Ni1ght Listener': 3.0,

 'You, Me and Dupree': 3.5},

'Michael Phillips': {'Lady in the Water': 2.5, 'Snakes on a Plane': 3.0,

 'Superman Returns': 3.5, 'The Night Listener': 4.0},

'Claudia Puig': {'Snakes on a Plane': 3.5, 'Just My Luck': 3.0,

 'The Night Listener': 4.5, 'Superman Returns': 4.0,

 'You, Me and Dupree': 2.5},

'Mick LaSalle': {'Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,

 'Just My Luck': 2.0, 'Superman Returns': 3.0, 'The Night Listener': 3.0,

 'You, Me and Dupree': 2.0},

'Jack Matthews': {'Lady in the Water': 3.0, 'Snakes on a Plane': 4.0,

 'The Night Listener': 3.0, 'Superman Returns': 5.0, 'You, Me and Dupree': 3.5},

'Toby': {'Snakes on a Plane':4.5,'You, Me and Dupree':1.0,'Superman Returns':4.0}}
from math import sqrt
def sim_distance(name,p1,p2):
    try:
        return critics[p1][name] - critics[p2][name]
    except KeyError:
        return None

    
def similarity(p1,p2,other = None):
    data = []
    for i in critics[p1].keys():
        data.append(sim_distance(i,p1,p2)) 
    return data    

def sum_n_sq(ls):
    out = 0
    for i in ls:
        try:
            out += i*i 
        except TypeError:    
            pass
    return out

def most_sim(p, fn = sum_n_sq, sim = similarity):
    data = {}
    for i in critics.keys():
        data[i] = (fn(sim(p,i)))
    return data

def squish(z):
    return 1/(1+z)

def recommend(p, squash = squish, fn = sum_n_sq, sim = similarity):
    mos_sim = most_sim(p, fn = sum_n_sq, sim = similarity)  
    recommendations = {}
    popular = {}
    for critic in mos_sim:
        if critic == p: continue # don't compare me to myself 
        for movie in critics[critic]:
            print(movie)
            try:
                recommendations[movie]+= squash(critics[critic][movie])
                popular[movie]+= 1 
            except KeyError:
                recommendations[movie] = squash(critics[critic][movie])
                popular[movie] = 1 
    for movie in recommendations:
        recommendations[movie] = recommendations[movie]/popular[movie] 

    return recommendations	

if __name__ == '__main__':
    print(sorted(recommend('Toby').items(), key=lambda x: x[1]))














	
	
