import { useState } from 'react';
import { BASE_URL } from '../data/connection';

export function Login() {
    // States
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    // This method sends a post request for the user
    const submitLogin = () => {
        const user = {username:username,password:password}
        const requestOptions = {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
          };
          const endpoint = BASE_URL + "users/login"
          fetch(endpoint, requestOptions)
              .then(response => {loginResponse(response)});     // set the bearer token to the local storage
    }

    // This method handles the login response
    const loginResponse = (response) => {   
        if(response.status === 200) {   // Login was successful
            localStorage.setItem("bearer", response.headers.get("authorization"));
            localStorage.setItem("username", username);
            getUser();  
        } else if (response.status === 403) {   // Incorrect password or username
            alert("incorrect username or password")
        } else {    // Any other error that occured
            alert("something went wrong")
        }
    }

    // This method sends a get request for the user with a particular username
    const getUser = () => {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', 'authorization': localStorage.getItem("bearer") },
        };
        const endpoint = BASE_URL + "users/" + username;
        fetch(endpoint, requestOptions)
        .then(response => response.json())
        .then(data => localStorage.setItem("id", data.id))
        .then(response => {window.location.href="/explore"});   // redirect to the explore page when logged in
    }

    return (

        <div className="page">
            <div className="login_container">
                <div className="login">
                    <h1>Login</h1>
                    <input className="login_field" value={username} onChange={(e) => {setUsername(e.target.value)}} placeholder="username"></input>
                    <input className="login_field" type="password" value={password} onChange={(e) => {setPassword(e.target.value)}} placeholder="password"></input>
                    <button className="btn" onClick={submitLogin}>Login</button>
                </div>
            </div>
            <div className="login_container display">
                <h1 className="brand_title">BOILER.</h1>
            </div>
        </div>
    );
}