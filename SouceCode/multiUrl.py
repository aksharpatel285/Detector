import pandas as pd
import numpy as np
import random
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression
import pickle
import multiprocessing
import warnings


def sanitization(web):
    web = web.lower()
    token = []
    dot_token_slash = []
    raw_slash = str(web).split('/')
    for i in raw_slash:
        raw1 = str(i).split('-')
        slash_token = []
        for j in range(0,len(raw1)):
            raw2 = str(raw1[j]).split('.')
            slash_token = slash_token + raw2
        dot_token_slash = dot_token_slash + raw1 + slash_token
    token = list(set(dot_token_slash)) 
    if 'com' in token:
        token.remove('com')
    return token


def checkUrl(self,url):
        
    urls = []
    urls.append(url)
    #print (urls)

    whitelist = ['hackthebox.eu','root-me.org','gmail.com']
    s_url = [i for i in urls if i not in whitelist]

    #Loading the model
    file = "pickel_model.pkl"
    with open(file, 'rb') as f1:  
        lgr = pickle.load(f1)
    f1.close()
    file = "pickel_vector.pkl"
    with open(file, 'rb') as f2:  
        vectorizer = pickle.load(f2)
    f2.close()

    #predicting
    vectorizer = vectorizer
    x = vectorizer.transform(s_url)
    y_predict = lgr.predict(x)

    for site in whitelist:
        s_url.append(site)

    predict = list(y_predict)
    for j in range(0,len(whitelist)):
        predict.append('good')
        print(predict[0])
    return predict[0]


if __name__ == "__main__":
    a1 = input("Enter URL here : ")
    proc2 = multiprocessing.Process(target = checkUrl,args=("",a1))
    proc2.start()
    proc2.join()