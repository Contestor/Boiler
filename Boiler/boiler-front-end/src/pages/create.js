import { useState } from "react";
import { BASE_URL } from "../data/connection";
import { CodeBlock } from '../components/codeBlockCreate';
import { MenuBar } from '../components/menuBar';

export function Create() {
    // States
    const [method, setMethod] = useState({name:"",description:"",returnType:"",parameters:"",code:"",shared:false,attributes:[]})
    const [lines, setLines] = useState([<p>1</p>]);

    // This method sends a post request for the method being created
    const submitMethod = () => {
        if (method.name === "" && method.description === "" && method.code === "") {
            alert("fill in all fields");    // Alert user to fill in all fields before submiting method
        } else {
            const requestOptions = {
                method: "POST",
                headers: { 'Content-Type': 'application/json', 'authorization': localStorage.getItem("bearer")},
                body: JSON.stringify(method)
            };
            const endpoint = BASE_URL + "users/" + localStorage.getItem("id") + "/save";
            fetch(endpoint, requestOptions)
                .then(response => {window.location.href="/create"});    // Return to the create page once request is completed
        }
    }

    // This method updates the states when the codeblock is edited
    const handleChange = (text) => {
        // Set method code to value in textarea
        setMethod({...method,code:text});

        // Set line numbers
        const splitLines = text.split("\n");
        const linesToSet = [];
        for (var i = 0; i < splitLines.length; i++) {
            linesToSet.push(<p>{i + 1}</p>);
        }
        setLines(linesToSet);

        // Find and store all attributes for the method
        var parameters = splitLines[0].split(/[()]+/);
        if(parameters.length > 2) {
            parameters = parameters[1].split(",");
            const attributesToAdd = [];
            for (var j = 0; j < parameters.length; j++) {
                if (parameters[j] !== "") {
                    const attribute = parameters[j].trim().split(" ")
                    attributesToAdd.push({name: attribute[1],dataType:attribute[0]});
                }
            }
            setMethod({...method,attributes:attributesToAdd,code:text});
        } else {
            setMethod({...method,attributes:[],code:text});
        }
    }

    return (
        <div className="page">
            <MenuBar />
            <div className="main_container">
                <div className="header_container">
                    <h2><span className="highlight">CREATE</span> New Method</h2>
                    <input className="create_input" placeholder="method name" onChange={(data)=>{setMethod({...method,name:data.target.value})}}/>
                    <textarea className="create_input" placeholder="description" onChange={(data)=>{ setMethod({...method,description:data.target.value}) }} />
                </div>
                <CodeBlock key={method.id} mode={false} code={method.code} handleChange={handleChange} lines={lines} />
                <button className="btn" onClick={submitMethod}>Save</button>
            </div>
        </div>
    );
}