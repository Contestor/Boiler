import '../App.css';

import { useEffect, useState } from "react"
import { useLocation } from "react-router";
import { BASE_URL } from "../data/connection";
import { MenuBar } from '../components/menuBar';
import { CodeBlock } from '../components/codeBlock';
import { Footer } from '../components/footer';

export function MyMethods() {
    const search = useLocation().search;
    const id = new URLSearchParams(search).get('id');

    // States
    const [method, setMethod] = useState({code:""});
    const [mode, setMode] = useState(true);
    const [lines, setLines] = useState([<p>1</p>]);

    // Refresh method at initial render
    useEffect(()=>{
        refreshMethod();
      }, []);
    
    // This method sends a get request for the method with a particular id within the URL
    const refreshMethod = () =>{
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', 'authorization': localStorage.getItem("bearer") },
        };
        const endpoint = BASE_URL + "methods?id=" + id;
        fetch(endpoint, requestOptions)
            .then(response => response.json())
            .then(data => loadMethod(data));
    }

    // This method handles the delete button sending a delete request for the method with a particular id within the URL
    const handleOnClickDelete = () =>{
        const requestOptions = {
          method: 'DELETE',
          headers: { 'Content-Type': 'application/json' , 'authorization': localStorage.getItem("bearer")},
        };
        const endpoint = BASE_URL + "methods?id=" + id;
        fetch(endpoint, requestOptions)
        .then(response => {window.location.href="/create"});    // redirect to the create page
    }

    // This method handles the save button sending a put request for the method with a particular id within the URL
    const handleOnClickSave = () =>{
        const requestOptions = {
            method: "PUT",
            headers: { 'Content-Type': 'application/json', 'authorization': localStorage.getItem("bearer")},
            body: JSON.stringify(method)
        };
        const endpoint = BASE_URL + "users/" + localStorage.getItem("id") + "/update";
        fetch(endpoint, requestOptions)
            .then(response => {setMode(!mode)});    // toggle codeblock readability
    }

    // This method handles the copy button by writing to the clipboard the code and the attributes
    const handleOnClickCopy = () => {
        var attributes = "";
        for (var i = 0; i < method.attributes.length; i++) {
            attributes += method.attributes[i].dataType;
            attributes += " ";
            attributes += method.attributes[i].name;
            attributes += "\n\n";
        }
        navigator.clipboard.writeText(attributes + method.code);
    }

    // This method handles the edit button by toggling the mode state which renders the codeblock to be readable or not
    const handleOnClickEdit  = () => {
        setMode(!mode);
    }

    // This method handles the share button sending a put request for the method with a particular id within the URL
    const handleOnClickShare = () => {
        setMethod({...method,shared:!method.shared});
        const requestOptions = {
            method: "PUT",
            headers: { 'Content-Type': 'application/json', 'authorization': localStorage.getItem("bearer")},
            body: JSON.stringify(method)
        };
        const endpoint = BASE_URL + "users/" + localStorage.getItem("id") + "/update";
        fetch(endpoint, requestOptions);
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

    // This method sets the method state then updates the line numbers in the code block
    const loadMethod = (data) => {
        setMethod(data);
        updateLines(data.code)
    }

    // This updates the line numbers in the code block
    const updateLines = (code) => {
        // Set line numbers
        const splitLines = code.split("\n");
        const linesToSet = [];
        for (var i = 0; i < splitLines.length; i++) {
            linesToSet.push(<p>{i + 1}</p>);
        }
        setLines(linesToSet);
    }

    return(
        <div className="page">
            <MenuBar />
            <div className="main_container">
                <div className="header_container">
                    <h2><span className="highlight">METHOD</span> {method.name}</h2>
                    <p>{method.description}</p>
                </div>
                <CodeBlock key={method.id} mode={mode} code={method.code} lines={lines} handleChange={handleChange} />
            </div>
            <Footer data={method} mode={mode} shared={method.shared} onClickShare={handleOnClickShare} onClickEdit={handleOnClickEdit} onClickSave={handleOnClickSave} onClickCopy={handleOnClickCopy} onClickDelete={handleOnClickDelete} />
        </div>
    );
}