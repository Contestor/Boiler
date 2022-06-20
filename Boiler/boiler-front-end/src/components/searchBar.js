import '../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

export function SearchBar(props) {
    return (
        <div className="search_bar">
            <FontAwesomeIcon className="icon" icon={faMagnifyingGlass} />
            <input onChange={props.onChange} />
        </div>
    );
}