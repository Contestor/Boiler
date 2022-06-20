import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons';
import { faCopy } from '@fortawesome/free-solid-svg-icons';
import { faPen } from '@fortawesome/free-solid-svg-icons';
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';

export function Footer(props) {

    let editButton;
    if (props.mode)
        editButton = <button onClick={props.onClickEdit}>Edit</button>;
    else editButton = <button onClick={props.onClickSave}>Save</button>;

    let shareButton;
    if (props.shared)
        shareButton = <button onClick={props.onClickShare}>Shared</button>;
    else shareButton = <button onClick={props.onClickShare}>Share</button>;

    return (
        <div className="footer_container">
            <div className="footer_buttons_container">
                <div className="footer_button">
                    <FontAwesomeIcon icon={faArrowUpFromBracket} />
                    {shareButton}
                </div>
                <div className="footer_button">
                    <FontAwesomeIcon icon={faCopy} />
                    <button onClick={props.onClickCopy}>Copy</button>
                </div>
                <div className="footer_button">
                    <FontAwesomeIcon icon={faPen} />
                    {editButton}
                </div>
                <div className="footer_button">
                    <FontAwesomeIcon icon={faTrashCan} />
                    <button onClick={props.onClickDelete}>Delete</button>
                </div>
            </div>
        </div>
    );
}