import { Component } from '@angular/core';
import { HttpClient } from  '@angular/common/http';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ClientApp';
  constructor(private httpClient:HttpClient) {
	  
  }
  
  config = {
    headers: {
      'content-type': 'application/json',
      'accept': 'application/json'
    }
  }
  
  onClickClear(event: any) {
	 document.getElementById('txtarea').innerHTML = '';
  }
  
  onClickTypes(event: any) {
	this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/agents/classes')
	.subscribe(
		(data:any[]) => {
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
  
  onClickRun(event: any) {
	this.httpClient.put('http://localhost:8080/ATProjectWAR/agent-app/agents/running' + '/agent$InitiatorAgent/InitiatorAgent1')
	.subscribe(
		(data:any[]) => {
			document.getElementById('txtarea').innerHTML += 'Agent is now running!\n'; 
		}
	)
  }
  
  onClickStop(event: any) {
	 this.httpClient.delete('http://localhost:8080/ATProjectWAR/agent-app/agents/running' + '/InitiatorAgent1$master$localhost:8080$InitiatorAgent$agent')
	.subscribe(
		(data:any[]) => {
			document.getElementById('txtarea').innerHTML += 'Agent stopped!\n';   
		}
	)
  }
  
  onClickSend(event: any) {
	let acl = "{\"performative\":\"" + "request" + "\","
      + " \"sender\":"+ "\"PingAgent1$master$localhost:8080$PingAgent$agent\"" +","
      + " \"receivers\":[" + "\"PongAgent1$master$localhost:8080$PongAgent$agent\"" + "],"
      + " \"replyTo\":" + "\"PingAgent1$master$localhost:8080$PingAgent$agent\"" +","
      + " \"content\":\"" + "dont let your dreams be memes" + "\","
      + " \"language\":\"" + "english" + "\","
      + " \"encoding\":\"" + "UTF-8" + "\","
      + " \"ontology\":\"" + "ont" + "\","
      + " \"protocol\":\"" + "rest" + "\","
      + " \"conversationId\":\"" + "27387dfsjswhf324" + "\","
      + " \"replyWith\":\"" + "reee" + "\","
      + " \"inReplyTo\":\"" + "kek" + "\","
      + " \"replyBy\":\"" + "7843478" + "\"}";
	  
	  console.log(acl);
	  
	this.httpClient.post('http://localhost:8080/ATProjectWAR/agent-app/messages', acl, this.config)
	.subscribe(
		(data:any[]) => {
			console.log(data);
		}
	)
  }
}
