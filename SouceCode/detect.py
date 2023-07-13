from pysafebrowsing import SafeBrowsing

def checkurlfrom_Detect(self,url):
    
    KEY = "AIzaSyB1YA-Q2ZM4s1kC6pbVQSeTdUVolhnX1xY"
    set_api = SafeBrowsing(KEY)
    response = set_api.lookup_urls([url])
    r_str=str(response)
    
    result=""
   
    if(r_str.find('True') != -1):
        result="bad"
    else:
        result="good"
    return result

