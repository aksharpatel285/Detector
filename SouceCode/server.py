from flask import Flask, jsonify,render_template,redirect
from flask import request
from detect import checkurlfrom_Detect
from multiUrl import checkUrl,sanitization
app = Flask(__name__)

@app.route('/akshar', methods=['POST'])
def get_data_my():
    u1 = request.form["url"]
    data = checkUrl("",u1)
    return jsonify(data)

@app.route('/akshar_my', methods=['POST'])
def get_data():
    u1 = request.form["url"]
    data = checkurlfrom_Detect("",u1)
    return jsonify(data)
    
@app.route('/check', methods=['POST'])
def check():
    u1 = request.form["url"]
    data =checkurlfrom_Detect("",u1)
    return render_template('index.html',result=data,url=u1)

@app.route('/check_my', methods=['POST'])
def check_my():
    u1 = request.form["url"]
    data =checkUrl("",u1)
    return render_template('index.html',result=data,url=u1)

@app.route('/Name')
def get_name():
    
    return "Hello i am Akshar"

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')