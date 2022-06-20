export function CodeBlock(props) {

    // This handles the on ket down event for the tab button so that it doesn't escape the text area and adds a tab
    const handleOnKeyDown = (e) => {
        if (e.key ==="Tab") {
            e.preventDefault();
            props.handleChange(props.code + "\t");
        }
    }

    return(
        <div>
            <div className="body_overview code_block create">
                <div className="line_numbers">
                    {props.lines}
                </div>
                <div className="code_container">
                    <textarea className="code_textarea" value={props.code} onKeyDown={handleOnKeyDown} onChange={(e) => {props.handleChange(e.target.value)}} placeholder="type code here ..." />
                </div>
            </div>
        </div>
    );
}