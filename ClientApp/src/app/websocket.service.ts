import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { AID } from './interfaces/aid';
import { AType } from './interfaces/atype';

const url = 'ws://localhost:8080/ATProjectWAR/websocket';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
	
  ws: WebSocket;
  agentTypes: AType[] = [];
  performatives: string[] = [];
  runningAgents: AID[] = [];
  
  constructor() { }
  
  public connect(): void {
    this.ws = new WebSocket(url);
    var w = this;

    this.ws.onmessage = function (message) {
	  console.log("MESSAGE ARRIVED TO WEBSOCKET: " + message.data);
      var json = JSON.parse(message.data);
      var type = json.type;
      var data = json.data;
      let date = new Date();

      let now = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " - ";

      if (type == "start_agent") {
        let aType = { name: data[0].type.name, module: data[0].type.module };
        let aHost = { alias: data[0].host.alias, address: data[0].host.address };
        let aid = { name: data[0].name, host: aHost, type: aType };
		document.getElementById('txtarea').innerHTML += now;
		document.getElementById('txtarea').innerHTML += "STARTED AN AGENT: " + aid.name + " on host: " + aHost.address + " with alias: " + aHost.alias + "\n";
        w.runningAgents.push(aid);
      } else if (type == "stop_agent") {
        for (var i = 0; i < w.runningAgents.length; i++) {
          if (w.runningAgents[i].name == data[0].name && w.runningAgents[i].host.alias == data[0].host.alias
            && w.runningAgents[i].host.address == data[0].host.address && w.runningAgents[i].type.name == data[0].type.name
            && w.runningAgents[i].type.module == data[0].type.module) {
            w.runningAgents.splice(i, 1);
			document.getElementById('txtarea').innerHTML += now;
			document.getElementById('txtarea').innerHTML += "STOPPED AN AGENT: " + data[0].name + " on host: " + data[0].host.address + " with alias: " + data[0].host.alias + "\n";
          }
        }
      }  else if (type == "acl_message") {
		document.getElementById('txtarea').innerHTML += now;
        document.getElementById('txtarea').innerHTML += "sender name: " + data[0].sender.name + ", ";
        document.getElementById('txtarea').innerHTML +="performative: " + data[0].performative + ", ";
        document.getElementById('txtarea').innerHTML +="content: " + data[0].content + "\n";
      }
    };

  }
}
