/**
 *  Botem CSM505 (v.0.0.1)
 *
 * MIT License
 *
 * Copyright (c) 2020 fison67@nate.com
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
 
import groovy.json.JsonSlurper
import java.text.DateFormat

metadata {
	definition (name: "Botem CSM505", namespace: "streamorange58819", author: "fison67", ocfDeviceType: "oic.r.humidity", mnmn:"fison67", vid:"925fc05c-9366-31e7-a2cf-c0ead7d70e33") {
        capability "streamorange58819.counter"
        capability "Sensor"
	}
}


def setInfo(String app_url, String id) {
	state.app_url = app_url
    state.id = id
}

def parse(String description) {}

def setStatus(data){
	log.debug "${data}"
    sendEvent(name:"current", value: data.current)
    sendEvent(name:"totalin", value: data.totalIn)
    sendEvent(name:"totalout", value: data.totalOut)
}

def setCurrent(count){
	processCommand("setCurrent", count)
}

def minusCount(){
	processCommand("minusCount", [])
}

def plusCount(){
	processCommand("plusCount", [])
}

def processCommand(cmd, data){
	def body = [
        "id": state.id,
        "cmd": cmd,
        "data": data
    ]
    def options = makeCommand(body)
    sendCommand(options, null)
}

def refresh(){}

def updated(){}

def sendCommand(options, _callback){
	def myhubAction = new physicalgraph.device.HubAction(options, null, [callback: _callback])
    sendHubCommand(myhubAction)
}

def makeCommand(body){
	def options = [
     	"method": "POST",
        "path": "/api/control",
        "headers": [
        	"HOST": parent._getServerURL(),
            "Content-Type": "application/json"
        ],
        "body":body
    ]
    return options
}
