import '../App.css';

import { useEffect, useState } from "react";
import { BASE_URL } from "../data/connection";
import { MenuBar } from '../components/menuBar';
import { SearchBar } from '../components/searchBar';
import { Method } from '../components/methodOverview';

export function Explore() {
    // States
    const [methods, setMethods] = useState([])
    let domMethods = [];

    // Get all shared methods on initial render
    useEffect(() => {
        refreshMethods("all")
    }, []);

    // This method sends a get request for all the shared methods and stores them in the methods state
    const refreshMethods = (search) => {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', 'authorization': "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6YW5lIiwiZXhwIjoxNjU1NjAwOTE4fQ.MLNgl6YzWKNCCnD4DZgTPaMXv3NCH5vDStWAfIGjAjFuUIKPomkIZmhuxUVQQMtSog5quiSQ8vDicegTZaK7hQ" },
        };
        const endpoint = BASE_URL + "methods/search/" + search;
        fetch(endpoint, requestOptions)
        .then(response => response.json())
        .then(data => setMethods(data));    // update the method state with the data recieved from the get request
    }

    // This sets the initial dom for all the method overview tabs
    domMethods = [];
    for (let i=0; i<methods.length; i++) {
        domMethods.push(<Method key={i} data={methods[i]}/>)
    }

    // This method refreshes the methods state wheneve the search bar changes
    const handleOnChange = (e) => {
        if (e.target.value !== "") {
            refreshMethods(e.target.value); // return all shared methods 
        } else {
            refreshMethods("all")   // return all shared methods if the search bar is empty
        }
    }

    return(
        <div className="page">
            <MenuBar />
            <div className="main_container">
                <div className="header_container">
                    <h2><span className="highlight">EXPLORE</span> Methods</h2>
                </div>
                <SearchBar onChange={handleOnChange} />
                {domMethods}
            </div>
        </div>
    );
}