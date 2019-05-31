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
  
  onClickClear(event: any) {
	 document.getElementById('txtarea').innerHTML = '';
  }
  
  onClickTypes(event: any) {
	this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/agents/classes')
	.subscribe(
		(data:any[]) => {
			document.getElementById('txtarea').innerHTML += "TYPES: ";
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
			document.getElementById('txtarea').innerHTML += "RUNNING AGENTS: ";
			 for (let entry of data) {
                document.getElementById('txtarea').innerHTML += entry.name + " on address: " + entry.host.address + " with alias: " + entry.host.alias + "\n";
            }
		}
	)
  }
  
  onClickPerf(event: any) {
	this.httpClient.get('http://localhost:8080/ATProjectWAR/agent-app/messages')
	.subscribe(
		(data:any[]) => {
			document.getElementById('txtarea').innerHTML += "PERFORMATIVES: ";
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
}
