import { Component } from '@angular/core';
import { HttpClient } from  '@angular/common/http';
import { WebsocketService } from './websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ClientApp';
  constructor(private httpClient:HttpClient, private ws: WebsocketService) {
  }
  
  ngOnInit() {
	  this.ws.connect();
	  this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/messages')
	  .subscribe((data:any[]) => {
		  this.ws.performatives = data;
	  })
	  this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/agents/classes')
	  .subscribe((data:any[]) => {
		  this.ws.agentTypes = data;
	  })
	  this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/agents/running')
	  .subscribe((data:any[]) => {
		  this.ws.runningAgents = data;
	  })
  }
  
  config = {
    headers: {
      'content-type': 'application/json',
      'accept': 'application/json'
    }
  }
  
  onClickClear(event: any) {
	 document.getElementById('txtarea').innerHTML = '';
	 console.log(this.ws.runningAgents);
  }
  
  onClickTypes(event: any) {
	this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/agents/classes')
	.subscribe(
		(data:any[]) => {
			let date = new Date();
			let now = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " - ";
			document.getElementById('txtarea').innerHTML += now;
			document.getElementById('txtarea').innerHTML += "TYPES:\n";
			 for (let entry of data) {
				if(data.indexOf(entry) == data.length - 1) {
					document.getElementById('txtarea').innerHTML += entry.name;
				}
				else {
					document.getElementById('txtarea').innerHTML += entry.name + ", ";
				}
            }
			document.getElementById('txtarea').innerHTML += "\n";
		}
	)
  }
  
  onClickRunning(event: any) {
	this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/agents/running')
	.subscribe(
		(data:any[]) => {
			let date = new Date();
			let now = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " - ";
			document.getElementById('txtarea').innerHTML += now;
			document.getElementById('txtarea').innerHTML += "RUNNING AGENTS:\n";
			 for (let entry of data) {
				console.log(entry);
                document.getElementById('txtarea').innerHTML += entry.name + " on address: " + entry.host.address + " with alias: " + entry.host.alias + "\n";
            }
		}
	)
  }
  
  onClickPerf(event: any) {
	this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/messages')
	.subscribe(
		(data:any[]) => {
			let date = new Date();
			let now = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " - ";
			document.getElementById('txtarea').innerHTML += now;
			document.getElementById('txtarea').innerHTML += "PERFORMATIVES:\n";
			 for (let entry of data) {
				if(data.indexOf(entry) == data.length - 1) {
					document.getElementById('txtarea').innerHTML += entry;
				}
				else {
					document.getElementById('txtarea').innerHTML += entry + ", ";
				}
            }
			document.getElementById('txtarea').innerHTML += "\n";
		}
	)
  }
  
  onClickRun(event: any, typeVal, nameVal) {
	console.log(typeVal);
	console.log(nameVal);
	this.httpClient.put('http://localhost:8080/ATProjectWAR/agent-app/agents/running/' + typeVal + "/" + nameVal, this.config)
	.subscribe(
		(data:any[]) => {
		}
	)
  }
  
  onClickStop(event: any, value) {
	 console.log(value);
	 this.httpClient.delete('http://localhost:8080/ATProjectWAR/agent-app/agents/running/' + value)
	.subscribe(
		(data:any[]) => {
		}
	)
  }
  
  onClickSend(event: any, sender, receivers, perf, replyto, content, language, encoding, ontology, protocol, convid, replywith, inreply, replyby) {
	  
	console.log(receivers);
	let acl = "{\"performative\":\"" + perf + "\","
      + " \"sender\":\"" + sender + "\","
      + " \"receivers\":[\"" + receivers + "\"],"
      + " \"replyTo\":\"" + replyto + "\","
      + " \"content\":\"" + content + "\","
      + " \"language\":\"" + language + "\","
      + " \"encoding\":\"" + encoding + "\","
      + " \"ontology\":\"" + ontology + "\","
      + " \"protocol\":\"" + protocol + "\","
      + " \"conversationId\":\"" + convid + "\","
      + " \"replyWith\":\"" + replywith + "\","
      + " \"inReplyTo\":\"" + inreply + "\","
      + " \"replyBy\":\"" + replyby + "\"}";
	  
	  console.log(acl);
	  
	this.httpClient.post('http://localhost:8080/ATProjectWAR/agent-app/messages', acl, this.config)
	.subscribe(
		(data:any[]) => {
			
		}
	)
  }
}
