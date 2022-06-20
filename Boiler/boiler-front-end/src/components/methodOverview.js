import '../App.css';

export function Method(props) {

    // This relocates the user to the view page for the method they clicked on
    const handleOnClick = () => {
        const href = "/view?id=" + props.data.id;
        window.location.href=href;
    }

    return (
        <div className="method_overview" onClick={handleOnClick}>
            <div className="header_overview">
                    <h3>METHOD : {props.data.name}</h3>
                </div>
            <div className='body_overview'>
                <p>{String(props.data.description)}</p>
                <div className="tags">{props.data.attributes.map(tag => {
                        return (
                            <div className='tag'>
                                <p>{tag.name}</p>
                            </div>
                        )})}
                </div>
            </div>
        </div>
    );
}