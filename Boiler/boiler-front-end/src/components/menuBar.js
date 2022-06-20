import '../App.css';

import { useEffect, useState } from "react";
import { BASE_URL } from "../data/connection";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCode } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

var visibility = false;

export function MenuBar() {
    
    // States
    const [methods, setMethods] = useState([]);
    const [domMethods, setDomMethods] = useState();

    // Refresh method at initial render
    useEffect(() => {
        refreshMethods();
        update();
    }, []);

    // This method sends a get request for the method with a particular id within the URL
    const refreshMethods = () => {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json', 'authorization': localStorage.getItem("bearer") },
        };
        const endpoint = BASE_URL + "users/" + localStorage.getItem("id") + "/methods"
        fetch(endpoint, requestOptions)
        .then(response => response.json())
        .then(data => setMethods(data));
    }

    // This method toggles the visibility of the menu items under the My Mythods menu item
    const toggleVisibility = () => {
        if (visibility) {
            setDomMethods([])
        } else {
            update();
        }
        visibility = !visibility;
    }

    // This sets the domMethods state with all the methods to add bellow the My Methods menu item from the logged in user
    const update = () => {
        const domMethodsToSet = [];
        for (let i=0; i<methods.length; i++) {
            const href = "my_methods?id=" + methods[i].id;
            domMethodsToSet.push(<tr key={i}><td></td><td className="menu_item"><a className="item sub_item" href={href}>{methods[i].name}</a></td></tr>)
        }
        setDomMethods(domMethodsToSet);
    }

    return (
        <div className="menubar">
            <div className="logo_container">
                <h1>BOILER.</h1>
            </div>
            <div className="body_container">
                <table>
                    <tbody>
                        <tr>
                            <td className="menu_item"><FontAwesomeIcon icon={faMagnifyingGlass} /></td>
                            <td className="menu_item"><a className="item" href="/explore">Explore</a></td>
                        </tr>
                        {localStorage.getItem("bearer") != null && 
                        <>
                            <tr>
                                <td className="menu_item"><FontAwesomeIcon icon={faCode} /></td>
                                <td className="menu_item"><button className="item" onClick={toggleVisibility}>My Methods</button></td>
                            </tr>
                            {domMethods}
                            <tr>
                                <td className="menu_item"><FontAwesomeIcon icon={faPlus} /></td>
                                <td className="menu_item"><a className="item" href="/create">Create</a></td>
                            </tr>
                        </>
                        }
                    </tbody>
                </table>
            </div>
            <div className="profile_container">
                {localStorage.getItem("bearer") == null && 
                    <>
                    <p>Guest User</p>
                    <p className="seperator">|</p>
                    <a href="/login" className="login_link item">log in</a>
                    </>
                }
                {localStorage.getItem("bearer") != null && 
                    <>
                        <p>{localStorage.getItem("username")}</p>
                        <p className="seperator">|</p>
                        <button onClick={() => {
                            localStorage.removeItem("bearer"); 
                            localStorage.removeItem("id");
                            localStorage.removeItem("username");
                            window.location.href="/explore"
                            }} className="login_link item">log out</button>
                    </>
                }
            </div>
        </div>
    );
}