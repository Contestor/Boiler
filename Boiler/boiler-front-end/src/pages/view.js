import '../App.css';

import { useEffect, useState } from "react"
import { useLocation } from "react-router";
import { BASE_URL } from "../data/connection";
import { MenuBar } from '../components/menuBar';
import { CodeBlock } from '../components/codeBlock';
import { Footer } from '../components/footerView';

export function View() {
    const search = useLocation().search;
    const id = new URLSearchParams(search).get('id');

    //State
    const [method, setMethod] = useState({code:""});
    const [lines, setLines] = useState([<p>1</p>]);

    // Refresh method at initial render
    useEffect(()=>{
        refreshMethod();
      }, []);

    // This method sends a get request for the method with a particular id within the URL
    const refreshMethod = () =>{
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', 'authorization': "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6YW5lIiwiZXhwIjoxNjU1NjAwOTE4fQ.MLNgl6YzWKNCCnD4DZgTPaMXv3NCH5vDStWAfIGjAjFuUIKPomkIZmhuxUVQQMtSog5quiSQ8vDicegTZaK7hQ" },
        };
        const endpoint = BASE_URL + "methods?id=" + id;
        fetch(endpoint, requestOptions)
            .then(response => response.json())
            .then(data => loadMethod(data));
    }

    // This method handles the copy button by writing to the clipboard the code and the attributes
    const handleOnClickCopy = () => {
        var attributes = "";
        for (var i = 0; i < method.attributes.length; i++) {
            attributes += method.attributes[i].name;
            attributes += "\n\n";
        }
        navigator.clipboard.writeText(attributes + method.code);
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


    return (
        <div className="page">
            <MenuBar />
            <div className="main_container">
                <div className="header_container">
                    <h2><span className="highlight">METHOD</span> {method.name}</h2>
                    <p>{method.description}</p>
                </div>
                <CodeBlock key={method.id} mode={true} code={method.code} lines={lines} />
            </div>
            <Footer handleOnClickCopy={handleOnClickCopy} />
        </div>
    );
}