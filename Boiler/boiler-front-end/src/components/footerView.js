import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart } from '@fortawesome/free-solid-svg-icons';
import { faCopy } from '@fortawesome/free-solid-svg-icons';
import { faPen } from '@fortawesome/free-solid-svg-icons';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

export function Footer(props) {
    return (
        <div className="footer_container">
            <div className="footer_buttons_container">
                <div className="footer_button">
                    <FontAwesomeIcon icon={faHeart} />
                    <button>Save</button>
                </div>
                <div className="footer_button">
                    <FontAwesomeIcon icon={faCopy} />
                    <button onClick={props.handleOnClickCopy}>Copy</button>
                </div>
                <div className="footer_button">
                    <FontAwesomeIcon icon={faArrowLeft} />
                    <button onClick={response => {window.location.href="/explore"}}>Back</button>
                </div>
            </div>
        </div>
    );
}